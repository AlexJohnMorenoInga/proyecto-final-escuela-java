package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.model.dto.CuotaDTO;
import com.nttdata.dockerized.postgresql.model.entity.Pago;

public interface PagoService {

    CuotaDTO traerCuotaMasCercana(Long idPrestamo);

    Pago guardarPago(Long idPrestamo);

}
