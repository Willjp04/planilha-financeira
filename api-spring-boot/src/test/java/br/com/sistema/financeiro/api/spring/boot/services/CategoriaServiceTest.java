package br.com.sistema.financeiro.api.spring.boot.services;

import br.com.sistema.financeiro.api.spring.boot.entities.Categoria;
import br.com.sistema.financeiro.api.spring.boot.repositories.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.UUID;

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

        Categoria resultado = categoriaService.salvarCategoria(categoria);

        assertNotNull(resultado);
        assertEquals("cartão de credito", categoria.getNome());
    }


    @Test
    void deletarCategoria() {
        UUID id = UUID.randomUUID();

        when(categoriaRepository.existsById(id)).thenReturn(true);
        categoriaService.excluirCategoriaPorId(id);

        verify(categoriaRepository, times(1)).deleteById(id);

    }

}
