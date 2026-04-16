package com.tpo.ecommerce.grupo6.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Pedido> pedidos;

}