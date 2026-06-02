package edu.fatec.poo.persistence.daoIntefaces;

import edu.fatec.poo.model.Cliente;

import java.sql.SQLException;
import java.util.Optional;

/**
 * Interface de Acesso a Dados (DAO) especializada para a entidade {@link Cliente}.
 * Herda as operações básicas de CRUD da interface {@link BasicDAO} e adiciona
 * consultas específicas necessárias para as regras de negócio de clientes.
 *
 * @version 1.0
 * @see BasicDAO
 * @see Cliente
 */
public interface ClienteDAO extends BasicDAO<Cliente> {

    /**
     * Busca um cliente no banco de dados utilizando o endereço de e-mail informado.
     * <p>
     * Este método é útil para cenários de autenticação, validação de unicidade de cadastro
     * ou recuperação de perfil.
     * </p>
     *
     * @param email O endereço de e-mail do cliente que se deseja localizar.
     * @return Um {@link Optional} contendo o {@link Cliente} encontrado, ou um
     * {@link Optional#empty()} caso nenhum cliente possua o e-mail fornecido.
     * @throws SQLException           Se ocorrer um erro de execução ou de sintaxe no banco de dados.
     * @throws ClassNotFoundException Se o driver JDBC do banco de dados não for localizado.
     */
    Optional<Cliente> findByEmail(String email) throws SQLException, ClassNotFoundException;
}