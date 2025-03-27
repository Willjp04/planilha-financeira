package br.com.sistema.financeiro.api.spring.boot.services;

import br.com.sistema.financeiro.api.spring.boot.entities.Transacao;
import br.com.sistema.financeiro.api.spring.boot.repositories.TransacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacaoService {


    @Autowired
    private TransacaoRepository transacaoRepository;


    @Transactional
    public Transacao salvarTransacao(Transacao transacao) {
        return transacaoRepository.save(transacao);
    }


}
