package com.tpo.ecommerce.grupo6.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistorialPedidoDTO {
    private Long id;
    private LocalDateTime fechaCambio;
    private String estado;
    private String descripcion;
    private Long pedidoId;
}