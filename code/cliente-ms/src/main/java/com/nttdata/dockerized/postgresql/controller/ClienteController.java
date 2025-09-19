package com.nttdata.dockerized.postgresql.controller;

import com.nttdata.dockerized.postgresql.model.dto.ClienteDTO;
import com.nttdata.dockerized.postgresql.model.dto.GuardarClienteRequestDTO;
import com.nttdata.dockerized.postgresql.model.dto.GuardarClienteResponseDTO;
import com.nttdata.dockerized.postgresql.model.entity.Cliente;
import com.nttdata.dockerized.postgresql.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.nttdata.dockerized.postgresql.mapper.ClienteMapper.INSTANCE;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/tipo_documento/{idTipoDocumento}")
    public ResponseEntity<GuardarClienteResponseDTO> guardarCliente(@PathVariable("idTipoDocumento") Long idTipoDocumento,
                                                                    @RequestBody GuardarClienteRequestDTO guardarClienteDTO){

        Cliente clienteGuardado = clienteService.guardarCliente(idTipoDocumento, INSTANCE.toClienteEntity(guardarClienteDTO));

        return new ResponseEntity<>(INSTANCE.toGuardarClienteResponseDTO(clienteGuardado), HttpStatus.CREATED);

    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<Cliente> traerClientePorId(@PathVariable(value = "idCliente") Long idCliente){

        Cliente cliente = clienteService.traerClientePorId(idCliente);

        return new ResponseEntity<>(cliente, HttpStatus.OK);

    }

    @GetMapping("/numero_documento/{numeroDocumento}")
    public ResponseEntity<Cliente> traerClientePorNumeroDocumento(@PathVariable(value = "numeroDocumento") String numeroDocumento){

         Cliente cliente= clienteService.traerClientePorNumeroDocumento(numeroDocumento);

        return new ResponseEntity<>(cliente, HttpStatus.OK);

    }

}
