package com.tpo.ecommerce.grupo6.service;

import com.tpo.ecommerce.grupo6.model.Envio;
import com.tpo.ecommerce.grupo6.repository.EnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnvioService {

    @Autowired
    private EnvioRepository envioRepository;

    public List<Envio> findAll() {
        return envioRepository.findAll();
    }

    public Optional<Envio> findById(Long id) {
        return envioRepository.findById(id);
    }

    public Envio save(Envio envio) {
        return envioRepository.save(envio);
    }

    public void deleteById(Long id) {
        envioRepository.deleteById(id);
    }
}