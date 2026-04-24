package com.tpo.ecommerce.grupo6.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tpo.ecommerce.grupo6.dto.CreateProductoDTO;
import com.tpo.ecommerce.grupo6.dto.ProductoDTO;
import com.tpo.ecommerce.grupo6.dto.UpdateProductoDTO;
import com.tpo.ecommerce.grupo6.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<ProductoDTO> getAllProductos() {
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<Map<String, Object>> verificarStock(@PathVariable Long id,
                                                              @RequestParam(required = false) Integer cantidad) {
        ProductoDTO producto = productoService.findById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("productoId", producto.getId());
        response.put("stock", producto.getStock());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> createProducto(@RequestBody CreateProductoDTO createDTO) {
        return ResponseEntity.ok(productoService.createProducto(createDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> updateProducto(@PathVariable Long id, @RequestBody UpdateProductoDTO updateDTO) {
        return ResponseEntity.ok(productoService.updateProducto(id, updateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}