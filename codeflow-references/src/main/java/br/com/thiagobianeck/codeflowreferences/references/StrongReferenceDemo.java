package br.com.thiagobianeck.codeflowreferences.references;

import java.util.ArrayList;
import java.util.List;

public class StrongReferenceDemo {
    private static final List<String> strongReferences = new ArrayList<>();

    public static void demonstrateStrongReference() {
        System.out.println("=== STRONG REFERENCE DEMO ===");

        // Criar objetos com referências fortes
        for (int i = 0; i < 1000; i++) {
            String data = "Data-" + i + "-" + generateLargeString();
            strongReferences.add(data);
        }

        System.out.println("Objetos criados: " + strongReferences.size());

        // Tentar forçar coleta de lixo
        System.out.println("Forçando Garbage Collection...");
        System.gc();

        // Aguardar um pouco
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Verificar se os objetos ainda existem
        System.out.println("Objetos após GC: " + strongReferences.size());
        System.out.println("✅ Strong References nunca são coletadas automaticamente!");

        // Limpeza manual
        strongReferences.clear();
        System.out.println("Objetos após clear(): " + strongReferences.size());
        System.out.println();
    }

    private static String generateLargeString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("LargeData");
        }
        return sb.toString();
    }

    public static void demonstrateMemoryLeak() {
        System.out.println("=== MEMORY LEAK SIMULATION ===");

        // Simular vazamento de memória
        List<byte[]> memoryHog = new ArrayList<>();

        try {
            for (int i = 0; i < 10000; i++) {
                // Alocar 1MB por iteração
                byte[] data = new byte[1024 * 1024];
                memoryHog.add(data);

                if (i % 1000 == 0) {
                    System.out.println("Alocados: " + (i + 1) + " MB");
                }
            }
        } catch (OutOfMemoryError e) {
            System.out.println("❌ OutOfMemoryError capturado!");
            System.out.println("Objetos na memória: " + memoryHog.size() + " MB");
        }

        System.out.println("🔧 Strong References podem causar OutOfMemoryError!");
        System.out.println();
    }
}