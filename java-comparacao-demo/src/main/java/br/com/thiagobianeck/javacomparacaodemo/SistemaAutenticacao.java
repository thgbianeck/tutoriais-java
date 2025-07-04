package br.com.thiagobianeck.javacomparacaodemo;

/**
 * Sistema de autenticação para demonstrar o problema
 */
public class SistemaAutenticacao {

    /**
     * Método INCORRETO - usa == para comparar strings
     */
    public boolean validarSenhaIncorreto(String senhaDigitada, String senhaArmazenada) {
        return senhaDigitada == senhaArmazenada; // ❌ ERRO!
    }

    /**
     * Método CORRETO - usa equals() para comparar strings
     */
    public boolean validarSenhaCorreto(String senhaDigitada, String senhaArmazenada) {
        if (senhaDigitada == null || senhaArmazenada == null) {
            return false;
        }
        return senhaDigitada.equals(senhaArmazenada); // ✅ CORRETO!
    }
}