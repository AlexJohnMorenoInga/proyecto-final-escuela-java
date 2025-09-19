package com.nttdata.dockerized.postgresql.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_pago")
    private Long idPago;

    @Column(name="id_cuota", nullable = false)
    private Long idCuota;

    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;

    @Column(name = "pago_capital")
    private Double pagoCapital;

    @Column(name = "pago_interes")
    private Double pagoInteres;

}
