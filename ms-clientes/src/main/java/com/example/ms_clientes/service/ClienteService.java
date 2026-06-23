package com.example.ms_clientes.service;

import com.example.ms_clientes.model.Cliente;
import com.example.ms_clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> update(Long id, Cliente clienteActualizado) {
        return clienteRepository.findById(id).map(clienteExistente -> {
            clienteExistente.setNombre(clienteActualizado.getNombre());
            clienteExistente.setEmail(clienteActualizado.getEmail());
            clienteExistente.setTelefono(clienteActualizado.getTelefono());
            return clienteRepository.save(clienteExistente);
        });
    }

    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }
}