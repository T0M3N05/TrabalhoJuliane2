package com.gabriel.estoque.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity                 // indica que a classe é uma entidade JPA (vira uma tabela)
@Table(name = "livros")
public class Livro {

    @Id                                                   // chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // ID gerado pelo banco (auto incremento)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private String autor;

    private String categoria;

    private Double preco;

    public Livro() {
    }

    public Livro(String titulo, String autor, String categoria, Double preco) {
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.preco = preco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
