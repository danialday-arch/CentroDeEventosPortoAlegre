package com.example.ms_pedidos.repository;

import com.example.ms_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByMesaId(Long mesaId);
    List<Pedido> findByEstado(String estado);
}