package edu.fatec.poo.persistence.daoIntefaces;

import edu.fatec.poo.model.Carrinho;
import edu.fatec.poo.model.ItemCarrinho;
import edu.fatec.poo.model.Produto;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Interface de Acesso a Dados (DAO) especializada para a entidade {@link ItemCarrinho}.
 * Herda as operações padrão de CRUD da interface {@link BasicDAO} e implementa
 * métodos específicos para consultar os itens contidos nos carrinhos ou vincular produtos.
 *
 * @see BasicDAO
 * @see ItemCarrinho
 * @see Carrinho
 * @see Produto
 */
public interface ItemCarrinhoDAO extends BasicDAO<ItemCarrinho> {

    /**
     * Busca todos os itens que estão associados a um determinado carrinho de compras.
     * <p>
     * Este método é ideal para carregar e listar todos os produtos e suas respectivas
     * quantidades que compõem o carrinho atual do cliente.
     * </p>
     *
     * @param carrinho O objeto {@link Carrinho} do qual se deseja extrair os itens.
     * @return Um {@link Optional} contendo uma {@link List} com os {@link ItemCarrinho} encontrados,
     * ou um {@link Optional#empty()} se o carrinho estiver vazio ou não possuir itens registrados.
     * @throws SQLException           Se ocorrer um erro na execução da consulta SQL no banco de dados.
     * @throws ClassNotFoundException Se o driver JDBC do banco de dados não for localizado.
     */
    Optional<List<ItemCarrinho>> findByCarrinho(Carrinho carrinho) throws SQLException, ClassNotFoundException;

    /**
     * Busca todos os itens de carrinho que contêm um determinado produto.
     * <p>
     * Este método é útil para cenários de auditoria, relatórios de vendas ou para verificar
     * em quantos carrinhos ativos (ou históricos) um produto específico foi adicionado.
     * </p>
     *
     * @param produto O objeto {@link Produto} que se deseja rastrear nos itens de carrinho.
     * @return Um {@link Optional} contendo uma {@link List} com os {@link ItemCarrinho} vinculados ao produto,
     * ou um {@link Optional#empty()} caso o produto não tenha sido adicionado a nenhum carrinho.
     * @throws SQLException           Se ocorrer um erro na execução da consulta SQL no banco de dados.
     * @throws ClassNotFoundException Se o driver JDBC do banco de dados não for localizado.
     */
    Optional<List<ItemCarrinho>> findByProduto(Produto produto) throws SQLException, ClassNotFoundException;
}