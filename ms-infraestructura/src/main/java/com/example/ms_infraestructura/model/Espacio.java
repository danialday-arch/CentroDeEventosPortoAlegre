package com.example.ms_infraestructura.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "espacios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Espacio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "El nombre de la zona o salon es obligatorio")
    private String nombre; // Ej: "Salón Principal", "Terraza", "VIP"

    @Column(nullable = false)
    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad minima es de 1 persona")
    private Integer capacidadPersonas;

    @Column(nullable = false)
    @NotBlank(message = "El estado es obligatorio")
    private String estado; // Ej: "DISPONIBLE", "OCUPADO", "MANTENIMIENTO"

}
