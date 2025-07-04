package br.com.thiagobianeck.techcorpgenerics.model;

public class Produto {
    private Long id;
    private String nome;
    private Double preco;
    private String categoria;

    // Construtor padrão
    public Produto() {}

    // Construtor com parâmetros
    public Produto(Long id, String nome, Double preco, String categoria) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    // toString para facilitar debugging
    @Override
    public String toString() {
        return String.format("Produto{id=%d, nome='%s', preco=%.2f, categoria='%s'}",
                id, nome, preco, categoria);
    }

    // equals e hashCode para comparações
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Produto produto = (Produto) obj;
        return id != null && id.equals(produto.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}