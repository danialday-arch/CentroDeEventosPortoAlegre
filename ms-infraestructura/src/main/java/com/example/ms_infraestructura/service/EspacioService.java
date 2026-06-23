package com.example.ms_infraestructura.service;

import com.example.ms_infraestructura.model.Espacio;
import com.example.ms_infraestructura.repository.EspacioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EspacioService {

    private final EspacioRepository espacioRepository;

    public List<Espacio> findAll() {
        return espacioRepository.findAll();
    }

    public Optional<Espacio> findById(Long id) {
        return espacioRepository.findById(id);
    }

    public Espacio save(Espacio espacio) {
        if (espacio.getEstado() == null) {
            espacio.setEstado("DISPONIBLE"); // Estado por defecto
        }
        return espacioRepository.save(espacio);
    }

    public Optional<Espacio> update(Long id, Espacio espacioActualizado) {
        return espacioRepository.findById(id).map(espacioExistente -> {
            espacioExistente.setNombre(espacioActualizado.getNombre());
            espacioExistente.setCapacidadPersonas(espacioActualizado.getCapacidadPersonas());
            espacioExistente.setEstado(espacioActualizado.getEstado());
            return espacioRepository.save(espacioExistente);
        });
    }

    public void deleteById(Long id) {
        espacioRepository.deleteById(id);
    }
}