package com.tpo.ecommerce.grupo6.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpo.ecommerce.grupo6.dto.CreateUsuarioDTO;
import com.tpo.ecommerce.grupo6.dto.UpdateUsuarioDTO;
import com.tpo.ecommerce.grupo6.dto.UsuarioDTO;
import com.tpo.ecommerce.grupo6.exception.UsuarioNoEncontradoException;
import com.tpo.ecommerce.grupo6.mapper.UsuarioMapper;
import com.tpo.ecommerce.grupo6.model.Usuario;
import com.tpo.ecommerce.grupo6.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public List<UsuarioDTO> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarioMapper.toDTOList(usuarios);
    }

    public Optional<UsuarioDTO> findById(Long id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toDTO);
    }

    public Optional<UsuarioDTO> findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(usuarioMapper::toDTO);
    }

    public UsuarioDTO save(CreateUsuarioDTO dto) {
        Usuario usuario = usuarioMapper.toEntity(dto);
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(savedUsuario);
    }

    public Usuario findEntityById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(
                        "Usuario no encontrado con id: " + id));
    }

    public Optional<UsuarioDTO> update(Long id, UpdateUsuarioDTO dto) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuarioMapper.updateEntity(dto, usuario);
                    Usuario updatedUsuario = usuarioRepository.save(usuario);
                    return usuarioMapper.toDTO(updatedUsuario);
                });
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }
}