package br.com.sistema.financeiro.api.spring.boot.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.UUID;

@Entity
@Table(name = "Usuario")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Usuario {

    @Id
    private UUID id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String senha;
}
