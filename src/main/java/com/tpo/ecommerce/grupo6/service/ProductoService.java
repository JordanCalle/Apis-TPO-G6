package com.tpo.ecommerce.grupo6.service;

import com.tpo.ecommerce.grupo6.exception.ProductoNoEncontradoException;
import com.tpo.ecommerce.grupo6.model.Producto;
import com.tpo.ecommerce.grupo6.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Producto findById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado con id: " + id));
    }

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public void deleteById(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ProductoNoEncontradoException("No se puede eliminar. Producto no encontrado con id: " + id);
        }
        productoRepository.deleteById(id);
    }
}