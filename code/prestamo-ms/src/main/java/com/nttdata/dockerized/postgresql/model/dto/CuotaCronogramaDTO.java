package com.nttdata.dockerized.postgresql.model.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CuotaCronogramaDTO {

    private Integer numeradorCuota;

    private LocalDate fechaPagoPrevista;

    private Double saldoPrincipalPendiente; // Saldo del capital pendiente

    private Double capitalCuota;

    private Double interesCuota;

    private Double cuota; // Cuota

}
