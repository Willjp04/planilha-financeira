package br.com.sistema.financeiro.api.spring.boot.services;

import br.com.sistema.financeiro.api.spring.boot.entities.Categoria;
import br.com.sistema.financeiro.api.spring.boot.repositories.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    @InjectMocks
    private CategoriaService categoriaService;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Test
    void testCriarCategoria() {
        Categoria categoria = new Categoria();
        categoria.setNome("cartão de credito");

        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        Categoria resultado = categoriaService.criarCategoria(categoria);

        assertNotNull(resultado);
        assertEquals("cartão de credito", categoria.getNome());
    }


    @Test
    void deletarCategoria() {
        UUID id = UUID.randomUUID();

        when(categoriaRepository.existsById(id)).thenReturn(true);
        categoriaService.deletarCategoriaPorId(id);

        verify(categoriaRepository, times(1)).deleteById(id);

    }

    @Test
    void buscarCategoriaPorId() {

        Categoria categoria = new Categoria();
        UUID id = UUID.randomUUID();
        categoria.setId(id);
        categoria.setNome("cartao de credito");


        // Simulando o comportamento do findById no repositório
        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));

        Optional<Categoria> resultado = categoriaService.buscarCategoriaPorId(id);

        assertNotNull(resultado);
        assertEquals("cartao de credito", categoria.getNome());
        verify(categoriaRepository, times(1)).findById(id);


    }

    @Test
    void testAtualizarCategoria() {
        UUID id = UUID.randomUUID();
        Categoria categoriaExistente = new Categoria();
        categoriaExistente.setId(id);
        categoriaExistente.setNome("cartao de credito");

        Categoria categoriaAtualizada = new Categoria();
        categoriaAtualizada.setNome("cartao de debito");


        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoriaExistente));

        when(categoriaRepository.save(any(Categoria.class))).thenAnswer(invocation -> invocation.getArgument(0));


        // Act
        Categoria resultado = categoriaService.atualizarCategoria(id, categoriaAtualizada);

        assertNotNull(resultado);
        assertEquals(id,resultado.getId());
        assertEquals("cartao de debito",resultado.getNome());
        verify(categoriaRepository, times(1)).findById(id);
        verify(categoriaRepository, times(1)).save(any(Categoria.class));

    }

    @Test
    void testListarCategorias(){

        Categoria categoria = new Categoria();
        categoria.setId(UUID.randomUUID());
        categoria.setNome("cartao de credito");

        Categoria categoria2 = new Categoria();
        categoria2.setId(UUID.randomUUID());
        categoria2.setNome("cartao de debito");


        List<Categoria> categoriaLista = Arrays.asList(categoria,categoria2);

        when(categoriaRepository.findAll()).thenReturn(categoriaLista);

        List<Categoria> resultado = categoriaService.listarCategorias();

        assertNotNull(resultado);
        assertEquals(2,resultado.size());
        assertEquals("cartao de credito",resultado.get(0).getNome());
        assertEquals("cartao de debito",resultado.get(1).getNome());
        verify(categoriaRepository, times(1)).findAll();

    }

}
