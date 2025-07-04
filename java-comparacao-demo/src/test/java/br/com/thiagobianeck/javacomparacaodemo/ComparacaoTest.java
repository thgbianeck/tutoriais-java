package br.com.thiagobianeck.javacomparacaodemo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Comparação: == vs equals()")
class ComparacaoTest {

    private SistemaAutenticacao sistema;

    @BeforeEach
    void setUp() {
        sistema = new SistemaAutenticacao();
    }

    @Test
    @DisplayName("Deve comparar strings literais corretamente")
    void testComparacaoStringsLiterais() {
        String str1 = "Java";
        String str2 = "Java";
        String str3 = new String("Java");

        // Literais têm mesma referência
        assertTrue(str1 == str2, "Strings literais devem ter mesma referência");
        assertTrue(str1.equals(str2), "Strings literais devem ser iguais por conteúdo");

        // new String cria objeto diferente
        assertFalse(str1 == str3, "String literal vs new String devem ter referências diferentes");
        assertTrue(str1.equals(str3), "String literal vs new String devem ser iguais por conteúdo");
    }

    @Test
    @DisplayName("Deve comparar objetos Usuario corretamente")
    void testComparacaoUsuarios() {
        Usuario user1 = new Usuario("test", "Test User", 25);
        Usuario user2 = new Usuario("test", "Test User", 25);
        Usuario user3 = user1;
        Usuario user4 = new Usuario("other", "Other User", 30);

        // Objetos diferentes, mesmo conteúdo
        assertFalse(user1 == user2, "Objetos diferentes devem ter referências diferentes");
        assertTrue(user1.equals(user2), "Objetos com mesmo conteúdo devem ser iguais");

        // Mesma referência
        assertTrue(user1 == user3, "Mesma referência deve ser igual com ==");
        assertTrue(user1.equals(user3), "Mesma referência deve ser igual com equals()");

        // Conteúdo diferente
        assertFalse(user1.equals(user4), "Objetos com conteúdo diferente devem ser diferentes");
    }

    @Test
    @DisplayName("Deve validar sistema de autenticação")
    void testSistemaAutenticacao() {
        String senhaArmazenada = "123456";
        String senhaDigitada = new String("123456");
        String senhaInvalida = "654321";

        // Método incorreto pode falhar
        assertFalse(sistema.validarSenhaIncorreto(senhaDigitada, senhaArmazenada),
                "Método incorreto deve falhar com strings de fontes diferentes");

        // Método correto sempre funciona
        assertTrue(sistema.validarSenhaCorreto(senhaDigitada, senhaArmazenada),
                "Método correto deve funcionar independente da fonte das strings");

        // Senha inválida
        assertFalse(sistema.validarSenhaCorreto(senhaInvalida, senhaArmazenada),
                "Senha inválida deve retornar false");
    }

    @Test
    @DisplayName("Deve tratar valores null corretamente")
    void testValoresNull() {
        // Teste com valores null
        assertFalse(sistema.validarSenhaCorreto(null, "123456"),
                "Senha null deve retornar false");
        assertFalse(sistema.validarSenhaCorreto("123456", null),
                "Senha armazenada null deve retornar false");
        assertFalse(sistema.validarSenhaCorreto(null, null),
                "Ambas senhas null devem retornar false");
    }

    @Test
    @DisplayName("Deve validar contrato equals() e hashCode()")
    void testContratoEqualsHashCode() {
        Usuario user1 = new Usuario("test", "Test User", 25);
        Usuario user2 = new Usuario("test", "Test User", 25);
        Usuario user3 = new Usuario("other", "Other User", 30);

        // Reflexividade
        assertTrue(user1.equals(user1), "equals() deve ser reflexivo");

        // Simetria
        assertTrue(user1.equals(user2), "equals() deve ser simétrico");
        assertTrue(user2.equals(user1), "equals() deve ser simétrico");

        // Consistência do hashCode
        assertEquals(user1.hashCode(), user2.hashCode(),
                "Objetos iguais devem ter mesmo hashCode");

        // Objetos diferentes podem ter hashCodes diferentes
        assertNotEquals(user1.hashCode(), user3.hashCode(),
                "Objetos diferentes devem ter hashCodes diferentes");

        // Null safety
        assertFalse(user1.equals(null), "equals(null) deve retornar false");
    }
}