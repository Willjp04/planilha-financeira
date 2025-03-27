package br.com.sistema.financeiro.api.spring.boot.entities;


import br.com.sistema.financeiro.api.spring.boot.enums.TipoTransacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "TRANSACAO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transacao {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String descricao;

    @Column(nullable = false)
    private BigDecimal valor;

    private LocalDate data;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;

    @ManyToOne
    @JoinColumn(name = "CATEGORIA_ID")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;


}
