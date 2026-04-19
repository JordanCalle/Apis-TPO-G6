package com.tpo.ecommerce.grupo6.exception;

public class EmailExistenteException extends RuntimeException {

    public EmailExistenteException() {
        super("El email ya existe");
    }

    public EmailExistenteException(String message) {
        super(message);
    }

}
