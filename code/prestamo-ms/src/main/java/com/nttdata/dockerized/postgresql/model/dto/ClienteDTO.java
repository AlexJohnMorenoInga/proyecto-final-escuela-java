package com.nttdata.dockerized.postgresql.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

    private Long idCliente;

    private String nombre;

    private String apellido;

    private String numeroDocumento;

    private String email;

    private String celular;

    private Boolean active;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;

}
