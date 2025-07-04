package br.com.thiagobianeck.techcorpgenerics;

import br.com.thiagobianeck.techcorpgenerics.model.Produto;
import br.com.thiagobianeck.techcorpgenerics.service.ProdutoService;
import br.com.thiagobianeck.techcorpgenerics.util.CollectionUtils;

import java.util.*;

/**
 * Classe principal demonstrando o uso prático de Generics
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== TECHCORP GENERICS DEMO ===\n");

        // Criando o service
        ProdutoService service = new ProdutoService();

        // Demonstração 1: Criando produtos exemplo
        System.out.println("📦 Criando produtos exemplo...");
        service.criarProdutosExemplo();
        System.out.println();

        // Demonstração 2: Listando todos os produtos
        System.out.println("📋 Listando todos os produtos:");
        List<Produto> todosProdutos = service.getRepository().findAll();
        todosProdutos.forEach(System.out::println);
        System.out.println();

        // Demonstração 3: Buscando produtos por categoria
        System.out.println("🔍 Buscando produtos da categoria 'Eletrônicos':");
        List<Produto> eletronicos = service.buscarPor(Produto::getCategoria, "Eletrônicos");
        eletronicos.forEach(System.out::println);
        System.out.println();

        // Demonstração 4: Agrupando produtos por categoria
        System.out.println("📊 Agrupando produtos por categoria:");
        Map<String, List<Produto>> agrupados = service.agruparPorCategoria(todosProdutos);
        agrupados.forEach((categoria, produtos) -> {
            System.out.println("Categoria: " + categoria);
            produtos.forEach(p -> System.out.println("  - " + p.getNome()));
        });
        System.out.println();

        // Demonstração 5: Usando CollectionUtils
        System.out.println("🛠️ Usando CollectionUtils:");

        // Filtrando produtos caros (> 1000)
        List<Produto> produtosCaros = CollectionUtils.filter(todosProdutos,
                p -> p.getPreco() > 1000);
        System.out.println("Produtos caros (> R$ 1000):");
        produtosCaros.forEach(p -> System.out.println("  - " + p.getNome() + ": R$ " + p.getPreco()));

        // Mapeando para nomes
        List<String> nomes = CollectionUtils.map(todosProdutos, Produto::getNome);
        System.out.println("Nomes dos produtos: " + nomes);

        // Ordenando por preço
        List<Produto> ordenadosPorPreco = CollectionUtils.sort(todosProdutos,
                Comparator.comparing(Produto::getPreco));
        System.out.println("Produtos ordenados por preço:");
        ordenadosPorPreco.forEach(p -> System.out.println("  - " + p.getNome() + ": R$ " + p.getPreco()));
        System.out.println();

        // Demonstração 6: Produto mais caro
        System.out.println("💰 Produto mais caro:");
        Optional<Produto> maisCaro = service.buscarMaior(Produto::getPreco);
        maisCaro.ifPresent(p -> System.out.println("  - " + p.getNome() + ": R$ " + p.getPreco()));
        System.out.println();

        // Demonstração 7: Map customizado (ID -> Nome)
        System.out.println("🗂️ Map customizado (ID -> Nome):");
        Map<Long, String> mapIdNome = service.criarMapCustomizado(Produto::getId, Produto::getNome);
        mapIdNome.forEach((id, nome) -> System.out.println("  ID " + id + ": " + nome));
        System.out.println();

        // Demonstração 8: Wildcards em ação
        System.out.println("🎯 Demonstrando Wildcards:");

        // Upper bounded wildcard (? extends Produto)
        List<Produto> novosProdutos = Arrays.asList(
                new Produto(6L, "Teclado Mecânico", 350.0, "Eletrônicos"),
                new Produto(7L, "Monitor 4K", 1800.0, "Eletrônicos")
        );
        service.adicionarProdutos(novosProdutos);

        // Lower bounded wildcard (? super Produto)
        List<Object> destinoGenerico = new ArrayList<>();
        service.copiarProdutos(destinoGenerico);
        System.out.println("Elementos copiados para lista genérica: " + destinoGenerico.size());
        System.out.println();

        // Demonstração 9: Estatísticas finais
        System.out.println("📈 Estatísticas finais:");
        System.out.println("Total de produtos: " + service.getRepository().count());
        System.out.println("Categorias disponíveis: " +
                service.agruparPorCategoria(service.getRepository().findAll()).keySet());

        // Preço médio
        double precoMedio = service.getRepository().findAll().stream()
                .mapToDouble(Produto::getPreco)
                .average()
                .orElse(0.0);
        System.out.println("Preço médio: R$ " + String.format("%.2f", precoMedio));

        System.out.println("\n=== DEMO CONCLUÍDA ===");
    }
}