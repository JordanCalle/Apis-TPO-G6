package com.tpo.ecommerce.grupo6.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "envios")
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String direccion;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @Column(nullable = false)
    private String estado;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

}