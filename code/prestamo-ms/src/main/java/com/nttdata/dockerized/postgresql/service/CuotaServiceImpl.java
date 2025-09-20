package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.exceptions.ResourceNotFoundException;
import com.nttdata.dockerized.postgresql.model.entity.Cuota;
import com.nttdata.dockerized.postgresql.model.entity.Prestamo;
import com.nttdata.dockerized.postgresql.repository.CuotaRepository;
import com.nttdata.dockerized.postgresql.repository.PrestamoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CuotaServiceImpl implements CuotaService{

    private final CuotaRepository cuotaRepository;

    private final PrestamoRepository prestamoRepository;

    public CuotaServiceImpl(CuotaRepository cuotaRepository, PrestamoRepository prestamoRepository) {
        this.cuotaRepository = cuotaRepository;
        this.prestamoRepository = prestamoRepository;
    }

    @Override
    public List<Cuota> traerCuotas(Long idPrestamo) {
        // Verificar que exista el prestamo
        Prestamo prestamo = prestamoRepository.findById(idPrestamo).orElseThrow(
                () -> new ResourceNotFoundException("El tipo de documento indicado no existe")
        );
        // Buscar las cuotas por id de Prestamo
        Iterable<Cuota> cuotas = cuotaRepository.findByPrestamoIdPrestamo(idPrestamo);
        // Convertir el iterable a una lista
        List<Cuota> cuotaList = StreamSupport.stream(cuotas.spliterator(), false).collect(Collectors.toList());
        // Retornar la lista de cuotas
        return cuotaList;
    }

    @Override
    public Cuota traerCuotaMasCercanaAPagar(Long idPrestamo) {
        // Verificar que exista el prestamo
        Prestamo prestamo = prestamoRepository.findById(idPrestamo).orElseThrow(
                () -> new ResourceNotFoundException("El tipo de documento indicado no existe")
        );
        // Buscar las cuotas por id de Prestamo
        Iterable<Cuota> cuotas = cuotaRepository.findByPrestamoIdPrestamo(idPrestamo);
        // Convertir el iterable a una lista
        List<Cuota> cuotaList = StreamSupport.stream(cuotas.spliterator(), false).collect(Collectors.toList());
        // Extraer las cuotas pendientes
        List<Cuota> cuotasPendientes = new ArrayList<>();
        // Optimizacion con stream
        cuotasPendientes = cuotaList.stream().filter(cuota -> !cuota.getEstado()).collect(Collectors.toList());
        /*
        for(Cuota cuota:cuotaList){
            if(!cuota.getEstado()){
                cuotasPendientes.add(cuota);
            }
        }
         */
        // Validar que existan cuotas pendientes
        if(cuotasPendientes.isEmpty()){
            throw  new ResourceNotFoundException("No hay cuotas pendientes");
        }
        // Obtener la cuota mas cerna a pagar de la lista
        Cuota cuotaMasProxima = cuotasPendientes.get(0);
        // Retornar la cuota
        return cuotaMasProxima;
    }

    @Override
    public Cuota traerCuotaPorId(Long idCuota) {
        Cuota cuota = cuotaRepository.findById(idCuota).orElseThrow(
                () -> new ResourceNotFoundException("No existe una cuota con el id indicado")
        );
        return cuota;
    }

    @Override
    public Cuota cambiarEstadoCuota(Long idCuota) {

        Cuota cuota = cuotaRepository.findById(idCuota).orElseThrow(
                () -> new ResourceNotFoundException("No existe una cuota con el id indicado")
        );

        cuota.setEstado(true);
        cuota.setFechaPagoReal(LocalDate.now());

        cuotaRepository.save(cuota);

        return cuota;
    }


}
