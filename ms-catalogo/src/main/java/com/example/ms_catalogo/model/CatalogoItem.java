package com.example.ms_catalogo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "catalogo_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "El nombre del producto no puede estar vacio")
    private String nombreProducto;

    @Column(nullable = false)
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private Double precio;

    // AQUI ESTA EL CAMPO NUEVO QUE NECESITA PEDIDOS
    @Column(nullable = false)
    @NotBlank(message = "El area es obligatoria (Debe ser 'COCINA' o 'BAR')")
    private String area;

    @ElementCollection
    @NotEmpty(message = "La receta no puede estar vacia")
    @Valid
    @CollectionTable(name = "catalogo_recetas", joinColumns = @JoinColumn(name = "catalogo_id"))
    private List<RecetaInsumo> receta;
}