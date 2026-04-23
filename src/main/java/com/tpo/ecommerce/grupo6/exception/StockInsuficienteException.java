package com.tpo.ecommerce.grupo6.exception;

public class StockInsuficienteException extends RuntimeException {
    public StockInsuficienteException(String nombre, String disponible, String solicitado) {
        super("Stock insuficiente para el producto: " + nombre +
                ". Disponible: " + disponible +
                ", Solicitado: " + solicitado);
    }
}