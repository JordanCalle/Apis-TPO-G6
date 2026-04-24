package com.tpo.ecommerce.grupo6.mapper;

import com.tpo.ecommerce.grupo6.dto.CreatePedidoDTO;
import com.tpo.ecommerce.grupo6.dto.DetallePedidoDTO;
import com.tpo.ecommerce.grupo6.dto.PedidoDTO;
import com.tpo.ecommerce.grupo6.dto.UpdatePedidoDTO;
import com.tpo.ecommerce.grupo6.model.Pedido;
import com.tpo.ecommerce.grupo6.model.PedidoProducto;
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
        if (pedido.getDetalles() != null) {
            dto.setDetalles(pedido.getDetalles().stream()
                    .map(this::convertDetalle)
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
    }

    public DetallePedidoDTO convertDetalle(PedidoProducto detalle) {
        if (detalle == null) {
            return null;
        }
        DetallePedidoDTO dto = new DetallePedidoDTO();
        dto.setId(detalle.getId());
        if (detalle.getProducto() != null) {
            dto.setProductoId(detalle.getProducto().getId());
            dto.setProductoNombre(detalle.getProducto().getNombre());
        }
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubtotal(detalle.getSubtotal());
        return dto;
    }
}
