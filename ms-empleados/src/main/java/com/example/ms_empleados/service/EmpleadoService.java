package com.example.ms_empleados.service;

import com.example.ms_empleados.model.Empleado;
import com.example.ms_empleados.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;

    public List<Empleado> findAll() {
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> findById(Long id) {
        return empleadoRepository.findById(id);
    }

    public Empleado save(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public Optional<Empleado> update(Long id, Empleado empleadoActualizado) {
        return empleadoRepository.findById(id).map(empleadoExistente -> {
            empleadoExistente.setNombre(empleadoActualizado.getNombre());
            empleadoExistente.setRol(empleadoActualizado.getRol());
            empleadoExistente.setDisponibilidad(empleadoActualizado.getDisponibilidad());
            return empleadoRepository.save(empleadoExistente);
        });
    }

    public void deleteById(Long id) {
        empleadoRepository.deleteById(id);
    }
}