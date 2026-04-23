package com.tpo.ecommerce.grupo6.service;

import com.tpo.ecommerce.grupo6.dto.CategoriaDTO;
import com.tpo.ecommerce.grupo6.dto.CreateCategoriaDTO;
import com.tpo.ecommerce.grupo6.dto.UpdateCategoriaDTO;
import com.tpo.ecommerce.grupo6.exception.CategoriaDuplicadaException;
import com.tpo.ecommerce.grupo6.exception.CategoriaNoEncontradaException;
import com.tpo.ecommerce.grupo6.mapper.CategoriaMapper;
import com.tpo.ecommerce.grupo6.model.Categoria;
import com.tpo.ecommerce.grupo6.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    public List<CategoriaDTO> findAll() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categoriaMapper.toDTOList(categorias);
    }

    public CategoriaDTO findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNoEncontradaException("Categoría no encontrada con id: " + id));

        return categoriaMapper.toDTO(categoria);
    }

    public CategoriaDTO save(CreateCategoriaDTO createDTO) {
        Optional<Categoria> categoriaExistente = categoriaRepository.findByNombre(createDTO.getNombre());

        if (categoriaExistente.isPresent()) {
            throw new CategoriaDuplicadaException("Ya existe una categoría con nombre: " + createDTO.getNombre());
        }

        Categoria categoria = categoriaMapper.toEntity(createDTO);
        Categoria savedCategoria = categoriaRepository.save(categoria);

        return categoriaMapper.toDTO(savedCategoria);
    }

    public CategoriaDTO update(Long id, UpdateCategoriaDTO updateDTO) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNoEncontradaException("Categoría no encontrada con id: " + id));

        Optional<Categoria> categoriaConMismoNombre = categoriaRepository.findByNombre(updateDTO.getNombre());

        if (categoriaConMismoNombre.isPresent() &&
                !categoriaConMismoNombre.get().getId().equals(id)) {
            throw new CategoriaDuplicadaException("Ya existe una categoría con nombre: " + updateDTO.getNombre());
        }

        categoriaMapper.updateEntity(updateDTO, categoria);

        Categoria updatedCategoria = categoriaRepository.save(categoria);

        return categoriaMapper.toDTO(updatedCategoria);
    }

    public void deleteById(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new CategoriaNoEncontradaException("Categoría no encontrada con id: " + id);
        }

        categoriaRepository.deleteById(id);
    }
}