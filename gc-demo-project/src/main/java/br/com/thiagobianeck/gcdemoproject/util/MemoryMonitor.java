package br.com.thiagobianeck.gcdemoproject.util;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.text.DecimalFormat;

/**
 * Utilitário para monitoramento de memória e GC
 */
public class MemoryMonitor {
    private final MemoryMXBean memoryBean;
    private final DecimalFormat df = new DecimalFormat("#,##0.00");

    public MemoryMonitor() {
        this.memoryBean = ManagementFactory.getMemoryMXBean();
    }

    /**
     * Imprime informações detalhadas sobre o uso de memória
     */
    public void printMemoryInfo(String context) {
        System.out.println("\n📊 === MEMÓRIA: " + context + " ===");

        // Informações da Heap
        MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
        System.out.println("🏠 Heap Memory:");
        System.out.println("  ├─ Usada: " + formatBytes(heapUsage.getUsed()));
        System.out.println("  ├─ Committed: " + formatBytes(heapUsage.getCommitted()));
        System.out.println("  ├─ Máxima: " + formatBytes(heapUsage.getMax()));
        System.out.println("  └─ Uso: " + df.format((double)heapUsage.getUsed() / heapUsage.getMax() * 100) + "%");

        // Informações Non-Heap
        MemoryUsage nonHeapUsage = memoryBean.getNonHeapMemoryUsage();
        System.out.println("🏢 Non-Heap Memory:");
        System.out.println("  ├─ Usada: " + formatBytes(nonHeapUsage.getUsed()));
        System.out.println("  ├─ Committed: " + formatBytes(nonHeapUsage.getCommitted()));
        System.out.println("  └─ Máxima: " + formatBytes(nonHeapUsage.getMax()));

        // Informações do Sistema
        Runtime runtime = Runtime.getRuntime();
        System.out.println("💻 Sistema:");
        System.out.println("  ├─ Processadores: " + runtime.availableProcessors());
        System.out.println("  ├─ Total JVM: " + formatBytes(runtime.totalMemory()));
        System.out.println("  ├─ Livre JVM: " + formatBytes(runtime.freeMemory()));
        System.out.println("  └─ Máxima JVM: " + formatBytes(runtime.maxMemory()));
    }

    /**
     * Obtém uso atual da heap em percentual
     */
    public double getHeapUsagePercent() {
        MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
        return (double) heapUsage.getUsed() / heapUsage.getMax() * 100;
    }

    /**
     * Verifica se a memória está sob pressão
     */
    public boolean isMemoryUnderPressure() {
        return getHeapUsagePercent() > 80.0;
    }

    /**
     * Formata bytes para formato legível
     */
    private String formatBytes(long bytes) {
        if (bytes < 0) return "N/A";
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return df.format(bytes / 1024.0) + " KB";
        if (bytes < 1024 * 1024 * 1024) return df.format(bytes / (1024.0 * 1024.0)) + " MB";
        return df.format(bytes / (1024.0 * 1024.0 * 1024.0)) + " GB";
    }
}
