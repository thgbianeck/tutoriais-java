package br.com.thiagobianeck.codeflowreferences.references;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class SoftReferenceDemo {
    private static final List<SoftReference<String>> softReferences = new ArrayList<>();

    public static void demonstrateSoftReference() {
        System.out.println("=== SOFT REFERENCE DEMO ===");

        // Criar objetos com referências suaves
        for (int i = 0; i < 1000; i++) {
            String data = "SoftData-" + i + "-" + generateLargeString();
            SoftReference<String> softRef = new SoftReference<>(data);
            softReferences.add(softRef);
        }

        System.out.println("Soft References criadas: " + softReferences.size());

        // Verificar quantos objetos ainda existem
        int aliveCount = countAliveReferences();
        System.out.println("Objetos vivos inicialmente: " + aliveCount);

        // Forçar coleta de lixo
        System.out.println("Forçando Garbage Collection...");
        System.gc();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Verificar novamente
        aliveCount = countAliveReferences();
        System.out.println("Objetos vivos após GC: " + aliveCount);

        // Criar pressão de memória
        createMemoryPressure();

        // Verificar após pressão de memória
        aliveCount = countAliveReferences();
        System.out.println("Objetos vivos após pressão de memória: " + aliveCount);
        System.out.println("✅ Soft References são coletadas quando memória está baixa!");
        System.out.println();
    }

    private static int countAliveReferences() {
        int count = 0;
        for (SoftReference<String> ref : softReferences) {
            if (ref.get() != null) {
                count++;
            }
        }
        return count;
    }

    private static void createMemoryPressure() {
        System.out.println("Criando pressão de memória...");
        List<byte[]> memoryPressure = new ArrayList<>();

        try {
            for (int i = 0; i < 100; i++) {
                memoryPressure.add(new byte[1024 * 1024]); // 1MB
                System.gc(); // Forçar GC durante alocação
            }
        } catch (OutOfMemoryError e) {
            System.out.println("Pressão de memória aplicada com sucesso!");
        }
    }

    private static String generateLargeString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 200; i++) {
            sb.append("SoftReferenceData");
        }
        return sb.toString();
    }

    public static void demonstrateCache() {
        System.out.println("=== SOFT REFERENCE CACHE DEMO ===");

        SoftReferenceCache cache = new SoftReferenceCache();

        // Adicionar dados ao cache
        for (int i = 0; i < 100; i++) {
            cache.put("key-" + i, "Cached data " + i);
        }

        System.out.println("Cache size: " + cache.size());

        // Acessar alguns dados
        for (int i = 0; i < 10; i++) {
            String value = cache.get("key-" + i);
            System.out.println("Retrieved: " + value);
        }

        System.out.println("✅ Cache com Soft Reference funciona perfeitamente!");
        System.out.println();
    }

    private static class SoftReferenceCache {
        private final List<SoftReference<String>> cache = new ArrayList<>();
        private final List<String> keys = new ArrayList<>();

        public void put(String key, String value) {
            keys.add(key);
            cache.add(new SoftReference<>(value));
        }

        public String get(String key) {
            int index = keys.indexOf(key);
            if (index >= 0 && index < cache.size()) {
                SoftReference<String> ref = cache.get(index);
                return ref.get();
            }
            return null;
        }

        public int size() {
            return cache.size();
        }
    }
}