package com.nttdata.dockerized.postgresql.controller;

import com.nttdata.dockerized.postgresql.model.dto.CuotaDTO;
import com.nttdata.dockerized.postgresql.model.entity.Pago;
import com.nttdata.dockerized.postgresql.service.PagoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping("/prestamo/cuota/{idPrestamo}")
    public ResponseEntity<CuotaDTO> traerCuotaMasCercanaAPagar(@PathVariable(value = "idPrestamo") Long idPrestamo){

        CuotaDTO cuotaDTO = pagoService.traerCuotaMasCercana(idPrestamo);

        return new ResponseEntity<>(cuotaDTO, HttpStatus.OK);

    }

    @PostMapping("/prestamo/{idPrestamo}")
    public ResponseEntity<Pago> realizarPago(@PathVariable(value = "idPrestamo") Long idPrestamo){

        Pago pago = pagoService.guardarPago(idPrestamo);

        return new ResponseEntity<>(pago, HttpStatus.OK);

    }

}
