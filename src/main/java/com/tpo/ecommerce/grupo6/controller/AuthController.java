package com.tpo.ecommerce.grupo6.controller;

import com.tpo.ecommerce.grupo6.dto.UsuarioDTO;
import com.tpo.ecommerce.grupo6.security.AuthService;
import com.tpo.ecommerce.grupo6.security.dto.AuthRequest;
import com.tpo.ecommerce.grupo6.security.dto.AuthResponse;
import com.tpo.ecommerce.grupo6.security.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse authResponse = authService.authenticate(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> register(@RequestBody RegisterRequest request) {
        UsuarioDTO usuarioDTO = authService.register(request);
        return ResponseEntity.ok(usuarioDTO);
    }
}