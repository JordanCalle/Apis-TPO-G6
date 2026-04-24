package com.tpo.ecommerce.grupo6.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UpdatePedidoDTO {
    private LocalDateTime fechaPedido;
    private BigDecimal total;
}
