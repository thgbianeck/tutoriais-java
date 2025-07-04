package br.com.thiagobianeck.codeflowreferences.references;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class WeakReferenceDemo {
    private static final List<WeakReference<String>> weakReferences = new ArrayList<>();

    public static void demonstrateWeakReference() {
        System.out.println("=== WEAK REFERENCE DEMO ===");

        // Criar objetos com referências fracas
        for (int i = 0; i < 100; i++) {
            String data = "WeakData-" + i;
            WeakReference<String> weakRef = new WeakReference<>(data);
            weakReferences.add(weakRef);
        }

        System.out.println("Weak References criadas: " + weakReferences.size());

        // Verificar quantos objetos ainda existem
        int aliveCount = countAliveReferences();
        System.out.println("Objetos vivos inicialmente: " + aliveCount);

        // Forçar coleta de lixo
        System.out.println("Forçando Garbage Collection...");
        System.gc();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Verificar novamente
        aliveCount = countAliveReferences();
        System.out.println("Objetos vivos após GC: " + aliveCount);
        System.out.println("✅ Weak References são coletadas rapidamente!");
        System.out.println();
    }

    private static int countAliveReferences() {
        int count = 0;
        for (WeakReference<String> ref : weakReferences) {
            if (ref.get() != null) {
                count++;
            }
        }
        return count;
    }

    public static void demonstrateWeakHashMap() {
        System.out.println("=== WEAK HASH MAP DEMO ===");

        WeakHashMap<String, String> weakMap = new WeakHashMap<>();

        // Adicionar dados com chaves que serão coletadas
        for (int i = 0; i < 10; i++) {
            String key = new String("key-" + i); // Criar nova instância
            weakMap.put(key, "Value " + i);
        }

        System.out.println("WeakHashMap size inicial: " + weakMap.size());

        // Forçar coleta de lixo
        System.gc();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("WeakHashMap size após GC: " + weakMap.size());
        System.out.println("✅ WeakHashMap remove entradas automaticamente!");
        System.out.println();
    }

    public static void demonstrateObserverPattern() {
        System.out.println("=== WEAK OBSERVER PATTERN DEMO ===");

        EventPublisher publisher = new EventPublisher();

        // Criar observers que serão coletados
        for (int i = 0; i < 5; i++) {
            Observer observer = new Observer("Observer-" + i);
            publisher.addObserver(observer);
        }

        System.out.println("Observers registrados: " + publisher.getObserverCount());

        // Publicar evento
        publisher.publishEvent("Primeiro evento");

        // Forçar coleta de lixo
        System.gc();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Limpar referências mortas
        publisher.cleanupDeadReferences();

        System.out.println("Observers após limpeza: " + publisher.getObserverCount());

        // Publicar outro evento
        publisher.publishEvent("Segundo evento");

        System.out.println("✅ Observer pattern com Weak References previne vazamentos!");
        System.out.println();
    }

    private static class EventPublisher {
        private final List<WeakReference<Observer>> observers = new ArrayList<>();

        public void addObserver(Observer observer) {
            observers.add(new WeakReference<>(observer));
        }

        public void publishEvent(String event) {
            System.out.println("Publicando evento: " + event);
            for (WeakReference<Observer> ref : observers) {
                Observer observer = ref.get();
                if (observer != null) {
                    observer.onEvent(event);
                }
            }
        }

        public void cleanupDeadReferences() {
            observers.removeIf(ref -> ref.get() == null);
        }

        public int getObserverCount() {
            return observers.size();
        }
    }

    private static class Observer {
        private final String name;

        public Observer(String name) {
            this.name = name;
        }

        public void onEvent(String event) {
            System.out.println(name + " recebeu: " + event);
        }
    }
}