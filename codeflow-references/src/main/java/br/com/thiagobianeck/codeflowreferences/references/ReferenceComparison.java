package br.com.thiagobianeck.codeflowreferences.references;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;

public class ReferenceComparison {

    public static void compareAllReferenceTypes() {
        System.out.println("=== COMPARAÇÃO COMPLETA DOS TIPOS DE REFERÊNCIAS ===");

        // Criar objetos de teste
        List<String> strongRefs = new ArrayList<>();
        List<SoftReference<String>> softRefs = new ArrayList<>();
        List<WeakReference<String>> weakRefs = new ArrayList<>();
        List<PhantomReference<String>> phantomRefs = new ArrayList<>();
        ReferenceQueue<String> queue = new ReferenceQueue<>();

        // Criar referências de todos os tipos
        for (int i = 0; i < 100; i++) {
            String data = "TestData-" + i;

            // Strong Reference
            strongRefs.add(data);

            // Soft Reference
            softRefs.add(new SoftReference<>(new String(data)));

            // Weak Reference
            weakRefs.add(new WeakReference<>(new String(data)));

            // Phantom Reference
            phantomRefs.add(new PhantomReference<>(new String(data), queue));
        }

        System.out.println("Referências criadas de cada tipo: 100");
        System.out.println();

        // Verificar estado inicial
        printReferenceStats("INICIAL", strongRefs, softRefs, weakRefs, phantomRefs, queue);

        // Primeira coleta de lixo
        System.out.println("Executando primeira coleta de lixo...");
        System.gc();
        waitAndProcess(queue);

        printReferenceStats("APÓS 1ª GC", strongRefs, softRefs, weakRefs, phantomRefs, queue);

        // Segunda coleta de lixo
        System.out.println("Executando segunda coleta de lixo...");
        System.gc();
        waitAndProcess(queue);

        printReferenceStats("APÓS 2ª GC", strongRefs, softRefs, weakRefs, phantomRefs, queue);

        // Criar pressão de memória
        System.out.println("Criando pressão de memória...");
        createMemoryPressure();
        waitAndProcess(queue);

        printReferenceStats("APÓS PRESSÃO DE MEMÓRIA", strongRefs, softRefs, weakRefs, phantomRefs, queue);

        // Limpar strong references
        System.out.println("Limpando strong references...");
        strongRefs.clear();
        System.gc();
        waitAndProcess(queue);

        printReferenceStats("APÓS LIMPEZA MANUAL", strongRefs, softRefs, weakRefs, phantomRefs, queue);

        System.out.println("=== RESUMO DAS CARACTERÍSTICAS ===");
        printCharacteristicsSummary();
    }

    private static void printReferenceStats(String phase,
                                            List<String> strongRefs,
                                            List<SoftReference<String>> softRefs,
                                            List<WeakReference<String>> weakRefs,
                                            List<PhantomReference<String>> phantomRefs,
                                            ReferenceQueue<String> queue) {

        System.out.println("--- " + phase + " ---");
        System.out.println("Strong References: " + strongRefs.size());
        System.out.println("Soft References vivas: " + countAlive(softRefs));
        System.out.println("Weak References vivas: " + countAlive(weakRefs));
        System.out.println("Phantom References na fila: " + countInQueue(queue));
        System.out.println();
    }

    private static <T> int countAlive(List<? extends Reference<T>> references) {
        int count = 0;
        for (Reference<T> ref : references) {
            if (ref.get() != null) {
                count++;
            }
        }
        return count;
    }

    private static int countInQueue(ReferenceQueue<?> queue) {
        int count = 0;
        while (queue.poll() != null) {
            count++;
        }
        return count;
    }

    private static void waitAndProcess(ReferenceQueue<String> queue) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void createMemoryPressure() {
        try {
            List<byte[]> memoryHog = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                memoryHog.add(new byte[1024 * 1024]); // 1MB cada
            }
        } catch (OutOfMemoryError e) {
            System.out.println("Pressão de memória aplicada!");
        }
    }

    private static void printCharacteristicsSummary() {
        System.out.println("📊 STRONG REFERENCE:");
        System.out.println("  ✅ Nunca coletada automaticamente");
        System.out.println("  ❌ Pode causar vazamentos de memória");
        System.out.println("  🎯 Uso: Objetos essenciais");
        System.out.println();

        System.out.println("📊 SOFT REFERENCE:");
        System.out.println("  ✅ Coletada apenas com pouca memória");
        System.out.println("  ✅ Ideal para caches");
        System.out.println("  ⚠️ Comportamento dependente da JVM");
        System.out.println("  🎯 Uso: Caches que podem ser recriados");
        System.out.println();

        System.out.println("📊 WEAK REFERENCE:");
        System.out.println("  ✅ Coletada rapidamente");
        System.out.println("  ✅ Previne vazamentos");
        System.out.println("  ❌ Objetos podem desaparecer inesperadamente");
        System.out.println("  🎯 Uso: Observers, listeners, quebrar ciclos");
        System.out.println();

        System.out.println("📊 PHANTOM REFERENCE:");
        System.out.println("  ✅ Controle preciso de cleanup");
        System.out.println("  ✅ Notificação pós-coleta");
        System.out.println("  ❌ Complexidade de implementação");
        System.out.println("  🎯 Uso: Cleanup de recursos nativos");
        System.out.println();
    }
}