package com.tpo.ecommerce.grupo6.controller;

import com.tpo.ecommerce.grupo6.dto.CreateUsuarioDTO;
import com.tpo.ecommerce.grupo6.dto.UpdateUsuarioDTO;
import com.tpo.ecommerce.grupo6.dto.UsuarioDTO;
import com.tpo.ecommerce.grupo6.mapper.UsuarioMapper;
import com.tpo.ecommerce.grupo6.model.Usuario;
import com.tpo.ecommerce.grupo6.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @GetMapping
    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioMapper.toDTOList(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id) {
        return usuarioService.findById(id)
                .map(usuario -> ResponseEntity.ok(usuarioMapper.toDTO(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public UsuarioDTO createUsuario(@RequestBody CreateUsuarioDTO createDTO) {
        Usuario usuario = usuarioMapper.toEntity(createDTO);
        Usuario savedUsuario = usuarioService.save(usuario);
        return usuarioMapper.toDTO(savedUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Long id, @RequestBody UpdateUsuarioDTO updateDTO) {
        return usuarioService.findById(id)
                .map(usuario -> {
                    usuarioMapper.updateEntity(updateDTO, usuario);
                    Usuario updatedUsuario = usuarioService.save(usuario);
                    return ResponseEntity.ok(usuarioMapper.toDTO(updatedUsuario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        if (usuarioService.findById(id).isPresent()) {
            usuarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}