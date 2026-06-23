package com.example.ms_mesas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "Utensilios")
public class Utensilio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)

    private Long id;

    @NotBlank(message = "El nombre del utensilio no puede estar vacío ni contener solo espacios.")
    private String name;

    @NotNull(message = "La cantidad es obligatoria.")
    @Min(value = 1, message = "La cantidad debe ser al menos 1.")
    private Integer cantidad;

    @NotNull(message = "El ID del insumo de inventario es obligatorio.")
    @Min(value = 1, message = "El ID del insumo debe ser válido.")
    private Long insumoId; //

    @ManyToOne
    @JoinColumn(name = "mesa_id")
    @JsonIgnore
    private Mesa mesa;
}
