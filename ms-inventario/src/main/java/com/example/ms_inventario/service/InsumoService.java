package com.example.ms_inventario.service;

import com.example.ms_inventario.model.Insumo;
import com.example.ms_inventario.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InsumoService {

    private final InsumoRepository insumoRepository;

    public List<Insumo> findAll() {
        return insumoRepository.findAll();
    }

    public Optional<Insumo> findById(Long id) {
        return insumoRepository.findById(id);
    }

    public Insumo save(Insumo insumo) {
        return insumoRepository.save(insumo);
    }

    // Metodo clave para que se descuenten cosss
    public Insumo asignarAMesa(Long id, Integer cantidadRequerida) {
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El insumo con ID " + id + " no existe."));

        if (insumo.getStockDisponible() < cantidadRequerida) {
            throw new IllegalArgumentException("Stock insuficiente de " + insumo.getNombre() +
                    ". Requerido: " + cantidadRequerida + ", Disponible: " + insumo.getStockDisponible());
        }

        // mueve el stock de la bodega a la mesa
        insumo.setStockDisponible(insumo.getStockDisponible() - cantidadRequerida);
        insumo.setStockEnUso(insumo.getStockEnUso() + cantidadRequerida);

        return insumoRepository.save(insumo);
    }

    public void deleteById(Long id) {
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El insumo con ID " + id + " no existe."));

        // Regla de negocio: No se puede eliminar si hay stock repartido en el recinto
        if (insumo.getStockEnUso() > 0) {
            throw new IllegalArgumentException("No puedes eliminar el insumo '" + insumo.getNombre() +
                    "' porque actualmente hay " + insumo.getStockEnUso() + " unidades en uso en las mesas.");
        }

        insumoRepository.deleteById(id);
    }

    // Metodo para devolver stock a la bodega y asi luego poder eliminar la entidad si es pertinente
    public Insumo liberarDeMesa(Long id, Integer cantidadDevuelta) {
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El insumo con ID " + id + " no existe."));

        if (insumo.getStockEnUso() < cantidadDevuelta) {
            throw new IllegalArgumentException("Error de cuadratura: Intentas devolver " + cantidadDevuelta +
                    " unidades de " + insumo.getNombre() + ", pero el sistema indica que solo hay " +
                    insumo.getStockEnUso() + " en uso.");
        }

        // Movemos el stock de vuelta a la bodega
        insumo.setStockDisponible(insumo.getStockDisponible() + cantidadDevuelta);
        insumo.setStockEnUso(insumo.getStockEnUso() - cantidadDevuelta);

        return insumoRepository.save(insumo);
    }

    // Metodo para cuando el recinto compra más unidades de un insumo existente 
    public Insumo ingresarStock(Long id, Integer cantidadComprada) {
        if (cantidadComprada <= 0) {
            throw new IllegalArgumentException("La cantidad a ingresar debe ser mayor a cero.");
        }

        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El insumo con ID " + id + " no existe."));

        // Aumentamos el patrimonio histórico y lo que hay libre en bodega
        insumo.setStockTotal(insumo.getStockTotal() + cantidadComprada);
        insumo.setStockDisponible(insumo.getStockDisponible() + cantidadComprada);

        return insumoRepository.save(insumo);
    }
}