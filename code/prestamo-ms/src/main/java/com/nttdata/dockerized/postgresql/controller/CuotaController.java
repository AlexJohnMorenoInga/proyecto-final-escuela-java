package com.nttdata.dockerized.postgresql.controller;

import com.nttdata.dockerized.postgresql.model.entity.Cuota;
import com.nttdata.dockerized.postgresql.service.CuotaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuotas")
public class CuotaController {

    private final CuotaService cuotaService;

    public CuotaController(CuotaService cuotaService) {
        this.cuotaService = cuotaService;
    }

    @GetMapping("/prestamo/{idPrestamo}")
    public ResponseEntity<List<Cuota>> traerCuotas(@PathVariable(value = "idPrestamo") Long idPrestamo){
        List<Cuota> cuotas = cuotaService.traerCuotas(idPrestamo);
        return new ResponseEntity<>(cuotas, HttpStatus.OK);
    }

    @GetMapping("/proxima_pagar/prestamo/{idPrestamo}")
    public ResponseEntity<Cuota> traerCuotaMasProximaAPagar(@PathVariable(value = "idPrestamo") Long idPrestamo){
        Cuota cuota = cuotaService.traerCuotaMasCercanaAPagar(idPrestamo);
        return new ResponseEntity<>(cuota, HttpStatus.OK);
    }

    @GetMapping("/{idCuota}")
    public ResponseEntity<Cuota> traerCuotaPorId(@PathVariable(value = "idCuota") Long idCuota){
        Cuota cuota = cuotaService.traerCuotaPorId(idCuota);
        return new ResponseEntity<>(cuota, HttpStatus.OK);
    }

    @PutMapping("/{idCuota}")
    public ResponseEntity<Cuota> cambiarEstadoCuota(@PathVariable(value = "idCuota") Long idCuota){
        Cuota cuota = cuotaService.cambiarEstadoCuota(idCuota);
        return new ResponseEntity<>(cuota, HttpStatus.OK);
    }

}
