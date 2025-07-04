package br.com.thiagobianeck.techcorpgenerics.service;

import br.com.thiagobianeck.techcorpgenerics.model.Produto;
import br.com.thiagobianeck.techcorpgenerics.repository.GenericRepository;
import br.com.thiagobianeck.techcorpgenerics.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service para gerenciar produtos usando Generics avançados
 */
public class ProdutoService {

    private final GenericRepository<Produto, Long> produtoRepository;

    public ProdutoService() {
        this.produtoRepository = new GenericRepository<>(Produto.class);
    }

    /**
     * Método com Upper Bounded Wildcard
     * Aceita listas de Produto ou suas subclasses
     * @param produtos Lista de produtos (ou subclasses)
     */
    public void adicionarProdutos(List<? extends Produto> produtos) {
        Objects.requireNonNull(produtos, "Lista de produtos não pode ser null");

        for (Produto produto : produtos) {
            produtoRepository.save(produto, produto.getId());
        }
        System.out.println("Adicionados " + produtos.size() + " produtos");
    }

    /**
     * Método com Lower Bounded Wildcard
     * Aceita listas que podem receber Produto ou suas superclasses
     * @param destino Lista de destino
     */
    public void copiarProdutos(List<? super Produto> destino) {
        Objects.requireNonNull(destino, "Lista de destino não pode ser null");

        List<Produto> produtos = produtoRepository.findAll();
        destino.addAll(produtos);
        System.out.println("Copiados " + produtos.size() + " produtos para a lista de destino");
    }

    /**
     * Método genérico para buscar produtos por critério
     * @param <T> Tipo do valor de busca
     * @param extractor Função para extrair o valor de comparação
     * @param valor Valor a ser comparado
     * @return Lista de produtos encontrados
     */
    public <T> List<Produto> buscarPor(java.util.function.Function<Produto, T> extractor, T valor) {
        Objects.requireNonNull(extractor, "Extractor não pode ser null");
        Objects.requireNonNull(valor, "Valor não pode ser null");

        return produtoRepository.findByFilter(produto ->
                Objects.equals(extractor.apply(produto), valor));
    }

    /**
     * Método para demonstrar Type Inference
     * @param produtos Lista de produtos
     * @return Map agrupado por categoria
     */
    public Map<String, List<Produto>> agruparPorCategoria(List<Produto> produtos) {
        // Type inference - não precisa especificar tipos nos <>
        return CollectionUtils.groupBy(produtos, Produto::getCategoria);
    }

    /**
     * Método com múltiplos type parameters
     * @param <K> Tipo da chave
     * @param <V> Tipo do valor
     * @param keyExtractor Função para extrair chave
     * @param valueExtractor Função para extrair valor
     * @return Map com chave-valor customizados
     */
    public <K, V> Map<K, V> criarMapCustomizado(
            java.util.function.Function<Produto, K> keyExtractor,
            java.util.function.Function<Produto, V> valueExtractor) {

        return produtoRepository.findAll().stream()
                .collect(Collectors.toMap(keyExtractor, valueExtractor));
    }

    /**
     * Método com bounded type parameters
     * @param <T> Tipo que deve ser Comparable
     * @param extractor Função para extrair valor comparável
     * @return Produto com maior valor
     */
    public <T extends Comparable<T>> Optional<Produto> buscarMaior(
            java.util.function.Function<Produto, T> extractor) {

        return produtoRepository.findAll().stream()
                .max(Comparator.comparing(extractor));
    }

    // Métodos auxiliares para demonstração
    public void criarProdutosExemplo() {
        List<Produto> produtos = Arrays.asList(
                new Produto(1L, "Notebook Dell", 2500.0, "Eletrônicos"),
                new Produto(2L, "Mouse Gamer", 150.0, "Eletrônicos"),
                new Produto(3L, "Cadeira Ergonômica", 800.0, "Móveis"),
                new Produto(4L, "Mesa de Escritório", 600.0, "Móveis"),
                new Produto(5L, "Smartphone", 1200.0, "Eletrônicos")
        );

        adicionarProdutos(produtos);
    }

    public GenericRepository<Produto, Long> getRepository() {
        return produtoRepository;
    }
}