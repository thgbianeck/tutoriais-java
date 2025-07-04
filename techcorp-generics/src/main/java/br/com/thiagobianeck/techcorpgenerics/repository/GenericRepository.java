package br.com.thiagobianeck.techcorpgenerics.repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Repository genérico que pode trabalhar com qualquer tipo de entidade
 * @param <T> Tipo da entidade
 * @param <ID> Tipo do identificador
 */
public class GenericRepository<T, ID> {

    // Simulando um banco de dados em memória
    private final Map<ID, T> database = new HashMap<>();
    private final Class<T> entityType;

    public GenericRepository(Class<T> entityType) {
        this.entityType = entityType;
    }

    /**
     * Salva uma entidade no repository
     * @param entity Entidade a ser salva
     * @param id Identificador único
     * @return A entidade salva
     */
    public T save(T entity, ID id) {
        Objects.requireNonNull(entity, "Entidade não pode ser null");
        Objects.requireNonNull(id, "ID não pode ser null");

        database.put(id, entity);
        System.out.println("Entidade salva: " + entity);
        return entity;
    }

    /**
     * Busca uma entidade pelo ID
     * @param id Identificador da entidade
     * @return Optional contendo a entidade ou empty se não encontrada
     */
    public Optional<T> findById(ID id) {
        Objects.requireNonNull(id, "ID não pode ser null");
        return Optional.ofNullable(database.get(id));
    }

    /**
     * Retorna todas as entidades
     * @return Lista com todas as entidades
     */
    public List<T> findAll() {
        return new ArrayList<>(database.values());
    }

    /**
     * Remove uma entidade pelo ID
     * @param id Identificador da entidade
     * @return true se removida com sucesso, false caso contrário
     */
    public boolean deleteById(ID id) {
        Objects.requireNonNull(id, "ID não pode ser null");
        T removed = database.remove(id);
        if (removed != null) {
            System.out.println("Entidade removida: " + removed);
            return true;
        }
        return false;
    }

    /**
     * Verifica se existe uma entidade com o ID especificado
     * @param id Identificador da entidade
     * @return true se existe, false caso contrário
     */
    public boolean existsById(ID id) {
        Objects.requireNonNull(id, "ID não pode ser null");
        return database.containsKey(id);
    }

    /**
     * Conta o número total de entidades
     * @return Número de entidades
     */
    public long count() {
        return database.size();
    }

    /**
     * Busca entidades usando um filtro personalizado
     * @param filter Função de filtro
     * @return Lista de entidades que atendem ao filtro
     */
    public List<T> findByFilter(java.util.function.Predicate<T> filter) {
        Objects.requireNonNull(filter, "Filtro não pode ser null");
        return database.values().stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    /**
     * Limpa todos os dados do repository
     */
    public void clear() {
        database.clear();
        System.out.println("Repository limpo para tipo: " + entityType.getSimpleName());
    }
}