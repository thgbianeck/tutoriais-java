package br.com.thiagobianeck.codeflowreferences.references;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

public class PhantomReferenceDemo {
    private static final ReferenceQueue<String> referenceQueue = new ReferenceQueue<>();
    private static final List<PhantomReference<String>> phantomReferences = new ArrayList<>();

    public static void demonstratePhantomReference() {
        System.out.println("=== PHANTOM REFERENCE DEMO ===");

        // Criar objetos com referências fantasmas
        for (int i = 0; i < 10; i++) {
            String data = "PhantomData-" + i;
            PhantomReference<String> phantomRef = new PhantomReference<>(data, referenceQueue);
            phantomReferences.add(phantomRef);
        }

        System.out.println("Phantom References criadas: " + phantomReferences.size());

        // Verificar se get() sempre retorna null
        PhantomReference<String> firstRef = phantomReferences.get(0);
        System.out.println("phantomRef.get() retorna: " + firstRef.get());
        System.out.println("✅ Phantom Reference sempre retorna null no get()!");

        // Forçar coleta de lixo
        System.out.println("Forçando Garbage Collection...");
        System.gc();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Verificar referências na fila
        checkReferenceQueue();
        System.out.println();
    }

    private static void checkReferenceQueue() {
        System.out.println("Verificando Reference Queue...");

        int processedCount = 0;
        PhantomReference<?> ref;

        while ((ref = (PhantomReference<?>) referenceQueue.poll()) != null) {
            processedCount++;
            System.out.println("Referência fantasma processada: " + ref);

            // Aqui você pode fazer cleanup personalizado
            performCleanup(ref);
        }

        System.out.println("Total de referências processadas: " + processedCount);
        System.out.println("✅ Phantom References permitem cleanup controlado!");
    }

    private static void performCleanup(PhantomReference<?> ref) {
        // Simular cleanup de recursos
        System.out.println("  -> Executando cleanup para: " + ref.hashCode());
    }

    public static void demonstrateResourceManagement() {
        System.out.println("=== RESOURCE MANAGEMENT DEMO ===");

        ResourceManager manager = new ResourceManager();

        // Criar recursos que precisam de cleanup
        for (int i = 0; i < 5; i++) {
            NativeResource resource = new NativeResource("Resource-" + i);
            manager.trackResource(resource);
        }

        System.out.println("Recursos criados e rastreados: " + manager.getTrackedCount());

        // Forçar coleta de lixo
        System.gc();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Processar cleanup
        manager.processCleanup();

        System.out.println("✅ Resource management com Phantom References!");
        System.out.println();
    }

    private static class ResourceManager {
        private final ReferenceQueue<NativeResource> queue = new ReferenceQueue<>();
        private final List<ResourcePhantomReference> trackedResources = new ArrayList<>();

        public void trackResource(NativeResource resource) {
            ResourcePhantomReference ref = new ResourcePhantomReference(resource, queue);
            trackedResources.add(ref);
        }

        public void processCleanup() {
            System.out.println("Processando cleanup de recursos...");

            ResourcePhantomReference ref;
            while ((ref = (ResourcePhantomReference) queue.poll()) != null) {
                ref.cleanup();
                trackedResources.remove(ref);
            }
        }

        public int getTrackedCount() {
            return trackedResources.size();
        }

        private static class ResourcePhantomReference extends PhantomReference<NativeResource> {
            private final String resourceName;

            public ResourcePhantomReference(NativeResource resource, ReferenceQueue<NativeResource> queue) {
                super(resource, queue);
                this.resourceName = resource.getName();
            }

            public void cleanup() {
                System.out.println("  -> Limpando recurso: " + resourceName);
                // Aqui você faria o cleanup real do recurso nativo
            }
        }
    }

    private static class NativeResource {
        private final String name;

        public NativeResource(String name) {
            this.name = name;
            System.out.println("Recurso criado: " + name);
        }

        public String getName() {
            return name;
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("Finalizando recurso: " + name);
            super.finalize();
        }
    }
}