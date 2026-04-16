package com.tpo.ecommerce.grupo6.controller;

import com.tpo.ecommerce.grupo6.model.HistorialPedido;
import com.tpo.ecommerce.grupo6.service.HistorialPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial-pedidos")
public class HistorialPedidoController {

    @Autowired
    private HistorialPedidoService historialPedidoService;

    @GetMapping
    public List<HistorialPedido> getAllHistorialPedidos() {
        return historialPedidoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialPedido> getHistorialPedidoById(@PathVariable Long id) {
        return historialPedidoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public HistorialPedido createHistorialPedido(@RequestBody HistorialPedido historialPedido) {
        return historialPedidoService.save(historialPedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialPedido> updateHistorialPedido(@PathVariable Long id,
            @RequestBody HistorialPedido historialPedidoDetails) {
        return historialPedidoService.findById(id)
                .map(historialPedido -> {
                    historialPedido.setFechaCambio(historialPedidoDetails.getFechaCambio());
                    historialPedido.setEstado(historialPedidoDetails.getEstado());
                    historialPedido.setDescripcion(historialPedidoDetails.getDescripcion());
                    historialPedido.setPedido(historialPedidoDetails.getPedido());
                    HistorialPedido updatedHistorialPedido = historialPedidoService.save(historialPedido);
                    return ResponseEntity.ok(updatedHistorialPedido);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistorialPedido(@PathVariable Long id) {
        if (historialPedidoService.findById(id).isPresent()) {
            historialPedidoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}