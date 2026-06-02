package edu.fatec.poo.persistence.daoIntefaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface genérica que define as operações básicas de um CRUD (Create, Read, Update, Delete)
 * utilizando o padrão Data Access Object (DAO).
 *
 * @param <T> O tipo da entidade de domínio que será manipulada pelo DAO.
 * @version 1.0
 */
public interface BasicDAO<T> {

    /**
     * Insere um novo registro no banco de dados.
     *
     * @param object O objeto contendo os dados a serem persistidos.
     * @return Um {@link Optional} contendo o objeto persistido (geralmente incluindo chaves
     * primárias geradas pelo banco), ou um {@link Optional#empty()} se a inserção falhar.
     * @throws SQLException           Se ocorrer um erro de sintaxe ou de execução no banco de dados.
     * @throws ClassNotFoundException Se o driver JDBC de banco de dados não for encontrado.
     */
    Optional<T> add(T object) throws SQLException, ClassNotFoundException;

    /**
     * Busca um registro no banco de dados através do seu identificador único (UUID).
     *
     * @param id O {@link UUID} do registro que deseja encontrar.
     * @return Um {@link Optional} contendo o objeto encontrado, ou um {@link Optional#empty()}
     * caso nenhum registro corresponda ao ID fornecido.
     * @throws SQLException           Se ocorrer um erro no acesso ao banco de dados.
     * @throws ClassNotFoundException Se o driver JDBC de banco de dados não for encontrado.
     */
    Optional<T> findById(UUID id) throws SQLException, ClassNotFoundException;

    /**
     * Recupera todos os registros da entidade presentes no banco de dados.
     *
     * @return Um {@link Optional} contendo a lista com todos os objetos encontrados,
     * ou um {@link Optional#empty()} se a consulta não retornar resultados ou falhar.
     * @throws SQLException           Se ocorrer um erro no acesso ao banco de dados.
     * @throws ClassNotFoundException Se o driver JDBC de banco de dados não for encontrado.
     */
    Optional<List<T>> searchAll() throws SQLException, ClassNotFoundException;

    /**
     * Atualiza os dados de um registro existente no banco de dados.
     *
     * @param object O objeto com as alterações que devem ser salvas.
     * @return Um {@link Optional} contendo o objeto atualizado, ou um {@link Optional#empty()}
     * caso a atualização não seja bem-sucedida.
     * @throws SQLException           Se ocorrer um erro de restrição ou execução no banco de dados.
     * @throws ClassNotFoundException Se o driver JDBC de banco de dados não for encontrado.
     */
    Optional<T> update(T object) throws SQLException, ClassNotFoundException;

    /**
     * Remove um registro do banco de dados.
     *
     * @param object O objeto que representa o registro a ser excluído.
     * @return Um {@link Optional} contendo o objeto que foi removido, ou um {@link Optional#empty()}
     * se a exclusão falhar.
     * @throws SQLException           Se ocorrer um erro de integridade referencial ou execução no banco de dados.
     * @throws ClassNotFoundException Se o driver JDBC de banco de dados não for encontrado.
     */
    Optional<T> delete(T object) throws SQLException, ClassNotFoundException;
}