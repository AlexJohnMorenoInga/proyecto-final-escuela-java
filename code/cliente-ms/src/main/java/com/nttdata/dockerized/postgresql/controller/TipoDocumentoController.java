package com.nttdata.dockerized.postgresql.controller;

import com.nttdata.dockerized.postgresql.model.dto.TipoDocumentoDTO;
import com.nttdata.dockerized.postgresql.model.entity.TipoDocumento;
import com.nttdata.dockerized.postgresql.service.TipoDocumentoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.nttdata.dockerized.postgresql.mapper.TipoDocumentoMapper.INSTANCE;

@RestController
@RequestMapping("/api/tipo_documento")
public class TipoDocumentoController {

    private final TipoDocumentoService tipoDocumentoService;

    public TipoDocumentoController(TipoDocumentoService tipoDocumentoService) {
        this.tipoDocumentoService = tipoDocumentoService;
    }

    @PostMapping
    public ResponseEntity<TipoDocumento> guardarTipoDocumento(@RequestBody TipoDocumentoDTO tipoDocumentoDTO){

        TipoDocumento tipoDocumentoGuardado = tipoDocumentoService.crearTipoDocumento(INSTANCE.toTipoDocumento(tipoDocumentoDTO));

        return new ResponseEntity<>(tipoDocumentoGuardado, HttpStatus.CREATED);

    }

}
