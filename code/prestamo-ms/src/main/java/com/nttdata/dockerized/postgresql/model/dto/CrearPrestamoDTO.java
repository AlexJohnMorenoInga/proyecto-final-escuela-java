package com.nttdata.dockerized.postgresql.model.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrearPrestamoDTO {

    private LocalDate fechaDeAlta;

    private Integer plazo;

    private Double tasa;

    private Double importe;

}
