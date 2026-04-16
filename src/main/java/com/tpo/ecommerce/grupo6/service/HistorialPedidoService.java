package com.tpo.ecommerce.grupo6.service;

import com.tpo.ecommerce.grupo6.model.HistorialPedido;
import com.tpo.ecommerce.grupo6.repository.HistorialPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialPedidoService {

    @Autowired
    private HistorialPedidoRepository historialPedidoRepository;

    public List<HistorialPedido> findAll() {
        return historialPedidoRepository.findAll();
    }

    public Optional<HistorialPedido> findById(Long id) {
        return historialPedidoRepository.findById(id);
    }

    public HistorialPedido save(HistorialPedido historialPedido) {
        return historialPedidoRepository.save(historialPedido);
    }

    public void deleteById(Long id) {
        historialPedidoRepository.deleteById(id);
    }
}