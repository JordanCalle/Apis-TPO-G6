package com.tpo.ecommerce.grupo6.service;

import com.tpo.ecommerce.grupo6.dto.ItemCarritoDTO;
import com.tpo.ecommerce.grupo6.dto.CheckoutDTO;
import com.tpo.ecommerce.grupo6.dto.PagoRequestDTO;
import com.tpo.ecommerce.grupo6.exception.ProductoNoEncontradoException;
import com.tpo.ecommerce.grupo6.exception.StockInsuficienteException;
import com.tpo.ecommerce.grupo6.exception.UsuarioNoEncontradoException;
import com.tpo.ecommerce.grupo6.model.*;
import com.tpo.ecommerce.grupo6.repository.HistorialPedidoRepository;
import com.tpo.ecommerce.grupo6.repository.PagoRepository;
import com.tpo.ecommerce.grupo6.repository.PedidoRepository;
import com.tpo.ecommerce.grupo6.repository.ProductoRepository;
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
    private HistorialPedidoRepository historialPedidoRepository;

    @Autowired
    private UsuarioService usuarioService;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void deleteById(Long id) {
        pedidoRepository.deleteById(id);
    }

    @Transactional
    public Pedido checkout(CheckoutDTO checkoutDTO) {

        // Validar que los datos del pago vienen en el request
        if (checkoutDTO.getPago() == null) {
            throw new IllegalArgumentException("Información de pago requerida");
        }

        Usuario usuario = usuarioService.findById(checkoutDTO.getUsuarioId())
                .orElseThrow(() -> new UsuarioNoEncontradoException(
                        "Usuario no encontrado con id: " + checkoutDTO.getUsuarioId()));

        if (checkoutDTO.getItems() == null || checkoutDTO.getItems().isEmpty()) {
            throw new IllegalArgumentException("El carrito está vacío");
        }

        BigDecimal total = BigDecimal.ZERO;
        List<Producto> productosCompra = checkoutDTO.getItems().stream()
                .map(item -> validarStock(item))
                .collect(Collectors.toList());

        for (int i = 0; i < checkoutDTO.getItems().size(); i++) {
            ItemCarritoDTO item = checkoutDTO.getItems().get(i);
            Producto producto = productosCompra.get(i);
            BigDecimal subtotal = producto.getPrecio()
                    .multiply(new BigDecimal(item.getCantidad()));
            total = total.add(subtotal);
        }

        for (int i = 0; i < checkoutDTO.getItems().size(); i++) {
            ItemCarritoDTO item = checkoutDTO.getItems().get(i);
            Producto producto = productosCompra.get(i);
            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setProductos(productosCompra);
        pedido.setTotal(total);
        pedido.setFechaPedido(LocalDateTime.now());

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // Primer estado del pedido, previo al pago
        crearHistorialPedido(pedidoGuardado, "CREADO", "Pedido creado, pago pendiente.");

        procesarPago(pedidoGuardado, total, checkoutDTO.getPago());

        // Mockeamos estado de pedido ya que estamos simulando un pago exitoso. En un
        // caso real debería utilizar la salida del procesarPago()
        crearHistorialPedido(pedidoGuardado, "PAGO_PROCESADO", "Pago procesado exitosamente");

        return pedidoGuardado;
    }

    private void procesarPago(Pedido pedido, BigDecimal monto, PagoRequestDTO pagoRequest) {
        // Validar datos del pago
        if (pagoRequest.getMetodo() == null || pagoRequest.getMetodo().isEmpty()) {
            throw new IllegalArgumentException("Método de pago es requerido");
        }

        // Este método hace una validación simple, acá llamaría a una api externa para
        // procesar el pago
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
}