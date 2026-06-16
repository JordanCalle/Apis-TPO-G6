package com.tpo.ecommerce.grupo6.security;

import com.tpo.ecommerce.grupo6.dto.UsuarioDTO;
import com.tpo.ecommerce.grupo6.exception.EmailExistenteException;
import com.tpo.ecommerce.grupo6.mapper.AuthMapper;
import com.tpo.ecommerce.grupo6.model.Usuario;
import com.tpo.ecommerce.grupo6.repository.UsuarioRepository;
import com.tpo.ecommerce.grupo6.security.dto.AuthResponse;
import com.tpo.ecommerce.grupo6.security.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthMapper authMapper;

    public AuthResponse authenticate(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        String token = jwtUtils.generateToken(authentication.getName());

        // Obtener los datos del usuario
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();

        return new AuthResponse(token, usuario.getId(), usuario.getNombre(), usuario.getEmail());
    }

    public UsuarioDTO register(RegisterRequest request) {
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailExistenteException("El email ya está registrado");
        }
        Usuario usuario = authMapper.registerRequestToEntity(request);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setFechaRegistro(LocalDateTime.now());
        if (usuario.getRole() == null) {
            usuario.setRole(Role.ROLE_USER);
        }
        Usuario savedUser = usuarioRepository.save(usuario);
        return authMapper.entityToDTO(savedUser);
    }
}