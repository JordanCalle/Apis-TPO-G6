package com.tpo.ecommerce.grupo6.controller;

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

    @GetMapping
    public List<Producto> getAllProductos() {
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        return ResponseEntity.ok(producto);
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
    public Producto createProducto(@RequestBody Producto producto) {
        return productoService.save(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @RequestBody Producto productoDetails) {
        Producto producto = productoService.findById(id);

        producto.setNombre(productoDetails.getNombre());
        producto.setDescripcion(productoDetails.getDescripcion());
        producto.setPrecio(productoDetails.getPrecio());
        producto.setStock(productoDetails.getStock());
        producto.setCategoria(productoDetails.getCategoria());

        Producto updatedProducto = productoService.save(producto);
        return ResponseEntity.ok(updatedProducto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}