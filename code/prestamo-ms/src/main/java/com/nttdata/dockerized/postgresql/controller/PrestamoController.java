package com.nttdata.dockerized.postgresql.controller;

import com.nttdata.dockerized.postgresql.model.dto.ClienteDTO;
import com.nttdata.dockerized.postgresql.model.dto.CrearPrestamoDTO;
import com.nttdata.dockerized.postgresql.model.dto.CuotaCronogramaDTO;
import com.nttdata.dockerized.postgresql.model.entity.Prestamo;
import com.nttdata.dockerized.postgresql.service.PrestamoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.nttdata.dockerized.postgresql.mapper.PrestamoMapper.INSTANCE;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @GetMapping("/clientes/{numeroDocumento}")
    public ResponseEntity<ClienteDTO> traerCliente(@PathVariable(value = "numeroDocumento") String numeroDocumento){

        ClienteDTO clienteDTO = prestamoService.traerCliente(numeroDocumento);

        return new ResponseEntity<>(clienteDTO, HttpStatus.OK);

    }

    @PostMapping("/cliente/{numeroDocumento}")
    public ResponseEntity<Prestamo> crearPrestamo(@PathVariable(value = "numeroDocumento") String numeroDocumento, @RequestBody CrearPrestamoDTO crearPrestamoDTO){

        Prestamo prestamo = prestamoService.guardarPrestamo(numeroDocumento, INSTANCE.toPrestamo(crearPrestamoDTO));

        return new ResponseEntity<>(prestamo, HttpStatus.CREATED);

    }

    @GetMapping("/pendientes/cliente/{numeroDocumento}")
    public  ResponseEntity<List<Prestamo>> traerPrestamosPendientes(@PathVariable(value = "numeroDocumento") String numeroDocumento){

        List<Prestamo> prestamosPendientes = prestamoService.traerPrestamosPendientes(numeroDocumento);

        return new ResponseEntity<>(prestamosPendientes, HttpStatus.OK);

    }


    @PutMapping("/{idPrestamo}/plazo/{plazo}")
    public void cambiarEstadoPrestamo(@PathVariable(value = "idPrestamo") Long idPrestamo, @PathVariable(value = "plazo") Integer plazo){
        prestamoService.cambiarEstadoPrestamo(idPrestamo, plazo);
        //return new ResponseEntity<>(prestamo, HttpStatus.OK);
    }



}
