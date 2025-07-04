package br.com.thiagobianeck.gcdemoproject;

import br.com.thiagobianeck.gcdemoproject.cache.ImprovedProductCache;
import br.com.thiagobianeck.gcdemoproject.cache.ProductCache;
import br.com.thiagobianeck.gcdemoproject.model.User;
import br.com.thiagobianeck.gcdemoproject.util.MemoryMonitor;

import java.lang.ref.WeakReference;
import java.lang.ref.SoftReference;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Demonstração completa do funcionamento do Garbage Collector
 *
 * @author Thiago Bianeck
 * @version 1.0
 */
public class GarbageCollectorDemo {

    private static final MemoryMonitor memoryMonitor = new MemoryMonitor();

    public static void main(String[] args) {
        System.out.println("🚀 === DEMO: GARBAGE COLLECTOR EM AÇÃO ===");

        // Demonstra o problema original
        demonstrarProblema();

        // Demonstra a solução
        demonstrarSolucao();

        // Demonstra tipos de referências
        demonstrarTiposReferencias();

        // Demonstra monitoramento avançado
        demonstrarMonitoramentoAvancado();

        // Demonstra otimizações
        demonstrarOtimizacoes();
    }

    /**
     * Demonstra o problema original encontrado por Marina
     */
    private static void demonstrarProblema() {
        System.out.println("\n🔴 === PROBLEMA: VAZAMENTO DE MEMÓRIA ===");

        memoryMonitor.printMemoryInfo("Antes do problema");

        ProductCache cacheProblematico = new ProductCache();

        // Simula o problema: acúmulo de objetos
        for (int i = 0; i < 5; i++) {
            cacheProblematico.loadProducts();
            memoryMonitor.printMemoryInfo("Iteração " + (i + 1));
        }

        System.out.println("❌ Problema identificado: Objetos acumulando na memória!");
    }

    /**
     * Demonstra a solução implementada
     */
    private static void demonstrarSolucao() {
        System.out.println("\n✅ === SOLUÇÃO: CACHE OTIMIZADO ===");

        // Força GC para limpar memória do exemplo anterior
        System.gc();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        memoryMonitor.printMemoryInfo("Após limpeza");

        ImprovedProductCache cacheOtimizado = new ImprovedProductCache();

        // Demonstra o cache otimizado
        for (int i = 0; i < 5; i++) {
            cacheOtimizado.loadProducts();
            memoryMonitor.printMemoryInfo("Iteração otimizada " + (i + 1));
        }

        System.out.println("✅ Solução implementada: Memória controlada!");
    }

    /**
     * Demonstra diferentes tipos de referências
     */
    private static void demonstrarTiposReferencias() {
        System.out.println("\n🔗 === TIPOS DE REFERÊNCIAS ===");

        // 1. Referência Forte (Strong Reference)
        User usuarioForte = new User("Carlos", "carlos@email.com");
        System.out.println("Referência Forte criada: " + usuarioForte.getName());

        // 2. Referência Fraca (Weak Reference)
        User usuarioTemp = new User("Temporário", "temp@email.com");
        WeakReference<User> referenciaFraca = new WeakReference<>(usuarioTemp);
        usuarioTemp = null; // Remove referência forte

        // 3. Referência Suave (Soft Reference)
        User usuarioCache = new User("Cache", "cache@email.com");
        SoftReference<User> referenciaSuave = new SoftReference<>(usuarioCache);
        usuarioCache = null; // Remove referência forte

        // 4. Referência Fantasma (Phantom Reference)
        ReferenceQueue<User> filaReferencias = new ReferenceQueue<>();
        User usuarioFantasma = new User("Fantasma", "fantasma@email.com");
        PhantomReference<User> referenciaFantasma = new PhantomReference<>(usuarioFantasma, filaReferencias);
        usuarioFantasma = null; // Remove referência forte

        // Força GC
        System.gc();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Verifica estado após GC
        System.out.println("Após GC:");
        System.out.println("- Referência Forte: " + usuarioForte.getName());
        System.out.println("- Referência Fraca: " + (referenciaFraca.get() != null ? referenciaFraca.get().getName() : "null"));
        System.out.println("- Referência Suave: " + (referenciaSuave.get() != null ? referenciaSuave.get().getName() : "null"));
        System.out.println("- Referência Fantasma: " + (referenciaFantasma.get() != null ? "Presente" : "null"));
    }

    /**
     * Demonstra monitoramento avançado do GC
     */
    private static void demonstrarMonitoramentoAvancado() {
        System.out.println("\n📊 === MONITORAMENTO AVANÇADO ===");

        // Obtém informações dos coletores de lixo
        List<java.lang.management.GarbageCollectorMXBean> gcBeans =
                java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();

        System.out.println("Coletores de Lixo ativos:");
        for (java.lang.management.GarbageCollectorMXBean gcBean : gcBeans) {
            System.out.println("- " + gcBean.getName() +
                    " (Coletas: " + gcBean.getCollectionCount() +
                    ", Tempo: " + gcBean.getCollectionTime() + "ms)");
        }

        // Informações da heap
        java.lang.management.MemoryMXBean memoryBean =
                java.lang.management.ManagementFactory.getMemoryMXBean();

        System.out.println("\nInformações da Heap:");
        System.out.println("- Heap usada: " + formatBytes(memoryBean.getHeapMemoryUsage().getUsed()));
        System.out.println("- Heap máxima: " + formatBytes(memoryBean.getHeapMemoryUsage().getMax()));
        System.out.println("- Non-Heap usada: " + formatBytes(memoryBean.getNonHeapMemoryUsage().getUsed()));
    }

    /**
     * Demonstra otimizações para reduzir pressão no GC
     */
    private static void demonstrarOtimizacoes() {
        System.out.println("\n⚡ === OTIMIZAÇÕES PARA GC ===");

        // 1. Reutilização de objetos
        demonstrarReutilizacaoObjetos();

        // 2. String interning
        demonstrarStringInterning();

        // 3. Pools de objetos
        demonstrarObjectPools();

        // 4. Lazy initialization
        demonstrarLazyInitialization();
    }

    private static void demonstrarReutilizacaoObjetos() {
        System.out.println("\n1. Reutilização de StringBuilder:");

        // ❌ Forma ineficiente
        long startTime = System.nanoTime();
        String resultado = "";
        for (int i = 0; i < 1000; i++) {
            resultado += "Item " + i + " ";
        }
        long tempoIneficiente = System.nanoTime() - startTime;

        // ✅ Forma eficiente
        startTime = System.nanoTime();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("Item ").append(i).append(" ");
        }
        String resultadoEficiente = sb.toString();
        long tempoEficiente = System.nanoTime() - startTime;

        System.out.println("- Concatenação String: " + tempoIneficiente / 1_000_000 + "ms");
        System.out.println("- StringBuilder: " + tempoEficiente / 1_000_000 + "ms");
        System.out.println("- Melhoria: " + (tempoIneficiente / tempoEficiente) + "x mais rápido");
    }

    private static void demonstrarStringInterning() {
        System.out.println("\n2. String Interning:");

        String str1 = "Hello World";
        String str2 = "Hello World";
        String str3 = new String("Hello World");
        String str4 = str3.intern();

        System.out.println("- str1 == str2: " + (str1 == str2) + " (string literals)");
        System.out.println("- str1 == str3: " + (str1 == str3) + " (new String)");
        System.out.println("- str1 == str4: " + (str1 == str4) + " (após intern())");
    }

    private static void demonstrarObjectPools() {
        System.out.println("\n3. Object Pools:");

        // Exemplo simples de pool
        Queue<StringBuilder> pool = new ArrayDeque<>();

        // Inicializa pool
        for (int i = 0; i < 10; i++) {
            pool.offer(new StringBuilder());
        }

        // Usa objeto do pool
        StringBuilder sb = pool.poll();
        if (sb != null) {
            sb.append("Usando objeto do pool");
            System.out.println("- " + sb.toString());

            // Retorna ao pool após uso
            sb.setLength(0); // Limpa
            pool.offer(sb);
        }

        System.out.println("- Pool size: " + pool.size());
    }

    private static void demonstrarLazyInitialization() {
        System.out.println("\n4. Lazy Initialization:");

        class LazyResource {
            private volatile Map<String, String> cache;

            public Map<String, String> getCache() {
                if (cache == null) {
                    synchronized (this) {
                        if (cache == null) {
                            cache = new ConcurrentHashMap<>();
                            System.out.println("- Cache inicializado sob demanda");
                        }
                    }
                }
                return cache;
            }
        }

        LazyResource resource = new LazyResource();
        System.out.println("- Recurso criado (cache ainda não inicializado)");

        resource.getCache().put("key", "value");
        System.out.println("- Cache usado pela primeira vez");
    }

    private static String formatBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        if (bytes < 1024 * 1024 * 1024) return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
        return String.format("%.1f GB", bytes / (1024.0 * 1024.0 * 1024.0));
    }
}