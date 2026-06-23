package com.example.ms_orden_bar.repository;

import com.example.ms_orden_bar.model.OrdenBar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenBarRepository extends JpaRepository<OrdenBar, Long> {
}