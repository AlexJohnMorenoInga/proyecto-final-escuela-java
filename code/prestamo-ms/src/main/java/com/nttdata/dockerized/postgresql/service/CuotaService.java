package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.model.entity.Cuota;

import java.util.List;

public interface CuotaService {

    // Para traer todas las cuotas de un prestamo (pagadas y no pagadas)
    List<Cuota> traerCuotas(Long idPrestamo);

    // Para traer la cuota más cercana a pagar de un prestamo
    Cuota traerCuotaMasCercanaAPagar(Long idPrestamo);

    Cuota traerCuotaPorId(Long idCuota);

    Cuota cambiarEstadoCuota(Long idCuota);

}
