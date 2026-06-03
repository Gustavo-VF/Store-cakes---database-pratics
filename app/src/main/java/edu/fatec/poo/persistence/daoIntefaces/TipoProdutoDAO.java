package edu.fatec.poo.persistence.daoIntefaces;

import edu.fatec.poo.model.TipoProduto;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Interface de Acesso a Dados (DAO) especializada para a entidade {@link TipoProduto}.
 * Herda as operações padrão de CRUD da interface {@link BasicDAO} e adiciona
 * métodos de consulta específicos para categorização e classificação de produtos.
 *
 * @see BasicDAO
 * @see TipoProduto
 */
public interface TipoProdutoDAO extends BasicDAO<TipoProduto> {

    /**
     * Busca os tipos de produtos cuja descrição corresponda total ou parcialmente
     * ao termo informado.
     * <p>
     * Este método é comumente utilizado em campos de busca auto-complete, filtros de listagem
     * ou telas de cadastro para verificar categorias existentes (ex: pesquisar por "Eletrônicos").
     * </p>
     *
     * @param descricao O termo de busca ou descrição (completa ou parcial) do tipo de produto.
     * @return Um {@link Optional} contendo uma {@link List} com os {@link TipoProduto} que atendem
     * ao critério de busca, ou um {@link Optional#empty()} caso nenhuma categoria seja encontrada.
     * @throws SQLException           Se ocorrer um erro na execução da consulta SQL no banco de dados.
     * @throws ClassNotFoundException Se o driver JDBC do banco de dados não for localizado.
     */
    Optional<List<TipoProduto>> findByDescricao(String descricao) throws SQLException, ClassNotFoundException;

    /**
     * Recupera o ranking das 10 categorias (tipos de produtos) <b>mais vendidas</b> do sistema.
     * <p>
     * Este método realiza uma agregação baseada na quantidade total de itens de pedido vendidos
     * para cada categoria. É ideal para painéis de Business Intelligence (BI), dashboards gerenciais
     * e estratégias de marketing, destacando quais setores geram maior giro de estoque.
     * </p>
     *
     * @return Um {@link Optional} contendo uma {@link List} com até 10 objetos {@link TipoProduto},
     * ordenados de forma decrescente pelo volume de vendas, ou {@link Optional#empty()} se
     * nenhum produto tiver sido vendido no sistema até o momento.
     * @throws SQLException           Se ocorrer um erro na execução do agrupamento SQL ou leitura dos dados.
     * @throws ClassNotFoundException Se o driver JDBC do banco de dados não for localizado.
     */
    Optional<List<TipoProduto>> findByMostSoldTop10() throws SQLException, ClassNotFoundException;

    /**
     * Recupera o ranking das 10 categorias (tipos de produtos) <b>menos vendidas</b> ou estagnadas do sistema.
     * <p>
     * Este método analisa o histórico de movimentações para identificar as categorias com o menor
     * volume de saída. É uma ferramenta crítica para tomadas de decisão de inventário, planejamento
     * de promoções de queima de estoque ou revisão do catálogo de fornecedores.
     * </p>
     *
     * @return Um {@link Optional} contendo uma {@link List} com até 10 objetos {@link TipoProduto},
     * ordenados de forma crescente pelo volume de vendas (ou seja, do pior desempenho para o melhor),
     * ou {@link Optional#empty()} se não houver registros históricos de vendas para computar o ranking.
     * @throws SQLException           Se ocorrer um erro na execução da consulta ou na comunicação com o banco.
     * @throws ClassNotFoundException Se o driver JDBC do banco de dados não for localizado.
     */
    Optional<List<TipoProduto>> findByLeastSoldTop10() throws SQLException, ClassNotFoundException;
}