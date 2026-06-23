package com.example.ms_orden_cocina.controller;

import com.example.ms_orden_cocina.dto.OrdenTrabajoDTO;
import com.example.ms_orden_cocina.model.OrdenCocina;
import com.example.ms_orden_cocina.service.OrdenCocinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cocina")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CocinaController {

    private final OrdenCocinaService service;

    // CREATE
    @PostMapping("/ordenes")
    public ResponseEntity<Void> recibirOrden(@RequestBody OrdenTrabajoDTO orden) {
        service.recibirOrden(orden);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // READ ALL
    @GetMapping("/ordenes")
    public ResponseEntity<List<OrdenCocina>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // READ BY ID
    @GetMapping("/ordenes/{id}")
    public ResponseEntity<OrdenCocina> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE ESTADO
    @PutMapping("/ordenes/{id}/estado")
    public ResponseEntity<OrdenCocina> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        try {
            return ResponseEntity.ok(service.actualizarEstado(id, estado));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE
    @DeleteMapping("/ordenes/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}