package br.com.sistema.financeiro.api.spring.boot.services;

import br.com.sistema.financeiro.api.spring.boot.entities.Usuario;
import br.com.sistema.financeiro.api.spring.boot.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void testCriarUsuario() {

        Usuario usuario = new Usuario();
        usuario.setNome("Willian");
        usuario.setEmail("willian@gmail.com");
        usuario.setSenha("123456");

        // Simulando o comportamento do save
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Criando o usuário
        Usuario resultado = usuarioService.criarUsuario(usuario);

        // Verificações do criar usuário
        assertNotNull(resultado);
        assertEquals("Willian", resultado.getNome());
        assertEquals("willian@gmail.com", resultado.getEmail());
        assertEquals("123456", resultado.getSenha());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }


    @Test
    void testBuscarUsuarioPorEmail() {
        // Criando o usuário de teste
        Usuario usuario = new Usuario();
        usuario.setNome("Willian");
        usuario.setEmail("willian@gmail.com");
        usuario.setSenha("123456");

        // Simulando o comportamento do findByEmail no repositório
        when(usuarioRepository.findByEmail("willian@gmail.com")).thenReturn(Optional.of(usuario));

        // Chamando o método buscarUsuarioPorEmail do serviço
        Optional<Usuario> resultadoOptional = usuarioService.buscarUsuarioPorEmail("willian@gmail.com");

        // Verificando se o resultado não é vazio
        assertTrue(resultadoOptional.isPresent());

        // Obtendo o usuário dentro do Optional e verificando os dados
        Usuario usuarioEncontrado = resultadoOptional.get();
        assertEquals("Willian", usuarioEncontrado.getNome());
        assertEquals("willian@gmail.com", usuarioEncontrado.getEmail());

        // Verificando se o repositório foi chamado uma vez
        verify(usuarioRepository, times(1)).findByEmail("willian@gmail.com");
    }

@Test
    void testBuscarUsuarioPorEmailNaoEncontrado() {
    // Simulando o comportamento de não encontrar o usuário no repositório
    when(usuarioRepository.findByEmail("naoencontrado@gmail.com")).thenReturn(Optional.empty());

    // Chamando o metodo
    Optional<Usuario> resultadoOptional = usuarioService.buscarUsuarioPorEmail("naoencontrado@gmail.com");

    // Verificando se o Optional está vazio
    assertFalse(resultadoOptional.isPresent());

    // Verifica se o repositório foi chamado uma vez
    verify(usuarioRepository, times(1)).findByEmail("naoencontrado@gmail.com");


}

}
