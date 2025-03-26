package br.com.sistema.financeiro.api.spring.boot.repositories;

import br.com.sistema.financeiro.api.spring.boot.entities.Transacao;
import br.com.sistema.financeiro.api.spring.boot.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {
    Optional<Transacao> findByUsuario(Usuario usuario);
}
