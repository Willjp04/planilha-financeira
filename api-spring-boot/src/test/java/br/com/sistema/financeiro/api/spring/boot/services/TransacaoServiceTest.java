package br.com.sistema.financeiro.api.spring.boot.services;

import br.com.sistema.financeiro.api.spring.boot.entities.Categoria;
import br.com.sistema.financeiro.api.spring.boot.entities.Transacao;
import br.com.sistema.financeiro.api.spring.boot.entities.Usuario;
import br.com.sistema.financeiro.api.spring.boot.enums.TipoTransacao;
import br.com.sistema.financeiro.api.spring.boot.repositories.CategoriaRepository;
import br.com.sistema.financeiro.api.spring.boot.repositories.TransacaoRepository;
import br.com.sistema.financeiro.api.spring.boot.repositories.UsuarioRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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

    @Mock
    private CategoriaRepository categoriaRepository;


    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    void testCriarTransacao() {
        // Criando Categoria Mockada
        Categoria categoria = new Categoria();
        categoria.setId(UUID.randomUUID());
        categoria.setNome("Cartão");

        // Criando Usuário Mockado
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setNome("Willian");
        usuario.setEmail("willian@email.com");

        // Criando a Transação (sem categoria e usuário, pois serão buscados no método)
        Transacao transacao = new Transacao();
        transacao.setDescricao("Fatura do Cartão de Crédito XP");
        transacao.setValor(new BigDecimal("780.00"));
        transacao.setData(LocalDate.of(2024, 3, 28)); // Definir data fixa evita falhas na comparação
        transacao.setTipo(TipoTransacao.DESPESA);

        // Mockando comportamento dos Repositórios
        when(categoriaRepository.findByNomeIgnoreCase("Cartão")).thenReturn(Optional.of(categoria));
        when(usuarioRepository.findByEmail("willian@email.com")).thenReturn(Optional.of(usuario));
        when(transacaoRepository.save(any(Transacao.class))).thenAnswer(invocation -> {
            Transacao t = invocation.getArgument(0);
            t.setId(UUID.randomUUID()); // Simula a geração do ID
            return t;
        });

        // Chamando o método corrigido com os 3 argumentos
        Transacao resultado = transacaoService.salvarTransacao(transacao, "Cartão", "willian@email.com");

        // Verificações
        assertNotNull(resultado);
        assertNotNull(resultado.getId()); // Garante que o ID foi gerado
        assertEquals("Fatura do Cartão de Crédito XP", resultado.getDescricao());
        assertEquals(0, new BigDecimal("780.00").compareTo(resultado.getValor())); // Comparação correta de BigDecimal
        assertEquals(LocalDate.of(2024, 3, 28), resultado.getData());
        assertEquals(TipoTransacao.DESPESA, resultado.getTipo());
        assertEquals(categoria, resultado.getCategoria());
        assertEquals(usuario, resultado.getUsuario());

        // Verifica se os métodos foram chamados corretamente
        verify(categoriaRepository, times(1)).findByNomeIgnoreCase("Cartão");
        verify(usuarioRepository, times(1)).findByEmail("willian@email.com");
        verify(transacaoRepository, times(1)).save(any(Transacao.class));
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

    @Test
    void listarTransacoes(){


        Categoria categoria = new Categoria();
        UUID uuid = UUID.randomUUID();
        categoria.setId(uuid);
        categoria.setNome("Cartão");


        Usuario usuario = new Usuario();
        usuario.setId(uuid);
        usuario.setNome("Willian");




        Transacao transacao = new Transacao();
        transacao.setId(UUID.randomUUID());
        transacao.setDescricao("Fatura do Cartão de Crédito XP");
        transacao.setValor(new BigDecimal("780.00"));
        transacao.setData(LocalDate.now());
        transacao.setTipo(TipoTransacao.DESPESA);
        transacao.setCategoria(categoria);
        transacao.setUsuario(usuario);


        Transacao transacao2 = new Transacao();
        transacao2.setId(UUID.randomUUID());
        transacao2.setDescricao("Salário Fujitsu");
        transacao2.setValor(new BigDecimal("3850.00"));
        transacao2.setData(LocalDate.now());
        transacao2.setTipo(TipoTransacao.RECEITA);
        transacao2.setCategoria(categoria);
        transacao2.setUsuario(usuario);

        List<Transacao>  listarTransacoes = Arrays.asList(transacao, transacao2);
        when(transacaoRepository.findAll()).thenReturn(listarTransacoes);

        List<Transacao> resultado = transacaoService.listarTransacoes();

        assertNotNull(resultado);
        assertEquals(listarTransacoes.size(), resultado.size());
        verify(transacaoRepository,times(1)).findAll();




    }


}
