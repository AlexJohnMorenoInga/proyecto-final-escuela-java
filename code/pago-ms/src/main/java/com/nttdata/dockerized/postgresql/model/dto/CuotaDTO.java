package com.nttdata.dockerized.postgresql.model.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CuotaDTO {

    private Long idCuota;

    private LocalDate fechaPagoPrevista;

    private Double capitalCuota;

    private Double interesCuota;

    private Boolean estado; //0:pendiente, 1:pagado

    private Integer numeradorCuota;

    private LocalDate fechaPagoReal;

}
