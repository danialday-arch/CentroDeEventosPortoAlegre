package com.example.ms_catalogo.model;


import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Embeddable
public class RecetaInsumo {

    @NotNull(message = "El ID del insumo es obligatorio")
    @Positive(message = "El ID del insumo debe ser valido")
    private Long insumoId;
    private Integer cantidad;
}