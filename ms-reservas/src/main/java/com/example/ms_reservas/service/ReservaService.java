package com.example.ms_reservas.service;

import com.example.ms_reservas.model.Reserva;
import com.example.ms_reservas.repository.ReservaRepository;
import com.example.ms_reservas.feign.ClienteClient;
import com.example.ms_reservas.feign.MesaClient;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository repository;
    private final ClienteClient clienteClient;
    private final MesaClient mesaClient; // Inyectamos el nuevo cliente

    public List<Reserva> findAll() {
        return repository.findAll();
    }

    public Optional<Reserva> findById(Long id) {
        return repository.findById(id);
    }

    public Reserva save(Reserva reserva) {
        // 1. Validar que el Cliente exista
        try {
            clienteClient.obtenerClientePorId(reserva.getClienteId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error: El cliente con ID " + reserva.getClienteId() + " no existe.");
        }

        // 2. Validar que la Mesa exista
        try {
            mesaClient.obtenerMesaPorId(reserva.getMesaId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error: La mesa con ID " + reserva.getMesaId() + " no existe.");
        }

        // 3. Si ambos existen, guardar
        return repository.save(reserva);
    }

    public Reserva actualizarEstado(Long id, String nuevoEstado) {
        return repository.findById(id).map(reserva -> {
            reserva.setEstado(nuevoEstado);
            return repository.save(reserva);
        }).orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}