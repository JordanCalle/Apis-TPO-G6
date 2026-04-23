package com.tpo.ecommerce.grupo6.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CreateProductoDTO {
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private Long categoriaId;
    
}
