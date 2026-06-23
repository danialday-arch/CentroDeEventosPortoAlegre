package com.example.ms_inventario.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "insumos")
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "El nombre del insumo no puede estar vacío.")
    private String nombre;

    @NotBlank(message = "La categoría es obligatoria (Ej: Menaje, Ingrediente).")
    private String categoria;

    @NotNull(message = "El stock total es obligatorio.")
    @Min(value = 0, message = "El stock no puede ser negativo.")
    private Integer stockTotal;

    @Min(value = 0, message = "El stock disponible no puede ser negativo.")
    private Integer stockDisponible;

    @Min(value = 0, message = "El stock en uso no puede ser negativo.")
    private Integer stockEnUso;

    // Metodo que se ejecuta justo antes de guardar en base de datos por primera vez
    @PrePersist
    public void prePersist() {
        if (this.stockDisponible == null) {
            this.stockDisponible = this.stockTotal; // Al crearlo, todo está disponible
        }
        if (this.stockEnUso == null) {
            this.stockEnUso = 0; // Al crearlo, nada está en uso
        }
    }
}