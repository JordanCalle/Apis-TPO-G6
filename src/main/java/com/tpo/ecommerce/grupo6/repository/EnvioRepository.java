package com.tpo.ecommerce.grupo6.repository;

import com.tpo.ecommerce.grupo6.model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {

}