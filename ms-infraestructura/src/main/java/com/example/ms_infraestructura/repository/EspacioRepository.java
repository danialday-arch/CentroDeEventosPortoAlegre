package com.example.ms_infraestructura.repository;

import com.example.ms_infraestructura.model.Espacio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspacioRepository extends JpaRepository<Espacio, Long> {
}