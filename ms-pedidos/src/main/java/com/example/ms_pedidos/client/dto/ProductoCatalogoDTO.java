package com.example.ms_pedidos.client.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProductoCatalogoDTO {
    private Long id;
    private String nombre;
    private String area; // Fundamental para saber si mandar la orden a COCINA o BAR
    private List<RecetaDTO> receta; // Fundamental para descontar inventario

    @Data
    public static class RecetaDTO {
        private Long insumoId;
        private Integer cantidad;
    }
}