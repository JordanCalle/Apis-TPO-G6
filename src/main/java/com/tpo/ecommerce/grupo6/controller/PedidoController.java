package com.tpo.ecommerce.grupo6.controller;

import com.tpo.ecommerce.grupo6.dto.CheckoutDTO;
import com.tpo.ecommerce.grupo6.dto.CreatePedidoDTO;
import com.tpo.ecommerce.grupo6.dto.PedidoDTO;
import com.tpo.ecommerce.grupo6.dto.UpdatePedidoDTO;
import com.tpo.ecommerce.grupo6.mapper.PedidoMapper;
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

    @Autowired
    private PedidoMapper pedidoMapper;

    @GetMapping
    public List<PedidoDTO> getAllPedidos() {
        return pedidoMapper.toDTOList(pedidoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> getPedidoById(@PathVariable Long id) {
        return pedidoService.findById(id)
                .map(pedido -> ResponseEntity.ok(pedidoMapper.toDTO(pedido)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/checkout")
    public ResponseEntity<PedidoDTO> checkout(@RequestBody CheckoutDTO checkoutDTO) {
        Pedido pedido = pedidoService.checkout(checkoutDTO);
        PedidoDTO pedidoDTO = pedidoMapper.toDTO(pedido);
        return ResponseEntity.ok(pedidoDTO);
    }

    @PostMapping
    public PedidoDTO createPedido(@RequestBody CreatePedidoDTO createDTO) {
        Pedido pedido = pedidoMapper.toEntity(createDTO);
        Pedido savedPedido = pedidoService.save(pedido);
        return pedidoMapper.toDTO(savedPedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> updatePedido(@PathVariable Long id, @RequestBody UpdatePedidoDTO updateDTO) {
        return pedidoService.findById(id)
                .map(pedido -> {
                    pedidoMapper.updateEntity(updateDTO, pedido);
                    Pedido updatedPedido = pedidoService.save(pedido);
                    return ResponseEntity.ok(pedidoMapper.toDTO(updatedPedido));
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