package br.com.thiagobianeck.javacomparacaodemo;

import java.util.Objects;
import java.util.Scanner;

/**
 * Demonstração prática das diferenças entre == e equals() em Java
 *
 * @author Thiago Bianeck
 * @version 1.0
 */
public class ComparacaoDemo {

    public static void main(String[] args) {
        System.out.println("=== DEMONSTRAÇÃO: == vs equals() ===\n");

        // Demonstrações individuais
        demonstrarPrimitivos();
        demonstrarStrings();
        demonstrarObjetos();
        demonstrarCasoReal();

        System.out.println("\n=== FIM DA DEMONSTRAÇÃO ===");
    }

    /**
     * Demonstra comparação com tipos primitivos
     */
    private static void demonstrarPrimitivos() {
        System.out.println("🔢 TIPOS PRIMITIVOS:");
        System.out.println("-------------------");

        int numero1 = 42;
        int numero2 = 42;
        int numero3 = 50;

        System.out.printf("numero1 = %d, numero2 = %d, numero3 = %d%n", numero1, numero2, numero3);
        System.out.printf("numero1 == numero2: %b%n", numero1 == numero2);
        System.out.printf("numero1 == numero3: %b%n", numero1 == numero3);

        double preco1 = 19.99;
        double preco2 = 19.99;
        System.out.printf("preco1 == preco2: %b%n", preco1 == preco2);

        System.out.println();
    }

    /**
     * Demonstra comparação com Strings
     */
    private static void demonstrarStrings() {
        System.out.println("📝 STRINGS:");
        System.out.println("----------");

        // Strings literais (pool)
        String str1 = "Java";
        String str2 = "Java";
        System.out.println("String literais:");
        System.out.printf("str1 == str2: %b%n", str1 == str2);
        System.out.printf("str1.equals(str2): %b%n", str1.equals(str2));

        // Strings com new (objetos diferentes)
        String str3 = new String("Java");
        String str4 = new String("Java");
        System.out.println("\nString com new:");
        System.out.printf("str3 == str4: %b%n", str3 == str4);
        System.out.printf("str3.equals(str4): %b%n", str3.equals(str4));

        // Comparação entre literal e new
        System.out.println("\nLiteral vs New:");
        System.out.printf("str1 == str3: %b%n", str1 == str3);
        System.out.printf("str1.equals(str3): %b%n", str1.equals(str3));

        // Demonstração da identidade dos objetos
        System.out.println("\nIdentidade dos objetos:");
        System.out.printf("str1.hashCode(): %d%n", str1.hashCode());
        System.out.printf("str2.hashCode(): %d%n", str2.hashCode());
        System.out.printf("str3.hashCode(): %d%n", str3.hashCode());
        System.out.printf("System.identityHashCode(str1): %d%n", System.identityHashCode(str1));
        System.out.printf("System.identityHashCode(str3): %d%n", System.identityHashCode(str3));

        System.out.println();
    }

    /**
     * Demonstra comparação com objetos personalizados
     */
    private static void demonstrarObjetos() {
        System.out.println("👤 OBJETOS PERSONALIZADOS:");
        System.out.println("-------------------------");

        // Criando usuários
        Usuario usuario1 = new Usuario("carlos.silva", "Carlos Silva", 28);
        Usuario usuario2 = new Usuario("carlos.silva", "Carlos Silva", 28);
        Usuario usuario3 = usuario1; // mesma referência
        Usuario usuario4 = new Usuario("marina.santos", "Marina Santos", 32);

        System.out.println("Usuários criados:");
        System.out.println("usuario1: " + usuario1);
        System.out.println("usuario2: " + usuario2);
        System.out.println("usuario4: " + usuario4);

        System.out.println("\nComparações:");
        System.out.printf("usuario1 == usuario2: %b (objetos diferentes, mesmo conteúdo)%n", usuario1 == usuario2);
        System.out.printf("usuario1.equals(usuario2): %b%n", usuario1.equals(usuario2));
        System.out.printf("usuario1 == usuario3: %b (mesma referência)%n", usuario1 == usuario3);
        System.out.printf("usuario1.equals(usuario3): %b%n", usuario1.equals(usuario3));
        System.out.printf("usuario1.equals(usuario4): %b (conteúdo diferente)%n", usuario1.equals(usuario4));

        System.out.println();
    }

    /**
     * Demonstra caso real - sistema de autenticação
     */
    private static void demonstrarCasoReal() {
        System.out.println("🔐 CASO REAL - SISTEMA DE AUTENTICAÇÃO:");
        System.out.println("-------------------------------------");

        // Simula diferentes fontes de string
        String senhaArmazenada = "123456"; // Literal (pool)
        String senhaDigitada = new String("123456"); // Vem de input do usuário
        String senhaInvalida = "654321";

        SistemaAutenticacao sistema = new SistemaAutenticacao();

        System.out.println("Testando validação de senhas:");
        System.out.printf("Senha armazenada: '%s'%n", senhaArmazenada);
        System.out.printf("Senha digitada: '%s'%n", senhaDigitada);

        // Método incorreto (usando ==)
        boolean loginIncorreto = sistema.validarSenhaIncorreto(senhaDigitada, senhaArmazenada);
        System.out.printf("Login com == (INCORRETO): %b%n", loginIncorreto);

        // Método correto (usando equals)
        boolean loginCorreto = sistema.validarSenhaCorreto(senhaDigitada, senhaArmazenada);
        System.out.printf("Login com equals() (CORRETO): %b%n", loginCorreto);

        // Teste com senha inválida
        boolean loginInvalido = sistema.validarSenhaCorreto(senhaInvalida, senhaArmazenada);
        System.out.printf("Login com senha inválida: %b%n", loginInvalido);
    }
}

