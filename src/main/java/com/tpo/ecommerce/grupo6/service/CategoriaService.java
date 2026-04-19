package com.tpo.ecommerce.grupo6.service;

import com.tpo.ecommerce.grupo6.exception.CategoriaDuplicadaException;
import com.tpo.ecommerce.grupo6.exception.CategoriaNoEncontradaException;
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

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Categoria findById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNoEncontradaException("Categoría no encontrada con id: " + id));
    }

    public Categoria save(Categoria categoria) {
        Optional<Categoria> categoriaExistente = categoriaRepository.findByNombre(categoria.getNombre());

        if (categoriaExistente.isPresent()) {
            throw new CategoriaDuplicadaException("Ya existe una categoría con nombre: " + categoria.getNombre());
        }

        return categoriaRepository.save(categoria);
    }

    public Categoria update(Long id, Categoria categoriaDetails) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNoEncontradaException("Categoría no encontrada con id: " + id));

        Optional<Categoria> categoriaConMismoNombre = categoriaRepository.findByNombre(categoriaDetails.getNombre());

        if (categoriaConMismoNombre.isPresent() &&
                !categoriaConMismoNombre.get().getId().equals(id)) {
            throw new CategoriaDuplicadaException("Ya existe una categoría con nombre: " + categoriaDetails.getNombre());
        }

        categoria.setNombre(categoriaDetails.getNombre());
        categoria.setDescripcion(categoriaDetails.getDescripcion());

        return categoriaRepository.save(categoria);
    }

    public void deleteById(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new CategoriaNoEncontradaException("Categoría no encontrada con id: " + id);
        }

        categoriaRepository.deleteById(id);
    }
}