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
}