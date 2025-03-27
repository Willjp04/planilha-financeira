package br.com.sistema.financeiro.api.spring.boot.controllers;


import br.com.sistema.financeiro.api.spring.boot.entities.Categoria;
import br.com.sistema.financeiro.api.spring.boot.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;




@RestController
@RequestMapping("/v1/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;


    @PostMapping("/criarCategoria")
    public ResponseEntity<Categoria> criarCategoria(@RequestBody Categoria categoria) {
        Categoria categoriaCriada = categoriaService.criarCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCriada);
    }


    @GetMapping("/listarTodos")
    public List<Categoria> listarTodos() {
        return categoriaService.listarCategorias();

    }

    @GetMapping("/listarPorNome")
    public List<Categoria> buscarCategoriasPorNome(@RequestParam String nome) {
        return categoriaService.buscarCategoriaPorNome(nome);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Categoria> atualizarCategoria(@PathVariable UUID id, @RequestBody Categoria categoriaAtualizada) {
    Categoria categoria = categoriaService.atualizarCategoria(id, categoriaAtualizada);
    return ResponseEntity.ok(categoria);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable UUID id) {
        categoriaService.deletarCategoriaPorId(id);
        return ResponseEntity.noContent().build();


    }







}
