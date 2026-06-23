package com.example.ms_orden_cocina.dto;

import lombok.Data;

@Data
public class OrdenTrabajoDTO {
    private Long pedidoId;
    private Long productoId;
    private Integer cantidad;
    private String comentarios;
}