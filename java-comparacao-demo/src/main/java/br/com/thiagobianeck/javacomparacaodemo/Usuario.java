package br.com.thiagobianeck.javacomparacaodemo;

import java.util.Objects;

/**
 * Classe que representa um usuário do sistema
 */
public class Usuario {
    private String username;
    private String nomeCompleto;
    private int idade;

    public Usuario(String username, String nomeCompleto, int idade) {
        this.username = username;
        this.nomeCompleto = nomeCompleto;
        this.idade = idade;
    }

    /**
     * Implementação correta do equals() seguindo o contrato
     */
    @Override
    public boolean equals(Object obj) {
        // 1. Verificação de referência (otimização)
        if (this == obj) return true;

        // 2. Verificação de null
        if (obj == null) return false;

        // 3. Verificação de classe
        if (getClass() != obj.getClass()) return false;

        // 4. Cast e comparação de campos
        Usuario usuario = (Usuario) obj;
        return idade == usuario.idade &&
                Objects.equals(username, usuario.username) &&
                Objects.equals(nomeCompleto, usuario.nomeCompleto);
    }

    /**
     * Implementação do hashCode() - OBRIGATÓRIO quando equals() é sobrescrito
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, nomeCompleto, idade);
    }

    @Override
    public String toString() {
        return String.format("Usuario{username='%s', nome='%s', idade=%d}",
                username, nomeCompleto, idade);
    }

    // Getters
    public String getUsername() { return username; }
    public String getNomeCompleto() { return nomeCompleto; }
    public int getIdade() { return idade; }
}

