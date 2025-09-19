package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.exceptions.BadRequestException;
import com.nttdata.dockerized.postgresql.exceptions.ResourceNotFoundException;
import com.nttdata.dockerized.postgresql.model.entity.Cliente;
import com.nttdata.dockerized.postgresql.model.entity.TipoDocumento;
import com.nttdata.dockerized.postgresql.repository.ClienteRepository;
import com.nttdata.dockerized.postgresql.repository.TipoDocumentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class ClienteServiceImpl implements ClienteService{

    private final ClienteRepository clienteRepository;

    private final TipoDocumentoRepository tipoDocumentoRepository;

    private static final String NOMBRE_APELLIDO_REGEX = "^[A-Za-zÀ-ÖØ-öø-ÿ ]{1,50}$";
    private static final String NUMERO_DOCUMENTO_REGEX = "^\\d{8,}$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String NUMERO_CELULAR_REGEX = "^9\\d{8}$";

    public ClienteServiceImpl(ClienteRepository clienteRepository, TipoDocumentoRepository tipoDocumentoRepository) {
        this.clienteRepository = clienteRepository;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    @Override
    public Cliente guardarCliente(Long idTipoDocumento, Cliente cliente) {
        // Verificar que el tipo de documento exista
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(idTipoDocumento).orElseThrow(
                () -> new ResourceNotFoundException("El tipo de documento indicado no existe")
        );
        // Validar nombre
        String nombreNormalizado = StringUtils.trimWhitespace(cliente.getNombre());
        if (!isValidNombreApellido(nombreNormalizado)) {
            throw new BadRequestException("Nombre inválido");
        }
        cliente.setNombre(nombreNormalizado);
        // Validar apellido
        String apellidoNormalizado = StringUtils.trimWhitespace(cliente.getApellido());
        if (!isValidNombreApellido(apellidoNormalizado)) {
            throw new BadRequestException("Apellido inválido");
        }
        cliente.setApellido(apellidoNormalizado);
        // Validar numero de documento (que tenga minimo 8 digitos) - dni tiene 8 y carnet de extranjeria tiene 9
        if(!isValidNumeroDocumento(cliente.getNumeroDocumento())){
            throw new BadRequestException("Número de documento inválido");
        }
        // Validar email
        if(!isValidEmail(cliente.getEmail())){
            throw new BadRequestException("Email inválido");
        }
        // Validar celular
        if(!isValidNumeroCelular(cliente.getCelular())){
            throw new BadRequestException("Número de célular inválido");
        }
        // Validar unicidad del numero de documento
        if (clienteRepository.existsByNumeroDocumentoIgnoreCase(cliente.getNumeroDocumento())) {
            throw new BadRequestException("El número de documento de identidad ya existe");
        }
        // Validar unicidad del email
        if (clienteRepository.existsByEmailIgnoreCase(cliente.getEmail())) {
            throw new BadRequestException("El email ya existe");
        }
        // Validar unicidad del celular
        if (clienteRepository.existsByCelularIgnoreCase(cliente.getCelular())){
            throw new BadRequestException("El número de célular ya existe");
        }
        // Setear el tipo de documento al cliente
        cliente.setTipoDocumento(tipoDocumento);
        // Setear active (cliente nace activado) y fecha de creacion
        cliente.setActive(true);
        cliente.setFechaCreacion(LocalDateTime.now());
        // Guardar el cliente
        Cliente clienteGuardado = clienteRepository.save(cliente);
        // Retornar una respuesta
        return clienteGuardado;
    }

    @Override
    public Cliente traerClientePorId(Long idCliente) {
        // Buscar al cliente por id
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResourceNotFoundException("El cliente indicado no existe")
        );
        // Devolver al cliente encontrado
        return cliente;
    }

    @Override
    public Cliente traerClientePorNumeroDocumento(String numeroDocumento) {
        // Buscar al cliente por numero de documento de identidad
        Cliente cliente = clienteRepository.findClienteByNumeroDocumento(numeroDocumento).orElseThrow(
                () -> new ResourceNotFoundException("No existe un cliente con el número de documento de identidad indicado")
        );
        // Devolver al cliente encontrado
        return cliente;
    }


    private boolean isValidNombreApellido(String nombre) {
        return nombre != null && nombre.matches(NOMBRE_APELLIDO_REGEX);
    }

    private boolean isValidNumeroDocumento(String numeroDocumento) {
        return numeroDocumento != null && numeroDocumento.matches(NUMERO_DOCUMENTO_REGEX);
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    private boolean isValidNumeroCelular(String celular) {
        return celular != null && celular.matches(NUMERO_CELULAR_REGEX);
    }

}
