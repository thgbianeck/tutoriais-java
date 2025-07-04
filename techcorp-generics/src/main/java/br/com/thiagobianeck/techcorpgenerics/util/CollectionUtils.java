package br.com.thiagobianeck.techcorpgenerics.util;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Utilitários genéricos para trabalhar com coleções
 */
public class CollectionUtils {

    /**
     * Método genérico para filtrar uma lista
     * @param <T> Tipo dos elementos da lista
     * @param list Lista a ser filtrada
     * @param predicate Condição de filtro
     * @return Nova lista filtrada
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        Objects.requireNonNull(list, "Lista não pode ser null");
        Objects.requireNonNull(predicate, "Predicate não pode ser null");

        List<T> result = new ArrayList<>();
        for (T item : list) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Método genérico para transformar uma lista
     * @param <T> Tipo dos elementos da lista original
     * @param <R> Tipo dos elementos da lista resultado
     * @param list Lista original
     * @param mapper Função de transformação
     * @return Nova lista transformada
     */
    public static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        Objects.requireNonNull(list, "Lista não pode ser null");
        Objects.requireNonNull(mapper, "Mapper não pode ser null");

        List<R> result = new ArrayList<>();
        for (T item : list) {
            result.add(mapper.apply(item));
        }
        return result;
    }

    /**
     * Método genérico para encontrar o primeiro elemento que atende a condição
     * @param <T> Tipo dos elementos da lista
     * @param list Lista a ser pesquisada
     * @param predicate Condição de busca
     * @return Optional com o primeiro elemento encontrado
     */
    public static <T> Optional<T> findFirst(List<T> list, Predicate<T> predicate) {
        Objects.requireNonNull(list, "Lista não pode ser null");
        Objects.requireNonNull(predicate, "Predicate não pode ser null");

        for (T item : list) {
            if (predicate.test(item)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    /**
     * Método genérico para ordenar uma lista
     * @param <T> Tipo dos elementos da lista
     * @param list Lista a ser ordenada
     * @param comparator Comparador para ordenação
     * @return Nova lista ordenada
     */
    public static <T> List<T> sort(List<T> list, Comparator<T> comparator) {
        Objects.requireNonNull(list, "Lista não pode ser null");
        Objects.requireNonNull(comparator, "Comparator não pode ser null");

        List<T> result = new ArrayList<>(list);
        result.sort(comparator);
        return result;
    }

    /**
     * Método genérico para agrupar elementos de uma lista
     * @param <T> Tipo dos elementos da lista
     * @param <K> Tipo da chave de agrupamento
     * @param list Lista a ser agrupada
     * @param keyExtractor Função para extrair a chave
     * @return Map com os elementos agrupados
     */
    public static <T, K> Map<K, List<T>> groupBy(List<T> list, Function<T, K> keyExtractor) {
        Objects.requireNonNull(list, "Lista não pode ser null");
        Objects.requireNonNull(keyExtractor, "KeyExtractor não pode ser null");

        Map<K, List<T>> result = new HashMap<>();
        for (T item : list) {
            K key = keyExtractor.apply(item);
            result.computeIfAbsent(key, k -> new ArrayList<>()).add(item);
        }
        return result;
    }
}