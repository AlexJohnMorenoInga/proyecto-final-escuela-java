package com.nttdata.dockerized.postgresql.model.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuardarClienteRequestDTO {

    private String nombre;

    private String apellido;

    private String numeroDocumento;

    private String email;

    private String celular;

}
