package br.com.sistema.financeiro.api.spring.boot.controllers;


import br.com.sistema.financeiro.api.spring.boot.dtos.request.TransacaoRequestDto;
import br.com.sistema.financeiro.api.spring.boot.dtos.response.TransacaoResponseDto;
import br.com.sistema.financeiro.api.spring.boot.entities.Categoria;
import br.com.sistema.financeiro.api.spring.boot.entities.Transacao;
import br.com.sistema.financeiro.api.spring.boot.entities.Usuario;
import br.com.sistema.financeiro.api.spring.boot.enums.TipoTransacao;
import br.com.sistema.financeiro.api.spring.boot.repositories.CategoriaRepository;
import br.com.sistema.financeiro.api.spring.boot.repositories.UsuarioRepository;
import br.com.sistema.financeiro.api.spring.boot.services.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/transacoes")
public class TransacaoController {


    @Autowired
    private TransacaoService transacaoService;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/criarTransacao")
    public ResponseEntity<TransacaoResponseDto> criarTransacao(@RequestBody @Valid TransacaoRequestDto dto) {
        try {
            // Converte o tipo de String para o enum TipoTransacao
            TipoTransacao tipoTransacao = TipoTransacao.valueOf(dto.tipo().toUpperCase());  // "DESPESA" ou "RECEITA"

            // Cria a transação com o tipo já convertido
            Transacao transacao = new Transacao(dto.descricao(), dto.valor(), dto.data(), tipoTransacao);

            // Pega o nome da categoria e e-mail do usuário a partir do DTO
            String nomeCategoria = dto.categoria();  // O nome da categoria vem do DTO
            String emailUsuario = dto.usuario();  // O e-mail do usuário vem do DTO

            // Chama o serviço para salvar a transação, passando todos os parâmetros necessários
            Transacao transacaoSalva = transacaoService.salvarTransacao(
                    transacao,
                    nomeCategoria,
                    emailUsuario
            );

            // Cria um DTO de resposta com os dados da transação salva
            TransacaoResponseDto responseDto = new TransacaoResponseDto(
                    transacaoSalva.getId(),
                    transacaoSalva.getDescricao(),
                    transacaoSalva.getValor(),
                    transacaoSalva.getData(),
                    transacaoSalva.getTipo().name(),  // Converte o enum para String
                    transacaoSalva.getCategoria().getNome(),
                    transacaoSalva.getUsuario().getNome()
            );

            // Retorna a transação salva com o DTO de resposta
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tipo de transação inválido: " + dto.tipo());
        }
    }



}