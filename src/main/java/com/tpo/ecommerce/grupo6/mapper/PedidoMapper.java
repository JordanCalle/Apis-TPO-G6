package com.tpo.ecommerce.grupo6.mapper;

import com.tpo.ecommerce.grupo6.dto.CreatePedidoDTO;
import com.tpo.ecommerce.grupo6.dto.PedidoDTO;
import com.tpo.ecommerce.grupo6.dto.UpdatePedidoDTO;
import com.tpo.ecommerce.grupo6.model.Pedido;
import com.tpo.ecommerce.grupo6.model.Producto;
import com.tpo.ecommerce.grupo6.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    @Autowired
    private ProductoMapper productoMapper;

    public PedidoDTO toDTO(Pedido pedido) {
        if (pedido == null) {
            return null;
        }
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setTotal(pedido.getTotal());
        if (pedido.getUsuario() != null) {
            dto.setUsuarioId(pedido.getUsuario().getId());
            dto.setUsuarioNombre(pedido.getUsuario().getNombre());
        }
        if (pedido.getProductos() != null) {
            dto.setProductos(pedido.getProductos().stream()
                    .map(productoMapper::toDTO)
                    .collect(Collectors.toList()));
        }
        if (pedido.getPago() != null) {
            dto.setPagoId(pedido.getPago().getId());
        }
        if (pedido.getEnvio() != null) {
            dto.setEnvioId(pedido.getEnvio().getId());
        }
        return dto;
    }

    public List<PedidoDTO> toDTOList(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Pedido toEntity(CreatePedidoDTO dto) {
        if (dto == null) {
            return null;
        }
        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        if (dto.getUsuarioId() != null) {
            Usuario usuario = new Usuario();
            usuario.setId(dto.getUsuarioId());
            pedido.setUsuario(usuario);
        }
        if (dto.getProductosIds() != null && !dto.getProductosIds().isEmpty()) {
            List<Producto> productos = dto.getProductosIds().stream()
                    .map(id -> {
                        Producto p = new Producto();
                        p.setId(id);
                        return p;
                    })
                    .collect(Collectors.toList());
            pedido.setProductos(productos);
        }
        return pedido;
    }

    public void updateEntity(UpdatePedidoDTO dto, Pedido pedido) {
        if (dto == null) {
            return;
        }
        if (dto.getFechaPedido() != null) {
            pedido.setFechaPedido(dto.getFechaPedido());
        }
        if (dto.getTotal() != null) {
            pedido.setTotal(dto.getTotal());
        }
        if (dto.getProductosIds() != null && !dto.getProductosIds().isEmpty()) {
            List<Producto> productos = dto.getProductosIds().stream()
                    .map(id -> {
                        Producto p = new Producto();
                        p.setId(id);
                        return p;
                    })
                    .collect(Collectors.toList());
            pedido.setProductos(productos);
        }
    }
}
