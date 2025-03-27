package br.com.sistema.financeiro.api.spring.boot.repositories;

import br.com.sistema.financeiro.api.spring.boot.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
    List<Categoria> findByNomeIgnoreCase(String nome);

}
