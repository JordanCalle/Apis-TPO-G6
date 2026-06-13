package com.tpo.ecommerce.grupo6.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tpo.ecommerce.grupo6.dto.CheckoutDTO;
import com.tpo.ecommerce.grupo6.dto.CreatePedidoDTO;
import com.tpo.ecommerce.grupo6.dto.PedidoDTO;
import com.tpo.ecommerce.grupo6.dto.UpdatePedidoDTO;
import com.tpo.ecommerce.grupo6.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "http://localhost:5173")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<PedidoDTO> getAllPedidos() {
        return pedidoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> getPedidoById(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.findById(id));
    }

    @PostMapping("/checkout")
    public ResponseEntity<PedidoDTO> checkout(@RequestBody CheckoutDTO checkoutDTO) {
        return ResponseEntity.ok(pedidoService.checkout(checkoutDTO));
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> createPedido(@RequestBody CreatePedidoDTO createDTO) {
        return ResponseEntity.ok(pedidoService.create(createDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePedido(@PathVariable Long id, @RequestBody UpdatePedidoDTO updateDTO) {
        return ResponseEntity.ok(pedidoService.updatePedido(id, updateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}