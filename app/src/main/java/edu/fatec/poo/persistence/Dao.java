package edu.fatec.poo.persistence;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface genérica para o padrão de projeto Data Access Object (DAO).
 * Define as operações básicas de CRUD (Create, Read, Update, Delete) para persistência de dados.
 *
 * @param <T> O tipo da entidade que será manipulada por esta interface.
 * @author Seu Nome
 * @version 1.0
 */
public interface Dao<T> {

    /**
     * Persiste um novo objeto no banco de dados.
     *
     * @param object O objeto a ser adicionado.
     * @throws SQLException Se ocorrer um erro durante o acesso ao banco de dados.
     */
    public void add(T object) throws SQLException;

    /**
     * Realiza a busca de um objeto específico.
     * Geralmente utiliza um atributo identificador presente no objeto de exemplo.
     *
     * @param object O objeto contendo os critérios de busca (ex: ID).
     * @return O objeto encontrado com os dados carregados do banco,
     * ou {@code null} caso não seja localizado.
     * @throws SQLException Se ocorrer um erro durante o acesso ao banco de dados.
     */
    public T search(T object) throws SQLException;

    /**
     * Atualiza os dados de um objeto já existente no banco de dados.
     *
     * @param object O objeto com os novos dados a serem persistidos.
     * @throws SQLException Se ocorrer um erro durante o acesso ao banco de dados.
     */
    public void update(T object) throws SQLException;

    /**
     * Remove um registro do banco de dados.
     *
     * @param object O objeto a ser excluído.
     * @throws SQLException Se ocorrer um erro durante o acesso ao banco de dados.
     */
    public void delete(T object) throws SQLException;

    /**
     * Recupera todos os registros da entidade armazenados no banco de dados.
     *
     * @return Uma {@link List} contendo todos os objetos encontrados,
     * ou uma lista vazia se não houver registros.
     * @throws SQLException Se ocorrer um erro durante o acesso ao banco de dados.
     */
    public List<T> searchAll() throws SQLException;
}