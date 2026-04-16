package com.tpo.ecommerce.grupo6.controller;

import com.tpo.ecommerce.grupo6.model.Pago;
import com.tpo.ecommerce.grupo6.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public List<Pago> getAllPagos() {
        return pagoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> getPagoById(@PathVariable Long id) {
        return pagoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pago createPago(@RequestBody Pago pago) {
        return pagoService.save(pago);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> updatePago(@PathVariable Long id, @RequestBody Pago pagoDetails) {
        return pagoService.findById(id)
                .map(pago -> {
                    pago.setMetodo(pagoDetails.getMetodo());
                    pago.setMonto(pagoDetails.getMonto());
                    pago.setFechaPago(pagoDetails.getFechaPago());
                    pago.setPedido(pagoDetails.getPedido());
                    Pago updatedPago = pagoService.save(pago);
                    return ResponseEntity.ok(updatedPago);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePago(@PathVariable Long id) {
        if (pagoService.findById(id).isPresent()) {
            pagoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}