package com.example.ms_infraestructura.controller;

import com.example.ms_infraestructura.model.Espacio;
import com.example.ms_infraestructura.service.EspacioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/infraestructura")
@CrossOrigin("*") // Para que pase por el API Gateway
@RequiredArgsConstructor
public class EspacioController {

    private final EspacioService espacioService;

    @GetMapping
    public ResponseEntity<List<Espacio>> getEspacios() {
        return ResponseEntity.ok(espacioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Espacio> getEspacioById(@PathVariable Long id) {
        return espacioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Espacio> saveEspacio(@Valid @RequestBody Espacio espacio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(espacioService.save(espacio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Espacio> updateEspacio(@PathVariable Long id, @Valid @RequestBody Espacio espacio) {
        return espacioService.update(id, espacio)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEspacioById(@PathVariable Long id) {
        if (espacioService.findById(id).isPresent()) {
            espacioService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}