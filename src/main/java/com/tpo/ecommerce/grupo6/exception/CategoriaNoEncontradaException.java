package com.tpo.ecommerce.grupo6.exception;

public class CategoriaNoEncontradaException extends RuntimeException {

    public CategoriaNoEncontradaException() {
        super("Categoría no encontrada");
    }

    public CategoriaNoEncontradaException(String message) {
        super(message);
    }
}
