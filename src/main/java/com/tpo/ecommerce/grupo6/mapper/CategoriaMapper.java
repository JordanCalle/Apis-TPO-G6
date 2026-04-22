package com.tpo.ecommerce.grupo6.mapper;

import com.tpo.ecommerce.grupo6.dto.CategoriaDTO;
import com.tpo.ecommerce.grupo6.dto.CreateCategoriaDTO;
import com.tpo.ecommerce.grupo6.dto.UpdateCategoriaDTO;
import com.tpo.ecommerce.grupo6.model.Categoria;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoriaMapper {

    public CategoriaDTO toDTO(Categoria categoria) {
        if (categoria == null) {
            return null;
        }
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        return dto;
    }

    public List<CategoriaDTO> toDTOList(List<Categoria> categorias) {
        return categorias.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Categoria toEntity(CreateCategoriaDTO dto) {
        if (dto == null) {
            return null;
        }
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        return categoria;
    }

    public void updateEntity(UpdateCategoriaDTO dto, Categoria categoria) {
        if (dto == null) {
            return;
        }
        if (dto.getNombre() != null) {
            categoria.setNombre(dto.getNombre());
        }
        if (dto.getDescripcion() != null) {
            categoria.setDescripcion(dto.getDescripcion());
        }
    }
}
