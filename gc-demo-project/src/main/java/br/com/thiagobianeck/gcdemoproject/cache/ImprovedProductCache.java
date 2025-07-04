package br.com.thiagobianeck.gcdemoproject.cache;

import br.com.thiagobianeck.gcdemoproject.model.Product;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.ref.SoftReference;

/**
 * Cache otimizado que resolve o problema de vazamento de memória
 * Esta é a solução que Marina propôs
 */
public class ImprovedProductCache {
    private final Map<String, SoftReference<List<Product>>> categoryCache = new ConcurrentHashMap<>();
    private final Map<Long, SoftReference<Product>> productCache = new ConcurrentHashMap<>();
    private static final Random random = new Random();
    private static final int MAX_CACHE_SIZE = 10000;

    /*
     * 🔄 SOLUÇÃO: Carrega produtos de forma otimizada
     * Utiliza SoftReference para permitir coleta de lixo quando necessário
     * Organiza produtos por categoria e mantém cache individual
     */

    /**
     * ✅ SOLUÇÃO: Gerencia memória adequadamente
     */
    public void loadProducts() {
        System.out.println("🔄 Carregando produtos (método otimizado)...");

        // Limpa cache antigo se necessário
        cleanupCache();

        // Simula carregamento do banco de dados
        List<Product> newProducts = simulateProductsFromDatabase();

        // Organiza por categoria usando SoftReference
        Map<String, List<Product>> productsByCategory = new HashMap<>();
        for (Product product : newProducts) {
            productsByCategory.computeIfAbsent(product.getCategory(), k -> new ArrayList<>()).add(product);

            // Cache individual de produtos
            productCache.put(product.getId(), new SoftReference<>(product));
        }

        // Atualiza cache por categoria
        for (Map.Entry<String, List<Product>> entry : productsByCategory.entrySet()) {
            categoryCache.put(entry.getKey(), new SoftReference<>(entry.getValue()));
        }

        System.out.println("📦 Cache atualizado - Categorias: " + categoryCache.size() +
                ", Produtos: " + productCache.size());
    }

    /**
     * Busca produtos por categoria
     */
    public List<Product> getProductsByCategory(String category) {
        SoftReference<List<Product>> ref = categoryCache.get(category);
        if (ref != null) {
            List<Product> products = ref.get();
            if (products != null) {
                return new ArrayList<>(products);
            } else {
                // Referência foi coletada pelo GC, remove do cache
                categoryCache.remove(category);
            }
        }
        return new ArrayList<>();
    }

    /**
     * Busca produto por ID
     */
    public Product getProductById(Long id) {
        SoftReference<Product> ref = productCache.get(id);
        if (ref != null) {
            Product product = ref.get();
            if (product != null) {
                return product;
            } else {
                // Referência foi coletada pelo GC, remove do cache
                productCache.remove(id);
            }
        }
        return null;
    }

    /**
     * Limpeza proativa do cache
     */
    private void cleanupCache() {
        // Remove referências vazias
        categoryCache.entrySet().removeIf(entry -> entry.getValue().get() == null);
        productCache.entrySet().removeIf(entry -> entry.getValue().get() == null);

        // Se ainda está muito grande, força limpeza
        if (productCache.size() > MAX_CACHE_SIZE) {
            System.out.println("🧹 Cache muito grande, forçando limpeza...");

            // Remove metade dos itens mais antigos (simulação)
            Iterator<Map.Entry<Long, SoftReference<Product>>> iterator = productCache.entrySet().iterator();
            int toRemove = productCache.size() / 2;
            int removed = 0;

            while (iterator.hasNext() && removed < toRemove) {
                iterator.next();
                iterator.remove();
                removed++;
            }
        }
    }

    /**
     * Informações do cache
     */
    public String getCacheInfo() {
        int activeCategoryCache = 0;
        int activeProductCache = 0;

        for (SoftReference<List<Product>> ref : categoryCache.values()) {
            if (ref.get() != null) activeCategoryCache++;
        }

        for (SoftReference<Product> ref : productCache.values()) {
            if (ref.get() != null) activeProductCache++;
        }

        return String.format("Cache Info - Categorias: %d/%d, Produtos: %d/%d",
                activeCategoryCache, categoryCache.size(),
                activeProductCache, productCache.size());
    }

    /**
     * Limpa todo o cache
     */
    public void clearCache() {
        categoryCache.clear();
        productCache.clear();
        System.out.println("🧹 Cache limpo completamente");
    }

    /**
     * Simula busca no banco de dados
     */
    private List<Product> simulateProductsFromDatabase() {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Product product = new Product(
                    (long) i,
                    "Produto " + i,
                    "Descrição do produto " + i,
                    new BigDecimal(random.nextInt(1000) + 1),
                    "Categoria " + (i % 10)
            );
            products.add(product);
        }

        return products;
    }
}
