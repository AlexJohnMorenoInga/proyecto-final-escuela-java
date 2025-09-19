package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.model.dto.ClienteDTO;
import com.nttdata.dockerized.postgresql.model.entity.Prestamo;

import java.util.List;

public interface PrestamoService {

    Prestamo guardarPrestamo(String numeroDocumento, Prestamo prestamo);

    ClienteDTO traerCliente(String numeroDocumento);

    // Para traer prestamos no pagados o en proceso de pago
    List<Prestamo> traerPrestamosPendientes(String numeroDocumento);

    Prestamo cambiarEstadoPrestamo(Long idPrestamo, Integer plazo);

}
