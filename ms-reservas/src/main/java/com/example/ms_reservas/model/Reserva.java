package com.example.ms_reservas.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
@Getter
@Setter
@NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "El ID del cliente es obligatorio.")
    private Long clienteId;

    @NotNull(message = "El ID de la mesa es obligatorio.")
    private Long mesaId;

    @NotNull(message = "La fecha y hora de la reserva es obligatoria.")
    @FutureOrPresent(message = "La reserva debe ser en el futuro o en el presente.")
    private LocalDateTime fechaHora;

    @NotNull(message = "La cantidad de personas es obligatoria.")
    @Min(value = 1, message = "La reserva debe ser para al menos 1 persona.")
    private Integer cantidadPersonas;

    private String estado;

    @PrePersist
    public void prePersist() {
        if (this.estado == null || this.estado.trim().isEmpty()) {
            this.estado = "PENDIENTE";
        }
    }
}
