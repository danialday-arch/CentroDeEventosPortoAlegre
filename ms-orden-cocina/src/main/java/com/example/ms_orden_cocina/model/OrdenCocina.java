package com.example.ms_orden_cocina.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordenes_cocina")
@Getter
@Setter
@NoArgsConstructor
public class OrdenCocina {

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