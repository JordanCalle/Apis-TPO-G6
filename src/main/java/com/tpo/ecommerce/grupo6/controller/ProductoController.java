package com.tpo.ecommerce.grupo6.controller;

import com.tpo.ecommerce.grupo6.dto.CreateProductoDTO;
import com.tpo.ecommerce.grupo6.dto.ProductoDTO;
import com.tpo.ecommerce.grupo6.dto.UpdateProductoDTO;
import com.tpo.ecommerce.grupo6.mapper.ProductoMapper;
import com.tpo.ecommerce.grupo6.model.Producto;
import com.tpo.ecommerce.grupo6.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoMapper productoMapper;

    @GetMapping
    public List<ProductoDTO> getAllProductos() {
        return productoMapper.toDTOList(productoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        return ResponseEntity.ok(productoMapper.toDTO(producto));
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<Map<String, Object>> verificarStock(@PathVariable Long id,
                                                              @RequestParam(required = false) Integer cantidad) {
        Producto producto = productoService.findById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("productoId", producto.getId());
        response.put("stock", producto.getStock());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ProductoDTO createProducto(@RequestBody CreateProductoDTO createDTO) {
        Producto producto = productoMapper.toEntity(createDTO);
        Producto savedProducto = productoService.save(producto);
        return productoMapper.toDTO(savedProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> updateProducto(@PathVariable Long id, @RequestBody UpdateProductoDTO updateDTO) {
        Producto producto = productoService.findById(id);
        productoMapper.updateEntity(updateDTO, producto);
        Producto updatedProducto = productoService.save(producto);
        return ResponseEntity.ok(productoMapper.toDTO(updatedProducto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}