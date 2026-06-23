package com.example.ms_orden_cocina.service;

import com.example.ms_orden_cocina.dto.OrdenTrabajoDTO;
import com.example.ms_orden_cocina.model.OrdenCocina;
import com.example.ms_orden_cocina.repository.OrdenCocinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdenCocinaService {

    private final OrdenCocinaRepository repository;

    // CREATE (El que ya tenías, para recibir desde ms-pedidos)
    public OrdenCocina recibirOrden(OrdenTrabajoDTO dto) {
        OrdenCocina orden = new OrdenCocina();
        orden.setPedidoId(dto.getPedidoId());
        orden.setProductoId(dto.getProductoId());
        orden.setCantidad(dto.getCantidad());
        orden.setComentarios(dto.getComentarios());
        return repository.save(orden);
    }

    // READ ALL
    public List<OrdenCocina> findAll() {
        return repository.findAll();
    }

    // READ BY ID
    public Optional<OrdenCocina> findById(Long id) {
        return repository.findById(id);
    }

    // UPDATE ESTADO (Para que el cocinero marque la orden como "LISTA")
    public OrdenCocina actualizarEstado(Long id, String nuevoEstado) {
        return repository.findById(id).map(orden -> {
            orden.setEstado(nuevoEstado);
            return repository.save(orden);
        }).orElseThrow(() -> new RuntimeException("Orden de cocina no encontrada con id: " + id));
    }

    // DELETE (Por si el mesero cancela la orden)
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}