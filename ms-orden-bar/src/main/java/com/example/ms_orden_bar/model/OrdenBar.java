package com.example.ms_orden_bar.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordenes_bar")
@Getter
@Setter
@NoArgsConstructor
public class OrdenBar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pedidoId;
    private Long productoId;
    private Integer cantidad;
    private String comentarios;
    private String estado;
    private LocalDateTime fechaRecepcion;

    @PrePersist
    public void prePersist() {
        this.estado = "PENDIENTE";
        this.fechaRecepcion = LocalDateTime.now();
    }
}