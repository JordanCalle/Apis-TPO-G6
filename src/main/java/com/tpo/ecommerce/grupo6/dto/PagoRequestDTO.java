package com.tpo.ecommerce.grupo6.dto;

import lombok.Data;

@Data
public class PagoRequestDTO {
    private String metodo;  // TARJETA_CREDITO, TRANSFERENCIA, MERCADO_PAGO, etc.
    private String numeroTarjeta;
    private String titular;
    private String fechaVencimiento;
    private String cvv;
}