package com.nttdata.dockerized.postgresql.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cliente")
    private Long idCliente;

    private String nombre;

    private String apellido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tipo_documento", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private TipoDocumento tipoDocumento;

    @Column(name="numero_documento", unique = true, nullable = false)
    private String numeroDocumento;

    @Column(name="email", unique = true, nullable = false)
    private String email;

    @Column(name="celular", unique = true, nullable = false)
    private String celular;

    private Boolean active;

    @Column(name="fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name="fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

}
