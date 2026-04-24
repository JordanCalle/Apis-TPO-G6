package com.tpo.ecommerce.grupo6.service;

import com.tpo.ecommerce.grupo6.dto.*;
import com.tpo.ecommerce.grupo6.exception.ProductoNoEncontradoException;
import com.tpo.ecommerce.grupo6.exception.StockInsuficienteException;
import com.tpo.ecommerce.grupo6.exception.UsuarioNoEncontradoException;
import com.tpo.ecommerce.grupo6.mapper.PedidoMapper;
import com.tpo.ecommerce.grupo6.model.*;
import com.tpo.ecommerce.grupo6.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PedidoProductoRepository pedidoProductoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private HistorialPedidoRepository historialPedidoRepository;

    @Autowired
    private UsuarioService usuarioService;

    public List<PedidoDTO> findAll() {
        return pedidoMapper.toDTOList(pedidoRepository.findAll());
    }

    public PedidoDTO findById(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));
        return pedidoMapper.toDTO(pedido);
    }

    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void deleteById(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new RuntimeException("Pedido no encontrado con id: " + id);
        }
        pedidoRepository.deleteById(id);
    }

    public PedidoDTO create(CreatePedidoDTO dto) {
        Pedido pedido = pedidoMapper.toEntity(dto);
        Pedido savedPedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDTO(savedPedido);
    }

    @Transactional
    public PedidoDTO checkout(CheckoutDTO checkoutDTO) {
        // Validar que los datos del pago vienen en el request
        if (checkoutDTO.getPago() == null) {
            throw new IllegalArgumentException("Información de pago requerida");
        }

        Usuario usuario = usuarioService.findEntityById(checkoutDTO.getUsuarioId());

        if (checkoutDTO.getItems() == null || checkoutDTO.getItems().isEmpty()) {
            throw new IllegalArgumentException("El carrito está vacío");
        }

        // Validar stock y obtener productos
        List<Producto> productosCompra = checkoutDTO.getItems().stream()
                .map(this::validarStock)
                .collect(Collectors.toList());

        // Calcular total y crear detalles del pedido
        BigDecimal total = BigDecimal.ZERO;
        List<PedidoProducto> detallesPedido = new ArrayList<>();

        for (int i = 0; i < checkoutDTO.getItems().size(); i++) {
            ItemCarritoDTO item = checkoutDTO.getItems().get(i);
            Producto producto = productosCompra.get(i);

            BigDecimal subtotal = producto.getPrecio().multiply(new BigDecimal(item.getCantidad()));
            total = total.add(subtotal);

            // Crear detalle del pedido con cantidad e importe
            PedidoProducto detalle = new PedidoProducto();
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(subtotal);
            detallesPedido.add(detalle);

            // Descontar stock
            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);
        }

        // Crear pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setTotal(total);
        pedido.setFechaPedido(LocalDateTime.now());

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // Vincular detalles al pedido
        for (PedidoProducto detalle : detallesPedido) {
            detalle.setPedido(pedidoGuardado);
            pedidoProductoRepository.save(detalle);
        }
        pedidoGuardado.setDetalles(detallesPedido);

        // Crear historial inicial
        crearHistorialPedido(pedidoGuardado, "CREADO", "Pedido creado, pago pendiente.");

        // Procesar pago
        procesarPago(pedidoGuardado, total, checkoutDTO.getPago());

        // Crear historial de pago procesado
        crearHistorialPedido(pedidoGuardado, "PAGO_PROCESADO", "Pago procesado exitosamente");

        return pedidoMapper.toDTO(pedidoGuardado);
    }

    private void procesarPago(Pedido pedido, BigDecimal monto, PagoRequestDTO pagoRequest) {
        // Validar datos del pago
        if (pagoRequest.getMetodo() == null || pagoRequest.getMetodo().isEmpty()) {
            throw new IllegalArgumentException("Método de pago es requerido");
        }

        validarDatosPago(pagoRequest);

        Pago pago = new Pago();
        pago.setMetodo(pagoRequest.getMetodo());
        pago.setMonto(monto);
        pago.setFechaPago(LocalDateTime.now());
        pago.setPedido(pedido);

        Pago pagoGuardado = pagoRepository.save(pago);
        pedido.setPago(pagoGuardado);
        pedidoRepository.save(pedido);
    }

    private void validarDatosPago(PagoRequestDTO pagoRequest) {
        if (pagoRequest.getMetodo().equals("TARJETA_CREDITO")) {
            if (pagoRequest.getNumeroTarjeta() == null || pagoRequest.getNumeroTarjeta().isEmpty()) {
                throw new IllegalArgumentException("Número de tarjeta requerido");
            }
            if (pagoRequest.getCvv() == null || pagoRequest.getCvv().isEmpty()) {
                throw new IllegalArgumentException("CVV requerido");
            }
        }
    }

    private void crearHistorialPedido(Pedido pedido, String estado, String descripcion) {
        HistorialPedido historial = new HistorialPedido();
        historial.setEstado(estado);
        historial.setDescripcion(descripcion);
        historial.setFechaCambio(LocalDateTime.now());
        historial.setPedido(pedido);

        historialPedidoRepository.save(historial);
    }

    private Producto validarStock(ItemCarritoDTO item) {
        Producto producto = productoRepository.findById(item.getProductoId())
                .orElseThrow(() -> new ProductoNoEncontradoException(
                        "Producto no encontrado con id: " + item.getProductoId()));

        if (producto.getStock() < item.getCantidad()) {
            throw new StockInsuficienteException(producto.getNombre(), String.valueOf(producto.getStock()),
                    String.valueOf(item.getCantidad()));
        }

        return producto;
    }

    public PedidoDTO updatePedido(Long id, UpdatePedidoDTO updateDTO) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));
        pedidoMapper.updateEntity(updateDTO, pedido);
        Pedido updatedPedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDTO(updatedPedido);
    }
}