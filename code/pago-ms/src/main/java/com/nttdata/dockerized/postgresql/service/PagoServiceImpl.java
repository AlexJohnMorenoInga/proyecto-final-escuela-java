package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.client.PrestamoClient;
import com.nttdata.dockerized.postgresql.exceptions.ResourceNotFoundException;
import com.nttdata.dockerized.postgresql.model.dto.CuotaDTO;
import com.nttdata.dockerized.postgresql.model.entity.Pago;
import com.nttdata.dockerized.postgresql.repository.PagoRepository;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class PagoServiceImpl implements PagoService{

    private final PagoRepository pagoRepository;

    private final PrestamoClient prestamoClient;

    public PagoServiceImpl(PagoRepository pagoRepository, PrestamoClient prestamoClient) {
        this.pagoRepository = pagoRepository;
        this.prestamoClient = prestamoClient;
    }

    @Override
    public CuotaDTO traerCuotaMasCercana(Long idPrestamo) {

        try {
            CuotaDTO cuotaDTO = prestamoClient.traerCuotaMasCercanaAPagar(idPrestamo);
            return cuotaDTO;
        } catch (FeignException.NotFound e) {
            throw new  ResourceNotFoundException("Prestamo no encontrado");
        }

    }

    @Override
    public Pago guardarPago(Long idPrestamo) {

        try {

            CuotaDTO cuotaDTO = prestamoClient.traerCuotaMasCercanaAPagar(idPrestamo);
            //Se hace el pago de la cuota
            CuotaDTO cuotaDTOpagada = prestamoClient.cambiarEstadoCuota(cuotaDTO.getIdCuota());
            //Registrar el pago
            Pago pago = Pago.builder()
                    .idCuota(cuotaDTOpagada.getIdCuota())
                    .fechaPago(LocalDateTime.now())
                    .pagoCapital(cuotaDTOpagada.getCapitalCuota())
                    .pagoInteres(cuotaDTOpagada.getInteresCuota())
                    .build();
            Pago pagoGuardado = pagoRepository.save(pago);
            //Cambiar el estado del prestamo
            prestamoClient.cambiarEstadoPrestamo(idPrestamo, cuotaDTOpagada.getNumeradorCuota());

            return pagoGuardado;

        } catch (FeignException.NotFound e) {
            throw new  ResourceNotFoundException("Prestamo no encontrado");
        }



    }


}
