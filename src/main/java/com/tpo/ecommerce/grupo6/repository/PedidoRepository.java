package com.tpo.ecommerce.grupo6.repository;

import com.tpo.ecommerce.grupo6.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}