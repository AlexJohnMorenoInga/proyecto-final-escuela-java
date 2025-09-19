package com.nttdata.dockerized.postgresql.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "prestamo")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_prestamo")
    private Long idPrestamo;

    @Column(name="id_cliente", nullable = false)
    private Long idCliente;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private EstadoPrestamo estado;

    @Column(name = "fecha_de_alta")
    private LocalDate fechaDeAlta;

    @Column(name = "fecha_de_vencimiento")
    private LocalDate fechaDeVencimiento;

    private Integer plazo;

    private Double tasa;

    private Double importe; // ¿Puede ser un BigDecimal?

    @Column(name = "intereses_al_vencimiento")
    private Double interesesAlVencimiento; // ¿Puede ser un BigDecimal?

    @Column(name = "fecha_de_baja")
    private LocalDate fechaDeBaja;

}
