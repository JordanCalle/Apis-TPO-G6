package com.tpo.ecommerce.grupo6.dto;

import java.time.LocalDateTime;

import com.tpo.ecommerce.grupo6.security.Role;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String email;
    private Role role;
    private LocalDateTime fechaRegistro;
}
