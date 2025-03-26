package br.com.sistema.financeiro.api.spring.boot.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "CATEGORIA")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Categoria {

    @Id
    private UUID id;
    @Column(nullable = false)
    private String nome;
}
