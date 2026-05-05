package com.tpo.ecommerce.grupo6.mapper;

import com.tpo.ecommerce.grupo6.dto.UsuarioDTO;
import com.tpo.ecommerce.grupo6.model.Usuario;
import com.tpo.ecommerce.grupo6.security.dto.RegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public Usuario registerRequestToEntity(RegisterRequest request) {
        if (request == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(request.getPassword());
        return usuario;
    }

    public UsuarioDTO entityToDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setRole(usuario.getRole());
        dto.setFechaRegistro(usuario.getFechaRegistro());
        return dto;
    }
}
