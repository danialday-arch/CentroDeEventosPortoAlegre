package com.example.ms_catalogo.controller;

import com.example.ms_catalogo.model.CatalogoItem;
import com.example.ms_catalogo.service.CatalogoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogo")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CatalogoController {
    private final CatalogoService catalogoService;

    @GetMapping
    public ResponseEntity<List<CatalogoItem>> getCatalogo() {
        return ResponseEntity.ok(catalogoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatalogoItem> getCatalogoById(@PathVariable Long id) {
        return catalogoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CatalogoItem> saveCatalogo(@Valid @RequestBody CatalogoItem item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogoService.save(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CatalogoItem> updateCatalogo(@PathVariable Long id, @Valid @RequestBody CatalogoItem item) {
        return catalogoService.update(id, item)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCatalogoById(@PathVariable Long id) {
        if (catalogoService.findById(id).isPresent()) {
            catalogoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}