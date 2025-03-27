package br.com.sistema.financeiro.api.spring.boot.services;

import br.com.sistema.financeiro.api.spring.boot.entities.Transacao;
import br.com.sistema.financeiro.api.spring.boot.repositories.TransacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransacaoService {


    @Autowired
    private TransacaoRepository transacaoRepository;


    @Transactional
    public Transacao salvarTransacao(Transacao transacao) {
        return transacaoRepository.save(transacao);
    }

    public Transacao atualizarTransacao(UUID id, Transacao transacaoAtualizada) {
        return transacaoRepository.findById(id).map(transacao ->
        {
            transacao.setDescricao(transacaoAtualizada.getDescricao());
            transacao.setValor(transacaoAtualizada.getValor());
            transacao.setData(transacaoAtualizada.getData());
            transacao.setTipo(transacaoAtualizada.getTipo());
            transacao.setCategoria(transacaoAtualizada.getCategoria());
            transacao.setUsuario(transacaoAtualizada.getUsuario());
            return transacaoRepository.save(transacao);


        }).orElseThrow(() -> new RuntimeException("Transação não encontrada para o id " + id));


    }

    public void deletarTransacao(UUID id) {
        if(!transacaoRepository.existsById(id)) {
            throw new RuntimeException("Transação não encontrada para o id " + id);
        }
        transacaoRepository.deleteById(id);

    }

    public List<Transacao> listarTransacoes() {
        return transacaoRepository.findAll();
    }
}
