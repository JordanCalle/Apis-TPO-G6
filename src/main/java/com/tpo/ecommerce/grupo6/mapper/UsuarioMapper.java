package com.tpo.ecommerce.grupo6.mapper;

import com.tpo.ecommerce.grupo6.dto.CreateUsuarioDTO;
import com.tpo.ecommerce.grupo6.dto.UpdateUsuarioDTO;
import com.tpo.ecommerce.grupo6.dto.UsuarioDTO;
import com.tpo.ecommerce.grupo6.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    public UsuarioDTO toDTO(Usuario usuario) {
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

    public List<UsuarioDTO> toDTOList(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Usuario toEntity(CreateUsuarioDTO dto) {
        if (dto == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
        return usuario;
    }

    public void updateEntity(UpdateUsuarioDTO dto, Usuario usuario) {
        if (dto == null) {
            return;
        }
        if (dto.getNombre() != null) {
            usuario.setNombre(dto.getNombre());
        }
        if (dto.getEmail() != null) {
            usuario.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null) {
            usuario.setPassword(dto.getPassword());
        }
    }
}
