package com.nttdata.dockerized.postgresql.model.dto;

import com.nttdata.dockerized.postgresql.model.entity.TipoDocumento;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

    private Long idCliente;

    private String nombre;

    private String apellido;

    private TipoDocumento tipoDocumento;

    private String numeroDocumento;

    private String email;

    private String celular;

    private Boolean active;

}
