package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.client.ClienteClient;
import com.nttdata.dockerized.postgresql.exceptions.BadRequestException;
import com.nttdata.dockerized.postgresql.exceptions.ResourceNotFoundException;
import com.nttdata.dockerized.postgresql.model.dto.ClienteDTO;
import com.nttdata.dockerized.postgresql.model.dto.CuotaCronogramaDTO;
import com.nttdata.dockerized.postgresql.model.entity.Cuota;
import com.nttdata.dockerized.postgresql.model.entity.EstadoPrestamo;
import com.nttdata.dockerized.postgresql.model.entity.Prestamo;
import com.nttdata.dockerized.postgresql.repository.CuotaRepository;
import com.nttdata.dockerized.postgresql.repository.PrestamoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PrestamoServiceImpl implements PrestamoService{

    private final PrestamoRepository prestamoRepository;

    private final CuotaRepository cuotaRepository;

    private final ClienteClient clienteClient;

    public PrestamoServiceImpl(PrestamoRepository prestamoRepository, CuotaRepository cuotaRepository, ClienteClient clienteClient) {
        this.prestamoRepository = prestamoRepository;
        this.cuotaRepository = cuotaRepository;
        this.clienteClient = clienteClient;
    }

    @Override
    public Prestamo guardarPrestamo(String numeroDocumento, Prestamo prestamo) {

        if(prestamo.getPlazo()<0 || prestamo.getPlazo()>13){
            throw new BadRequestException("Plazo inválido");
        }
        if(prestamo.getTasa()<0.0 || prestamo.getTasa()>5.5){
            throw new BadRequestException("Tasa inválida");
        }
        if(prestamo.getImporte()<0.0 || prestamo.getImporte()>5000.0){
            throw new BadRequestException("Importe inválido");
        }

        prestamo.getImporte();
        // Verificar que exista el cliente
        ClienteDTO clienteDTO = clienteClient.traerClientePorNumeroDocumento(numeroDocumento);
        // ¿Que pasa si no obtengo a dicho cliente?

        // Generar cronograma de pagos
        List<CuotaCronogramaDTO> cuotaCronogramaDTOList = generarCronogramaDePagos(prestamo.getPlazo(), prestamo.getTasa(), prestamo.getImporte(), prestamo.getFechaDeAlta());
        // Setear valores importantes
        prestamo.setIdCliente(clienteDTO.getIdCliente());
        prestamo.setEstado(EstadoPrestamo.NO_PAGADO); // Los prestamos nacen con estado NO_PAGADO
        prestamo.setFechaDeVencimiento(prestamo.getFechaDeAlta().plusMonths(prestamo.getPlazo()));
        // Guardar el prestamo
        Prestamo prestamoGuardado =prestamoRepository.save(prestamo);
        // Guardar cuotas del prestamo
        for(CuotaCronogramaDTO cuotaCronogramaDTO:cuotaCronogramaDTOList){
            Cuota installment = Cuota.builder()
                    .prestamo(prestamoGuardado)
                    .fechaPagoPrevista(cuotaCronogramaDTO.getFechaPagoPrevista())
                    .capitalCuota(cuotaCronogramaDTO.getCapitalCuota())
                    .interesCuota(cuotaCronogramaDTO.getInteresCuota())
                    .estado(false)
                    .numeradorCuota(cuotaCronogramaDTO.getNumeradorCuota())
                    .fechaPagoReal(null)
                    .build();
            cuotaRepository.save(installment);
        }
        // Retornar el prestamo guardado
        return prestamoGuardado;
    }

    @Override
    public ClienteDTO traerCliente(String numeroDocumento) {
        ClienteDTO clienteDTO = clienteClient.traerClientePorNumeroDocumento(numeroDocumento);
        return clienteDTO;
    }

    @Override
    public List<Prestamo> traerPrestamosPendientes(String numeroDocumento) {
        // Traer al cliente por numero de documento
        ClienteDTO clienteDTO = clienteClient.traerClientePorNumeroDocumento(numeroDocumento);
        // ¿Que pasa si no obtengo a dicho cliente?

        // Buscar los prestamos por id del cliente
        Iterable<Prestamo> prestamos = prestamoRepository.findByIdCliente(clienteDTO.getIdCliente());
        // Convertir el iterable a una lista
        List<Prestamo> prestamoList = StreamSupport.stream(prestamos.spliterator(), false).collect(Collectors.toList());
        // Filtrar los prestamos no pagados o pagados parcialmente
        List<Prestamo> prestamosPendientesList = new ArrayList<>();
        for(Prestamo prestamo : prestamoList){
            if(prestamo.getEstado() != EstadoPrestamo.PAGADO){
                prestamosPendientesList.add(prestamo);
            }
        }
        // Retornar la lista de prestamos pendientes
        return prestamosPendientesList;
    }

    @Override
    public Prestamo cambiarEstadoPrestamo(Long idPrestamo, Integer plazo) {
        // Buscar el prestamo
        Prestamo prestamo = prestamoRepository.findById(idPrestamo).orElseThrow(
                () -> new ResourceNotFoundException("El prestamo indicado no existe")
        );
        // Cambiar el estado del prestamo
        if(prestamo.getPlazo().equals(plazo)){
            prestamo.setEstado(EstadoPrestamo.PAGADO);
            prestamo.setFechaDeBaja(LocalDate.now());
            prestamoRepository.save(prestamo);
        }else {
            prestamo.setEstado(EstadoPrestamo.EN_PROCESO_DE_PAGO);
            prestamoRepository.save(prestamo);
        }
        return prestamo;
    }


    public List<CuotaCronogramaDTO> generarCronogramaDePagos(Integer plazo, Double tasa, Double importe, LocalDate fechaDeAlta){

        //Integer plazo = term;
        //Double tasa = rate;
        //Double importe = amount;

        List<CuotaCronogramaDTO> cuotaCronogramaDTOList = new ArrayList<>();

        if (plazo <= 0 || tasa <= 0 || importe <= 0) {

            throw new BadRequestException("Ingrese los datos correctamente");

        } else {

            // Calculo de la CUOTA
            Double cuota;
            cuota = ((tasa / 100) * importe) / (1 - Math.pow(1 + (tasa / 100), -plazo));

            // Calculo del cronograma de pagos
            Integer contadorMes = 1;
            LocalDate fechaDePagoEsperada = fechaDeAlta.plusMonths(1);
            Double saldo = importe; // SALDO -- es el saldo del prestamo pendiente al momento de hacer un pago de cuota del CRONOGRAMA
            Integer numPago; // NUMERO DE PAGO EN CRONOGRAMA
            LocalDate fechaPago; // FECHA DE PAGO EN CRONOGRAMA
            Double interes; // INTERES -- Es el interes que se paga en cada cuota del CRONOGRAMA
            Double capital; // CAPITAL -- Es la amortizacion del capital que se hace en cada cuota del CRONOGRAMA

            while (contadorMes <= plazo) {

                numPago = contadorMes;
                fechaPago = fechaDePagoEsperada;
                interes = saldo * (tasa / 100);
                capital = cuota - interes;

                CuotaCronogramaDTO cuotaCronogramaDTO = CuotaCronogramaDTO.builder()
                        .numeradorCuota(numPago)
                        .fechaPagoPrevista(fechaPago)
                        .saldoPrincipalPendiente(BigDecimal.valueOf(saldo).setScale(2, RoundingMode.HALF_UP).doubleValue())
                        .capitalCuota(BigDecimal.valueOf(capital).setScale(2, RoundingMode.HALF_UP).doubleValue())
                        .interesCuota(BigDecimal.valueOf(interes).setScale(2, RoundingMode.HALF_UP).doubleValue())
                        .cuota(BigDecimal.valueOf(cuota).setScale(2, RoundingMode.HALF_UP).doubleValue())
                        .build();

                cuotaCronogramaDTOList.add(cuotaCronogramaDTO);

                saldo = saldo - capital;
                contadorMes += 1;
                fechaDePagoEsperada = fechaDePagoEsperada.plusMonths(1); // Aumentar un mes
            }
        }

        return cuotaCronogramaDTOList;

    }






}
