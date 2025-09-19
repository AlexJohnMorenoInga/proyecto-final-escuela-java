package com.nttdata.dockerized.postgresql.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cuota")
public class Cuota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cuota")
    private Long idCuota;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_prestamo", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Prestamo prestamo;

    @Column(name = "fecha_pago_prevista")
    private LocalDate fechaPagoPrevista;

    @Column(name = "capital_cuota")
    private Double capitalCuota;

    @Column(name = "interes_cuota")
    private Double interesCuota;

    private Boolean estado; //0:pendiente, 1:pagado

    @Column(name = "numerador_cuota")
    private Integer numeradorCuota;

    @Column(name = "fecha_pago_real")
    private LocalDate fechaPagoReal;

}
