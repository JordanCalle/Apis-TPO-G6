package com.tpo.ecommerce.grupo6.security.dto;

public class AuthResponse {

    private String token;
    private String tokenType = "Bearer";
    private Long usuarioId;
    private String nombre;
    private String email;

    public AuthResponse(String token) {
        this.token = token;
    }

    public AuthResponse(String token, Long usuarioId, String nombre, String email) {
        this.token = token;
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}