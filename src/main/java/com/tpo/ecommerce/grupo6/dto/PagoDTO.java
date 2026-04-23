package com.tpo.ecommerce.grupo6.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PagoDTO {
    private Long id;
    private String metodo;
    private BigDecimal monto;
    private LocalDateTime fechaPago;
    private Long pedidoId;
}