package com.tpo.ecommerce.grupo6.controller;

import com.tpo.ecommerce.grupo6.model.Pedido;
import com.tpo.ecommerce.grupo6.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable Long id) {
        return pedidoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pedido createPedido(@RequestBody Pedido pedido) {
        return pedidoService.save(pedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(@PathVariable Long id, @RequestBody Pedido pedidoDetails) {
        return pedidoService.findById(id)
                .map(pedido -> {
                    pedido.setFechaPedido(pedidoDetails.getFechaPedido());
                    pedido.setTotal(pedidoDetails.getTotal());
                    pedido.setUsuario(pedidoDetails.getUsuario());
                    pedido.setProductos(pedidoDetails.getProductos());
                    pedido.setPago(pedidoDetails.getPago());
                    pedido.setEnvio(pedidoDetails.getEnvio());
                    pedido.setHistorial(pedidoDetails.getHistorial());
                    Pedido updatedPedido = pedidoService.save(pedido);
                    return ResponseEntity.ok(updatedPedido);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        if (pedidoService.findById(id).isPresent()) {
            pedidoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}