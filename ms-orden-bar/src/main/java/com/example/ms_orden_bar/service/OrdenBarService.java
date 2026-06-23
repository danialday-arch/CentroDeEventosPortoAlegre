package com.example.ms_orden_bar.service;

import com.example.ms_orden_bar.dto.OrdenTrabajoDTO;
import com.example.ms_orden_bar.model.OrdenBar;
import com.example.ms_orden_bar.repository.OrdenBarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdenBarService {

    private final OrdenBarRepository repository;

    // CREATE
    public OrdenBar recibirOrden(OrdenTrabajoDTO dto) {
        OrdenBar orden = new OrdenBar();
        orden.setPedidoId(dto.getPedidoId());
        orden.setProductoId(dto.getProductoId());
        orden.setCantidad(dto.getCantidad());
        orden.setComentarios(dto.getComentarios());
        return repository.save(orden);
    }

    // READ ALL
    public List<OrdenBar> findAll() {
        return repository.findAll();
    }

    // READ BY ID
    public Optional<OrdenBar> findById(Long id) {
        return repository.findById(id);
    }

    // UPDATE ESTADO
    public OrdenBar actualizarEstado(Long id, String nuevoEstado) {
        return repository.findById(id).map(orden -> {
            orden.setEstado(nuevoEstado);
            return repository.save(orden);
        }).orElseThrow(() -> new RuntimeException("Orden de bar no encontrada con id: " + id));
    }

    // DELETE
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}