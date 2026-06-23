package com.example.ms_mesas.controller;

import com.example.ms_mesas.model.Mesa;
import com.example.ms_mesas.service.MesaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
@CrossOrigin("*")                   // Esto sirve para que el microservicio inventario tome la peticion cruzada del puerto 8080 (el apigateway) y dija, si mi rey!
@RequiredArgsConstructor
public class MesaController {

    private final MesaService mesaService;

    @GetMapping
    public ResponseEntity<List<Mesa>> getMesas(@RequestParam(required = false) String estado){
        if (estado != null) {
            return ResponseEntity.ok(mesaService.findByEstado(estado));
        }
        return ResponseEntity.ok(mesaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mesa> getMesaById(@PathVariable Long id){
        return mesaService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Mesa> saveMesa(@Valid @RequestBody Mesa mesa){
        return ResponseEntity.status(HttpStatus.CREATED).body(mesaService.save(mesa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mesa> updateMesa(@PathVariable Long id, @Valid @RequestBody Mesa mesa) {
        return mesaService.update(id, mesa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMesaById(@PathVariable Long id){
        if (mesaService.findById(id).isPresent()) {
            mesaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}