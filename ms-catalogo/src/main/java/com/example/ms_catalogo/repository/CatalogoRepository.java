package com.example.ms_catalogo.repository;

import com.example.ms_catalogo.model.CatalogoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogoRepository extends JpaRepository<CatalogoItem, Long> {
}