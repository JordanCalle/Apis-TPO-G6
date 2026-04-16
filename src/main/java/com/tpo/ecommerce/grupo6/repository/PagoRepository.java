package com.tpo.ecommerce.grupo6.repository;

import com.tpo.ecommerce.grupo6.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    // Custom queries can be added here if needed
}