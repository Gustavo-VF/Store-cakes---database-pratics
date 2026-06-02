package edu.fatec.poo.persistence.daoIntefaces;

import edu.fatec.poo.model.Produto;
import edu.fatec.poo.model.TipoProduto;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Interface de Acesso a Dados (DAO) especializada para a entidade {@link Produto}.
 * Herda as operações básicas de CRUD da interface {@link BasicDAO} e define
 * consultas avançadas baseadas em filtros de nome, categoria e faixas de preço.
 *
 * @see BasicDAO
 * @see Produto
 * @see TipoProduto
 */
public interface ProdutoDAO extends BasicDAO<Produto> {

    /**
     * Busca produtos cujo nome contenha o termo especificado.
     * <p>
     * Geralmente implementado utilizando uma busca parcial (como o operador {@code LIKE %name%} no SQL),
     * sendo ideal para barras de pesquisa e catálogos.
     * </p>
     *
     * @param name O termo ou fragmento do nome do produto a ser pesquisado.
     * @return Um {@link Optional} contendo uma {@link List} com os produtos que correspondem ao critério,
     * ou um {@link Optional#empty()} caso o termo seja inválido ou nenhum produto seja encontrado.
     * @throws SQLException           Se ocorrer um erro de execução na consulta ao banco de dados.
     * @throws ClassNotFoundException Se o driver JDBC do banco de dados não for localizado.
     */
    Optional<List<Produto>> findByName(String name) throws SQLException, ClassNotFoundException;

    /**
     * Busca todos os produtos que pertencem a uma determinada categoria ou tipo de produto.
     *
     * @param tipoProduto O objeto {@link TipoProduto} (categoria) pelo qual filtrar os produtos.
     * @return Um {@link Optional} contendo uma {@link List} com os produtos vinculados à categoria informada,
     * ou um {@link Optional#empty()} caso a categoria não possua produtos ou a busca falhe.
     * @throws SQLException           Se ocorrer um erro na sintaxe ou execução da query SQL.
     * @throws ClassNotFoundException Se o driver JDBC do banco de dados não for localizado.
     */
    Optional<List<Produto>> findByTipoProduto(TipoProduto tipoProduto) throws SQLException, ClassNotFoundException;

    /**
     * Busca produtos cujos preços estejam compreendidos dentro do intervalo estipulado.
     * <p>
     * Este método permite a filtragem de catálogo por faixas de preço (ex: buscar produtos entre R$ 50,00 e R$ 150,00).
     * </p>
     *
     * @param precoMin O valor mínimo do preço do produto (limite inferior).
     * @param precoMax O valor máximo do preço do produto (limite superior).
     * @return Um {@link Optional} contendo uma {@link List} com os produtos encontrados na faixa informada,
     * ou um {@link Optional#empty()} se nenhum produto atender às restrições de preço.
     * @throws SQLException           Se ocorrer um erro no acesso ou na lógica da consulta ao banco de dados.
     * @throws ClassNotFoundException Se o driver JDBC do banco de dados não for localizado.
     */
    Optional<List<Produto>> findByPreco(Double precoMin, Double precoMax) throws SQLException, ClassNotFoundException;
}