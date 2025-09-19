package com.nttdata.dockerized.postgresql.client;

import com.nttdata.dockerized.postgresql.model.dto.CuotaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "prestamo-ms")
public interface PrestamoClient {

    /*
    @GetMapping("/api/prestamos/pendientes/cliente/{numeroDocumento}")
    List<PrestamoDTO> traerPrestamosPendientes(@PathVariable(value = "numeroDocumento") String numeroDocumento);
     */

    // Traer la cuota mas cercana de un prestamo
    @GetMapping("/api/cuotas/proxima_pagar/prestamo/{idPrestamo}")
    CuotaDTO traerCuotaMasCercanaAPagar(@PathVariable(value = "idPrestamo") Long idPrestamo);

    @GetMapping("/api/cuotas/{idCuota}")
    CuotaDTO traerCuotaPorId(@PathVariable(value = "idCuota") Long idCuota);

    @PutMapping("/api/cuotas/{idCuota}")
    CuotaDTO cambiarEstadoCuota(@PathVariable(value = "idCuota") Long idCuota);

    @PutMapping("/api/prestamos/{idPrestamo}/plazo/{plazo}")
    void cambiarEstadoPrestamo(@PathVariable(value = "idPrestamo") Long idPrestamo, @PathVariable(value = "plazo") Integer plazo);



}
