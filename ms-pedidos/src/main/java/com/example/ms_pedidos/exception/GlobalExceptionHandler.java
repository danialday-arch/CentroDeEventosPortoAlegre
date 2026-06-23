package com.example.ms_pedidos.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import feign.FeignException;
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

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Atrapa errores logicos (@NotBlank, @Positive, etc.) [TU CÓDIGO]
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

    // 2. Atrapa errores de formato JSON [TU CÓDIGO]
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, String> handleJsonMappingExceptions(HttpMessageNotReadableException ex) {
        Map<String, String> errors = new HashMap<>();
        Throwable cause = ex.getMostSpecificCause();

        if (cause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) cause;
            String fieldName = ife.getPath().stream()
                    .map(ref -> ref.getFieldName() != null ? ref.getFieldName() : "[" + ref.getIndex() + "]")
                    .collect(Collectors.joining("."));

            Object valorEquivocado = ife.getValue();
            errors.put(fieldName, "Ingreso un valor invalido ('" + valorEquivocado + "'). Se esperaba un tipo de dato numerico.");

        } else if (cause instanceof JsonParseException) {
            JsonParseException jpe = (JsonParseException) cause;
            String detalleExacto = jpe.getOriginalMessage();

            errors.put("error_sintaxis", "Error de sintaxis en la peticion. Verifique el formato JSON, comillas faltantes o caracteres sueltos.");
            errors.put("pista_tecnica", detalleExacto);

        } else {
            errors.put("error_general", "Estructura JSON invalida.");
            if (cause != null) {
                errors.put("detalle", cause.getMessage());
            }
        }
        return errors;
    }

    // 3. Atrapa errores de Feign (Falta de Stock o 404 Catalogo) [NUEVO]
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FeignException.class)
    public Map<String, String> handleFeignException(FeignException ex) {
        Map<String, String> errors = new HashMap<>();

        if (ex.status() == 404) {
            errors.put("error", "El producto solicitado no existe en el catálogo.");
        } else if (ex.getMessage() != null && ex.getMessage().contains("Stock insuficiente")) {
            errors.put("error", "No hay stock suficiente en el inventario para completar este pedido.");
        } else {
            errors.put("error", "Error de comunicación interna con otros microservicios.");
        }
        return errors;
    }

    // 4. Atrapa errores generales de validacion interna [NUEVO]
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, String> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return errors;
    }
}