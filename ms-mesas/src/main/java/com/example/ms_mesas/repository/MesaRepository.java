package com.example.ms_mesas.repository;

import com.example.ms_mesas.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {

    // Metodo para get por estado
    public List<Mesa> findByEstado(String estado);
}