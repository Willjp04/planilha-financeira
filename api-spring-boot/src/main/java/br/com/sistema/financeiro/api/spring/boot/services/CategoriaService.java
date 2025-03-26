package br.com.sistema.financeiro.api.spring.boot.services;

import br.com.sistema.financeiro.api.spring.boot.entities.Categoria;
import br.com.sistema.financeiro.api.spring.boot.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;


    public List<Categoria>listarCategorias(){
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> buscarCategoriaPorId(UUID id){
        return categoriaRepository.findById(id);
    }

    public Categoria salvarCategoria(Categoria categoria){
        return categoriaRepository.save(categoria);
    }

    // Método para atualizar uma categoria existente
    public Categoria atualizar(UUID id, Categoria categoriaAtualizada) {
        return categoriaRepository.findById(id)
                .map(categoria -> {
                    categoria.setNome(categoriaAtualizada.getNome());
                    // Atualize outros campos conforme necessário
                    return categoriaRepository.save(categoria);
                })
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com o ID: " + id));
    }



    public void excluirCategoria(Categoria categoria){
        categoriaRepository.delete(categoria);
    }
}
