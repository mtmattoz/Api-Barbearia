package org.aula.model;

import jakarta.persistence.*;

@Entity
@Table(name = "servico")
public class Servico {

    @Id
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "duracao")
    private Integer duracao;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Double getValor() {
        return valor;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }
}