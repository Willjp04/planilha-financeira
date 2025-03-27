package br.com.sistema.financeiro.api.spring.boot.services;

import br.com.sistema.financeiro.api.spring.boot.entities.Categoria;
import br.com.sistema.financeiro.api.spring.boot.entities.Transacao;
import br.com.sistema.financeiro.api.spring.boot.entities.Usuario;
import br.com.sistema.financeiro.api.spring.boot.enums.TipoTransacao;
import br.com.sistema.financeiro.api.spring.boot.repositories.TransacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {




    @InjectMocks
    private TransacaoService transacaoService;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Test
    void testCriarTransacao() {

        Categoria categoria = new Categoria();
        UUID uuid = UUID.randomUUID();
        categoria.setId(uuid);
        categoria.setNome("Cartão");

        Usuario usuario = new Usuario();
        usuario.setId(uuid);
        usuario.setNome("Willian");



        Transacao transacao = new Transacao();
        transacao.setDescricao("Fatura do Cartão de Crédito XP");
        transacao.setValor(new BigDecimal("780.00"));
        transacao.setData(LocalDate.now());
        transacao.setTipo(TipoTransacao.DESPESA);
        transacao.setCategoria(categoria);
        transacao.setUsuario(usuario);


        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacao);

        Transacao resultado = transacaoService.salvarTransacao(transacao);

        assertNotNull(resultado);
        assertEquals("Fatura do Cartão de Crédito XP", resultado.getDescricao());
        assertEquals("780.00", resultado.getValor().toString());
        assertEquals(LocalDate.now(), resultado.getData());
        assertEquals(TipoTransacao.DESPESA, resultado.getTipo());
        assertEquals(categoria, resultado.getCategoria());
        assertEquals(usuario, resultado.getUsuario());
        verify(transacaoRepository,times(1)).save(any(Transacao.class));





    }

    @Test
    void testAtualizarTransacao(){

        Categoria categoria = new Categoria();
        UUID uuid = UUID.randomUUID();
        categoria.setId(uuid);
        categoria.setNome("Cartão");


        Usuario usuario = new Usuario();
        usuario.setId(uuid);
        usuario.setNome("Willian");




        UUID id = UUID.randomUUID();
        Transacao transacao = new Transacao();
        transacao.setId(id);
        transacao.setDescricao("Parcela da Entrada do Apartamento");
        transacao.setValor(new BigDecimal("980.00"));
        transacao.setData(LocalDate.now());
        transacao.setTipo(TipoTransacao.DESPESA);
        transacao.setCategoria(categoria);
        transacao.setUsuario(usuario);


Transacao transacaoAtualizada = new Transacao();
        transacaoAtualizada.setId(id);
        transacao.setDescricao("Parcela da Entrada do Apartamento ATUALIZADO");
        transacao.setValor(new BigDecimal("990.00"));
        transacao.setData(LocalDate.now());
        transacao.setTipo(TipoTransacao.DESPESA);
        transacao.setCategoria(categoria);
        transacao.setUsuario(usuario);

        when(transacaoRepository.findById(id)).thenReturn(Optional.of(transacaoAtualizada));
        when(transacaoRepository.save(any(Transacao.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transacao resultado = transacaoService.atualizarTransacao(id, transacao);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Parcela da Entrada do Apartamento ATUALIZADO", resultado.getDescricao());
        assertEquals("990.00", resultado.getValor().toString());
        verify(transacaoRepository,times(1)).save(any(Transacao.class));
        verify(transacaoRepository,times(1)).findById(id);

    }


    @Test
    void testDeletarTransacao(){
        UUID uuid = UUID.randomUUID();

        when(transacaoRepository.existsById(uuid)).thenReturn(true);
        transacaoService.deletarTransacao(uuid);
        verify(transacaoRepository,times(1)).deleteById(uuid);

    }


}
