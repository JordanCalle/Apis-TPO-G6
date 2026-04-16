package com.tpo.ecommerce.grupo6.controller;

import com.tpo.ecommerce.grupo6.model.Envio;
import com.tpo.ecommerce.grupo6.service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @GetMapping
    public List<Envio> getAllEnvios() {
        return envioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Envio> getEnvioById(@PathVariable Long id) {
        return envioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Envio createEnvio(@RequestBody Envio envio) {
        return envioService.save(envio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Envio> updateEnvio(@PathVariable Long id, @RequestBody Envio envioDetails) {
        return envioService.findById(id)
                .map(envio -> {
                    envio.setDireccion(envioDetails.getDireccion());
                    envio.setFechaEnvio(envioDetails.getFechaEnvio());
                    envio.setEstado(envioDetails.getEstado());
                    envio.setPedido(envioDetails.getPedido());
                    Envio updatedEnvio = envioService.save(envio);
                    return ResponseEntity.ok(updatedEnvio);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnvio(@PathVariable Long id) {
        if (envioService.findById(id).isPresent()) {
            envioService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}