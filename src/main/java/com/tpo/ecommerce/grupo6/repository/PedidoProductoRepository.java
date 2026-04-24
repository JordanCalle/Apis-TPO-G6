package com.tpo.ecommerce.grupo6.repository;

import com.tpo.ecommerce.grupo6.model.PedidoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoProductoRepository extends JpaRepository<PedidoProducto, Long> {
    List<PedidoProducto> findByPedidoId(Long pedidoId);
}