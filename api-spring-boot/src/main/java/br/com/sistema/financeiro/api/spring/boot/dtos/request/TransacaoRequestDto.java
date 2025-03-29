package br.com.sistema.financeiro.api.spring.boot.dtos.request;

import br.com.sistema.financeiro.api.spring.boot.enums.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransacaoRequestDto(
        String descricao,
        BigDecimal valor,
        LocalDate data,
        String tipo,
        String categoria,
        String usuario
) {
}
