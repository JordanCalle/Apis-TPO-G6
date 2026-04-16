package com.tpo.ecommerce.grupo6.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido;

    @Column(nullable = false)
    private BigDecimal total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToMany
    @JoinTable(name = "pedido_producto", joinColumns = @JoinColumn(name = "pedido_id"), inverseJoinColumns = @JoinColumn(name = "producto_id"))
    private List<Producto> productos;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Pago pago;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Envio envio;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<HistorialPedido> historial;

}