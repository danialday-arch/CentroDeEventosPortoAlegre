package com.example.ms_catalogo.service;

import com.example.ms_catalogo.model.CatalogoItem;
import com.example.ms_catalogo.repository.CatalogoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatalogoService {
    private final CatalogoRepository catalogoRepository;

    public List<CatalogoItem> findAll() {
        return catalogoRepository.findAll();
    }

    public Optional<CatalogoItem> findById(Long id) {
        return catalogoRepository.findById(id);
    }

    public CatalogoItem save(CatalogoItem item) {
        return catalogoRepository.save(item);
    }

    public Optional<CatalogoItem> update(Long id, CatalogoItem itemActualizado) {
        return catalogoRepository.findById(id).map(itemExistente -> {
            itemExistente.setNombreProducto(itemActualizado.getNombreProducto());
            itemExistente.setPrecio(itemActualizado.getPrecio());

            // Lógica añadida para mantener el área actualizada
            itemExistente.setArea(itemActualizado.getArea());

            if (itemActualizado.getReceta() != null) {
                itemExistente.getReceta().clear();
                itemExistente.getReceta().addAll(itemActualizado.getReceta());
            }
            return catalogoRepository.save(itemExistente);
        });
    }

    public void deleteById(Long id) {
        catalogoRepository.deleteById(id);
    }
}