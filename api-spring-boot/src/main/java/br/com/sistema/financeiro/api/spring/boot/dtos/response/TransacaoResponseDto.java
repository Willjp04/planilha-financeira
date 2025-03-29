package br.com.sistema.financeiro.api.spring.boot.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransacaoResponseDto(
        UUID id,
        String descricao,
        BigDecimal valor,
        LocalDate data,
        String tipo,
        String categoriaNome,  // Nome da categoria
        String usuarioNome
) {
}
