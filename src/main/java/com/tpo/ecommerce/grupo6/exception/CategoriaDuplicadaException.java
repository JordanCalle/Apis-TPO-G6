package com.tpo.ecommerce.grupo6.exception;

public class CategoriaDuplicadaException extends RuntimeException {

    public CategoriaDuplicadaException() {
        super("La categoría ya existe");
    }

    public CategoriaDuplicadaException(String message) {
        super(message);
    }
}
