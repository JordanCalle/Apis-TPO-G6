package com.tpo.ecommerce.grupo6.dto;

import lombok.Data;

import java.util.List;

@Data
public class CheckoutDTO {
    private Long usuarioId;
    private List<ItemCarritoDTO> items;
    private PagoRequestDTO pago;
}