package com.tpo.ecommerce.grupo6.dto;


import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class CreatePedidoDTO {
    private BigDecimal total;
    private Long usuarioId;
    private List<Long> productosIds;
    
}
