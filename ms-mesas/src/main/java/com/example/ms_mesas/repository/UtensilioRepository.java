package com.example.ms_mesas.repository;

import com.example.ms_mesas.model.Utensilio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtensilioRepository extends JpaRepository<Utensilio, Long> {
}
