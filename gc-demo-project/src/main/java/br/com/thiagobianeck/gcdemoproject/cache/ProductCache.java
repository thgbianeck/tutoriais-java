package br.com.thiagobianeck.gcdemoproject.cache;

import br.com.thiagobianeck.gcdemoproject.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Cache problemático que causa vazamento de memória
 * Este é o código original que Carlos escreveu
 */
public class ProductCache {
    private static List<Product> allProducts = new ArrayList<>();
    private static final Random random = new Random();

    /**
     * ❌ PROBLEMA: Acumula objetos sem limpar
     */
    public void loadProducts() {
        System.out.println("🔄 Carregando produtos (método problemático)...");

        // Simula carregamento do banco de dados
        List<Product> newProducts = simulateProductsFromDatabase();

        // PROBLEMA: Adiciona sem limpar a lista anterior
        allProducts.addAll(newProducts);

        System.out.println("📦 Total de produtos no cache: " + allProducts.size());
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(allProducts);
    }

    public int getCacheSize() {
        return allProducts.size();
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
                    "Descrição do produto " + i + " com detalhes extensos para ocupar mais memória",
                    new BigDecimal(random.nextInt(1000) + 1),
                    "Categoria " + (i % 10)
            );
            products.add(product);
        }

        return products;
    }
}