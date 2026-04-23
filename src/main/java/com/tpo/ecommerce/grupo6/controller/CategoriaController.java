package com.tpo.ecommerce.grupo6.controller;

import com.tpo.ecommerce.grupo6.dto.CategoriaDTO;
import com.tpo.ecommerce.grupo6.dto.CreateCategoriaDTO;
import com.tpo.ecommerce.grupo6.dto.UpdateCategoriaDTO;
import com.tpo.ecommerce.grupo6.mapper.CategoriaMapper;
import com.tpo.ecommerce.grupo6.model.Categoria;
import com.tpo.ecommerce.grupo6.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<CategoriaDTO> getAllCategorias() {
        return categoriaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> getCategoriaById(@PathVariable Long id) {
        CategoriaDTO categoria = categoriaService.findById(id);
        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public CategoriaDTO createCategoria(@RequestBody CreateCategoriaDTO createDTO) {
        return categoriaService.save(createDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> updateCategoria(@PathVariable Long id,
                                                        @RequestBody UpdateCategoriaDTO updateDTO) {
        CategoriaDTO updatedCategoria = categoriaService.update(id, updateDTO);
        return ResponseEntity.ok(updatedCategoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        categoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}