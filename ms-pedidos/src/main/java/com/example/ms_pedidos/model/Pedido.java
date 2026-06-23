package com.example.ms_pedidos.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    // --- NUEVO CAMPO AGREGADO ---
    @NotNull(message = "El ID del empleado (mesero) es obligatorio.")
    @Column(name = "empleado_id")
    private Long empleadoId;
    // ----------------------------

    @NotNull(message = "El ID de la mesa es obligatorio.")
    @Column(name = "mesa_id")
    private Long mesaId;

    @NotNull(message = "El total es obligatorio.")
    @Min(value = 0, message = "El total no puede ser negativo.")
    private Double total;

    @NotBlank(message = "El estado del pedido no puede estar vacío.")
    private String estado;

    @Valid
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles = new ArrayList<>();

    public void addDetalle(DetallePedido detalle) {
        detalles.add(detalle);
        detalle.setPedido(this);
    }

    @PrePersist
    public void prePersist() {
        if (this.estado == null || this.estado.trim().isEmpty()) {
            this.estado = "PENDIENTE";
        }
        if (this.total == null) {
            this.total = 0.0;
        }
    }
}