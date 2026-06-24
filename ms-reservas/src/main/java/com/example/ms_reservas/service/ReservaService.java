package com.example.ms_reservas.service;

import com.example.ms_reservas.model.Reserva;
import com.example.ms_reservas.repository.ReservaRepository;
import com.example.ms_reservas.feign.ClienteClient;
import com.example.ms_reservas.feign.MesaClient;
import feign.FeignException; // Importante: importar para capturar errores de Feign
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository repository;
    private final ClienteClient clienteClient;
    private final MesaClient mesaClient;

    public List<Reserva> findAll() {
        return repository.findAll();
    }

    public Optional<Reserva> findById(Long id) {
        return repository.findById(id);
    }

    public Reserva save(Reserva reserva) {
        // 1. Validar Cliente
        try {
            clienteClient.obtenerClientePorId(reserva.getClienteId());
        } catch (FeignException e) {
            throw new IllegalArgumentException("Error al validar Cliente (ID " + reserva.getClienteId() + "): " + e.status() + " - " + e.getMessage());
        }

        // 2. Validar Mesa
        try {
            mesaClient.obtenerMesaPorId(reserva.getMesaId());
        } catch (FeignException e) {
            // Esto nos dirá si es un 404 real o un error de conexión
            throw new IllegalArgumentException("Error al validar Mesa (ID " + reserva.getMesaId() + "): " + e.status() + " - " + e.getMessage());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error de conexión inesperado: " + e.getMessage());
        }

        // 3. Guardar
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