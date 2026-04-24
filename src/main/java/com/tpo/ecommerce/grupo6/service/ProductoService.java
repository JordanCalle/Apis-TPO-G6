package com.tpo.ecommerce.grupo6.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpo.ecommerce.grupo6.dto.CreateProductoDTO;
import com.tpo.ecommerce.grupo6.dto.ProductoDTO;
import com.tpo.ecommerce.grupo6.dto.UpdateProductoDTO;
import com.tpo.ecommerce.grupo6.exception.ProductoNoEncontradoException;
import com.tpo.ecommerce.grupo6.mapper.ProductoMapper;
import com.tpo.ecommerce.grupo6.model.Producto;
import com.tpo.ecommerce.grupo6.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoMapper productoMapper;

    public List<ProductoDTO> findAll() {
        return productoMapper.toDTOList(productoRepository.findAll());
    }

    public ProductoDTO findById(Long id) {
    Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado con id: " + id));

        return productoMapper.toDTO(producto);
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

    public ProductoDTO createProducto(CreateProductoDTO dto) {
    Producto producto = productoMapper.toEntity(dto);
    Producto savedProducto = productoRepository.save(producto);

    return productoMapper.toDTO(savedProducto);
    }

    public ProductoDTO updateProducto(Long id, UpdateProductoDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado con id: " + id));

        productoMapper.updateEntity(dto, producto);
        Producto updatedProducto = productoRepository.save(producto);

        return productoMapper.toDTO(updatedProducto);
    }
}