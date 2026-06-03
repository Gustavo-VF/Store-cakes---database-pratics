package edu.fatec.poo.persistence.daoIntefaces;

import edu.fatec.poo.model.Cliente;
import edu.fatec.poo.model.Pedido;
import edu.fatec.poo.model.RelatorioPedidoProduto;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Interface de Acesso a Dados (DAO) especializada para a entidade {@link Pedido}.
 * Herda as operações padrão de CRUD da interface {@link BasicDAO} e implementa
 * métodos específicos para gerenciar e consultar o histórico de compras dos clientes.
 *
 * @see BasicDAO
 * @see Pedido
 * @see Cliente
 */
public interface PedidoDAO extends BasicDAO<Pedido> {

    /**
     * Busca todos os pedidos realizados por um determinado cliente.
     * <p>
     * Este método mapeia o relacionamento entre o cliente e suas compras, sendo ideal
     * para renderizar o histórico de pedidos na área do cliente ou em relatórios de vendas.
     * </p>
     *
     * @param cliente O objeto {@link Cliente} proprietário dos pedidos que se deseja localizar.
     * @return Um {@link Optional} contendo uma {@link List} com todos os {@link Pedido}s vinculados
     * ao cliente, ou um {@link Optional#empty()} caso o cliente não possua histórico de compras.
     * @throws SQLException           Se ocorrer um erro na execução da consulta SQL no banco de dados.
     * @throws ClassNotFoundException Se o driver JDBC do banco de dados não for localizado.
     */
    Optional<List<Pedido>> findByCliente(Cliente cliente) throws SQLException, ClassNotFoundException;

    Optional<List<RelatorioPedidoProduto>> getSoldInLastYear() throws SQLException, ClassNotFoundException;

    Optional<List<RelatorioPedidoProduto>> getSoldInLastMonth() throws SQLException, ClassNotFoundException;

}