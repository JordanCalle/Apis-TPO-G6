package com.tpo.ecommerce.grupo6.controller;

import com.tpo.ecommerce.grupo6.dto.UsuarioDTO;
import com.tpo.ecommerce.grupo6.security.AuthService;
import com.tpo.ecommerce.grupo6.security.dto.AuthRequest;
import com.tpo.ecommerce.grupo6.security.dto.AuthResponse;
import com.tpo.ecommerce.grupo6.security.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        AuthResponse authResponse = authService.authenticate(request.getEmail(), request.getPassword());

        ResponseCookie cookie = ResponseCookie.from("jwt", authResponse.getToken()) // nombre de la cookie token
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(60) // duracion 1 dia
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of(
                        "usuarioId", authResponse.getUsuarioId(),
                        "nombre", authResponse.getNombre(),
                        "email", authResponse.getEmail()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> register(@RequestBody RegisterRequest request) {
        UsuarioDTO usuarioDTO = authService.register(request);
        return ResponseEntity.ok(usuarioDTO);
    }
}