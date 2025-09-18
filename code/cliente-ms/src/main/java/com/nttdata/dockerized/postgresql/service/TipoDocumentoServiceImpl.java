package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.exceptions.BadRequestException;
import com.nttdata.dockerized.postgresql.model.entity.TipoDocumento;
import com.nttdata.dockerized.postgresql.repository.TipoDocumentoRepository;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;

@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService{

    private final TipoDocumentoRepository tipoDocumentoRepository;

    private static final String NOMBRE_TIPO_DOCUMENTO_REGEX = "^(?=(?:.*[A-Za-z]){3,})[A-Za-z]+(?: [A-Za-z]+)*$";

    public TipoDocumentoServiceImpl(TipoDocumentoRepository tipoDocumentoRepository) {
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    @Override
    public TipoDocumento crearTipoDocumento(TipoDocumento tipoDocumento) {

        // Validar que el nombre del tipo documento tenga minimo 3 caracteres alfabeticos
        if (!isValidNombreTipoDocumento(tipoDocumento.getNombre())) {
            throw new BadRequestException("Nombre de tipo de documento inválido");
        }

        // Trim y normalizacion a mayusculas
        String nombreNormalizado = StringUtils.trimWhitespace(tipoDocumento.getNombre());
        nombreNormalizado = nombreNormalizado.toUpperCase();
        tipoDocumento.setNombre(nombreNormalizado);

        // Validar unicidad
        if (tipoDocumentoRepository.existsByNombreIgnoreCase(nombreNormalizado)) {
            throw new BadRequestException("El tipo de documento ya existe");
        }

        TipoDocumento tipoDocumentoGuardado = tipoDocumentoRepository.save(tipoDocumento);

        return tipoDocumentoGuardado;

    }

    private boolean isValidNombreTipoDocumento(String nombreTipoDocumento) {
        return nombreTipoDocumento != null && nombreTipoDocumento.matches(NOMBRE_TIPO_DOCUMENTO_REGEX);
    }

}
