package com.tpo.ecommerce.grupo6.exception;

public class ProductoNoEncontradoException extends RuntimeException{

    public ProductoNoEncontradoException(){
        super("Producto no encontrado");
    }
    
    public ProductoNoEncontradoException(String message) {
        super(message);
    }
}
