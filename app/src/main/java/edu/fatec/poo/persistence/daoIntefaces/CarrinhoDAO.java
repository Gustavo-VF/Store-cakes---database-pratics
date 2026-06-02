package edu.fatec.poo.persistence.daoIntefaces;

import edu.fatec.poo.model.Carrinho;
import edu.fatec.poo.model.Cliente;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Interface de Acesso a Dados (DAO) especializada para a entidade {@link Carrinho}.
 * Herda as operações padrão de CRUD da interface {@link BasicDAO} e implementa
 * métodos específicos para gerenciar o ciclo de vida dos carrinhos de compras.
 *
 * @author Seu Nome / Grupo
 * @version 1.3
 * @see BasicDAO
 * @see Carrinho
 * @see Cliente
 */
public interface CarrinhoDAO extends BasicDAO<Carrinho> {

    /**
     * Busca o carrinho de compras ativo associado a um determinado cliente.
     * <p>
     * Este método utiliza a instância do {@link Cliente} para mapear o relacionamento
     * e recuperar o carrinho atual/aberto correspondente no banco de dados.
     * </p>
     *
     * @param cliente O objeto {@link Cliente} proprietário do carrinho.
     * @return Um {@link Optional} contendo o {@link Carrinho} ativo associado ao cliente,
     * ou um {@link Optional#empty()} caso o cliente não possua nenhum carrinho aberto.
     * @throws SQLException           Se ocorrer um erro na execução da consulta SQL no banco de dados.
     * @throws ClassNotFoundException Se o driver JDBC do banco de dados não for localizado.
     */
    Optional<Carrinho> findByCliente(Cliente cliente) throws SQLException, ClassNotFoundException;

    /**
     * Busca todos os carrinhos de compras (histórico, salvos ou abandonados)
     * associados a um determinado cliente.
     * <p>
     * Este método é útil para recuperar múltiplos registros vinculados ao mesmo cliente,
     * permitindo listar compras anteriores ou relatórios.
     * </p>
     *
     * @param cliente O objeto {@link Cliente} do qual se deseja obter os carrinhos.
     * @return Um {@link Optional} contendo uma {@link List} com todos os {@link Carrinho}s
     * vinculados ao cliente, ou um {@link Optional#empty()} caso nenhum registro seja encontrado.
     * @throws SQLException           Se ocorrer um erro na execução da consulta SQL no banco de dados.
     * @throws ClassNotFoundException Se o driver JDBC do banco de dados não for localizado.
     */
    Optional<List<Carrinho>> findByClienteMult(Cliente cliente) throws SQLException, ClassNotFoundException;
}