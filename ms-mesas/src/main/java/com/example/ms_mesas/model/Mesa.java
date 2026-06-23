package com.example.ms_mesas.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "mesas")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)

    private Long id;

    @NotNull(message = "El ID del espacio es obligatorio")
    private Long espacioId;

    @NotNull(message = "El número de mesa es obligatorio.")
    @Min(value = 1, message = "El número de mesa debe ser 1 o superior.")
    private Integer numero;

    private String estado;
    @NotEmpty(message = "La mesa debe tener al menos un utensilio asignado.")
    @Valid

    // Relacion 1 a muchos
    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Utensilio> utensilios;
}
