package com.nttdata.dockerized.postgresql.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tipo_documento")
public class TipoDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_tipo_documento")
    private Long idTipoDocumento;

    @Column(unique = true, nullable = false)
    private String nombre;

}
