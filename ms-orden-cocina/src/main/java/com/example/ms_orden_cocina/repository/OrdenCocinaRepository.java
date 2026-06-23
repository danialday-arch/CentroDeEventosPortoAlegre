package com.example.ms_orden_cocina.repository;

import com.example.ms_orden_cocina.model.OrdenCocina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenCocinaRepository extends JpaRepository<OrdenCocina, Long> {
}