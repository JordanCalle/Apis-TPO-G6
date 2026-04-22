package com.tpo.ecommerce.grupo6.mapper;

import com.tpo.ecommerce.grupo6.dto.CreateProductoDTO;
import com.tpo.ecommerce.grupo6.dto.ProductoDTO;
import com.tpo.ecommerce.grupo6.dto.UpdateProductoDTO;
import com.tpo.ecommerce.grupo6.model.Categoria;
import com.tpo.ecommerce.grupo6.model.Producto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductoMapper {

    public ProductoDTO toDTO(Producto producto) {
        if (producto == null) {
            return null;
        }
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        if (producto.getCategoria() != null) {
            dto.setCategoriaId(producto.getCategoria().getId());
            dto.setCategoriaNombre(producto.getCategoria().getNombre());
        }
        return dto;
    }

    public List<ProductoDTO> toDTOList(List<Producto> productos) {
        return productos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Producto toEntity(CreateProductoDTO dto) {
        if (dto == null) {
            return null;
        }
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        if (dto.getCategoriaId() != null) {
            Categoria categoria = new Categoria();
            categoria.setId(dto.getCategoriaId());
            producto.setCategoria(categoria);
        }
        return producto;
    }

    public void updateEntity(UpdateProductoDTO dto, Producto producto) {
        if (dto == null) {
            return;
        }
        if (dto.getNombre() != null) {
            producto.setNombre(dto.getNombre());
        }
        if (dto.getDescripcion() != null) {
            producto.setDescripcion(dto.getDescripcion());
        }
        if (dto.getPrecio() != null) {
            producto.setPrecio(dto.getPrecio());
        }
        if (dto.getStock() != null) {
            producto.setStock(dto.getStock());
        }
        if (dto.getCategoriaId() != null) {
            Categoria categoria = new Categoria();
            categoria.setId(dto.getCategoriaId());
            producto.setCategoria(categoria);
        }
    }
}
