package br.com.thiagobianeck.codeflowreferences;

import br.com.thiagobianeck.codeflowreferences.references.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE DEMONSTRAÇÃO DE REFERÊNCIAS ===");
        System.out.println("CodeFlow - Dominando os Tipos de Referências em Java");
        System.out.println();

        // Configurar JVM para melhor visualização
        System.out.println("Configurações da JVM:");
        System.out.println("Max Memory: " + (Runtime.getRuntime().maxMemory() / 1024 / 1024) + " MB");
        System.out.println("Total Memory: " + (Runtime.getRuntime().totalMemory() / 1024 / 1024) + " MB");
        System.out.println("Free Memory: " + (Runtime.getRuntime().freeMemory() / 1024 / 1024) + " MB");
        System.out.println();

        try {
            // Demonstrar cada tipo de referência
            StrongReferenceDemo.demonstrateStrongReference();
            StrongReferenceDemo.demonstrateMemoryLeak();

            SoftReferenceDemo.demonstrateSoftReference();
            SoftReferenceDemo.demonstrateCache();

            WeakReferenceDemo.demonstrateWeakReference();
            WeakReferenceDemo.demonstrateWeakHashMap();
            WeakReferenceDemo.demonstrateObserverPattern();

            PhantomReferenceDemo.demonstratePhantomReference();
            PhantomReferenceDemo.demonstrateResourceManagement();

            // Comparação final
            ReferenceComparison.compareAllReferenceTypes();

        } catch (Exception e) {
            System.err.println("Erro durante execução: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("=== DEMONSTRAÇÃO CONCLUÍDA ===");
        System.out.println("✅ Todos os tipos de referências foram demonstrados!");
        System.out.println("🎯 Agora você domina o controle avançado de memória em Java!");
    }
}