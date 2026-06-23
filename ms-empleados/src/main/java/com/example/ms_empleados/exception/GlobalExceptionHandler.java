package com.example.ms_empleados.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Hidden // Linea que soluciona problemas con swagger por la version de la libreria del pom, al no encontrar el metodo adecuado de springdoc-openapi (2.5.0), explota
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Atrapa errores logicos (@NotBlank, @Positive, etc.)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, String> handleJsonMappingExceptions(HttpMessageNotReadableException ex) {
        Map<String, String> errors = new HashMap<>();

        // Corrije el fallo de cuando metes un caracter en el id del insumo cuando armas un post o put en el catalogo
        Throwable cause = ex.getMostSpecificCause();

        if (cause instanceof InvalidFormatException) {
            // Entro un string con comillas donde iba un numero
            InvalidFormatException ife = (InvalidFormatException) cause;
            String fieldName = ife.getPath().stream()
                    .map(ref -> ref.getFieldName() != null ? ref.getFieldName() : "[" + ref.getIndex() + "]")
                    .collect(Collectors.joining("."));

            Object valorEquivocado = ife.getValue();
            errors.put(fieldName, "Ingreso un valor invalido ('" + valorEquivocado + "'). Se esperaba un tipo de dato numerico.");

        } else if (cause instanceof JsonParseException) {
            // El JSON esta roto por falta de comillas o mala sintaxis
            JsonParseException jpe = (JsonParseException) cause;
            String detalleExacto = jpe.getOriginalMessage();

            errors.put("error_sintaxis", "Error de sintaxis en la peticion. Verifique el formato JSON, comillas faltantes o caracteres sueltos.");
            errors.put("pista_tecnica", detalleExacto);

        } else {
            // ultimo recurso
            errors.put("error_general", "Estructura JSON invalida.");
            if (cause != null) {
                errors.put("detalle", cause.getMessage());
            }
        }

        return errors;
    }
}