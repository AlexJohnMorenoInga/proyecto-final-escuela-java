package com.nttdata.dockerized.postgresql.model.dto;

import com.nttdata.dockerized.postgresql.model.entity.TipoDocumento;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuardarClienteResponseDTO {

    private Long idCliente;

    private String nombre;

    private String apellido;

    private TipoDocumento tipoDocumento;

    private String numeroDocumento;

    private String email;

    private String celular;

    private LocalDateTime fechaCreacion;

}
