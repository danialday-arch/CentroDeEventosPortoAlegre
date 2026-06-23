package com.example.ms_inventario.controller;

import com.example.ms_inventario.model.Insumo;
import com.example.ms_inventario.service.InsumoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin("*")                   // Esto sirve para que el microservicio inventario tome la peticion cruzada del puerto 8080 (el apigateway) y dija, si mi rey!
@RequiredArgsConstructor
public class InsumoController {

    private final InsumoService insumoService;

    @GetMapping
    public ResponseEntity<List<Insumo>> getAll() {
        return ResponseEntity.ok(insumoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Insumo> getById(@PathVariable Long id) {
        return insumoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Insumo> create(@Valid @RequestBody Insumo insumo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(insumoService.save(insumo));
    }

    // Ruta especial que ms-mesas consumirá
    @PutMapping("/{id}/asignar")
    public ResponseEntity<Insumo> asignarStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        return ResponseEntity.ok(insumoService.asignarAMesa(id, cantidad));
    }

    // Ruta especial para cuando se levanta una mesa y se devuelve el menaje al stock de bodega
    @PutMapping("/{id}/liberar")
    public ResponseEntity<Insumo> liberarStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        return ResponseEntity.ok(insumoService.liberarDeMesa(id, cantidad));
    }

    // Ruta para modificar la cantidad de existencias totales de algun item de la bodega
    @PutMapping("/{id}/ingresar")
    public ResponseEntity<Insumo> ingresarStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        return ResponseEntity.ok(insumoService.ingresarStock(id, cantidad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        insumoService.deleteById(id);
        return ResponseEntity.noContent().build(); // Devuelve 204 No Content si fue exitoso
    }
}