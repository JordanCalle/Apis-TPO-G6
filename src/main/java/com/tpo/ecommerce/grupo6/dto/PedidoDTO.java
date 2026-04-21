package com.tpo.ecommerce.grupo6.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class PedidoDTO {
    private Long id;
    private LocalDateTime fechaPedido;
    private BigDecimal total;
    private Long usuarioId;
    private String usuarioNombre;
    private List<ProductoDTO> productos;
    private Long pagoId;
    private Long envioId;
}
