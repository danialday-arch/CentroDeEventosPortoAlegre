package com.example.ms_clientes.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)

    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo electronico valido")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "El telefono es obligatorio")
    @Pattern(regexp = "^[0-9]+$", message = "El telefono solo puede contener numeros")
    @Size(min = 8, max = 15, message = "El telefono debe tener entre 8 y 15 digitos")
    private String telefono;
}