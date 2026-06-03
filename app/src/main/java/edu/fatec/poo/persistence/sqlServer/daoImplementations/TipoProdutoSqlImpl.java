package edu.fatec.poo.persistence.sqlServer.daoImplementations;

import edu.fatec.poo.model.StatusPedido;
import edu.fatec.poo.model.TipoProduto;
import edu.fatec.poo.persistence.connection.ADaoConnector;
import edu.fatec.poo.persistence.daoIntefaces.TipoProdutoDAO;
import edu.fatec.poo.persistence.sqlServer.ConfiguredSqlConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TipoProdutoSqlImpl implements TipoProdutoDAO {

    private final String tableName = "tipo_produto";
    private final String tableProdutoName = "produto";
    private final String tableItemPedidoName = "item_pedido";
    private final String tablePedidoName = "pedido";
    private final ADaoConnector connector;

    public TipoProdutoSqlImpl() {
        this.connector = new ConfiguredSqlConnector().getConector();
    }

    // --- Construtores de Query (SQL Builders) ---

    private String fullQueryAll() {
        return fullQuery().append(";").toString();
    }

    private String fullQueryById() {
        return fullQuery().append(" WHERE id = ?;").toString();
    }

    private String queryByDescricao() {
        return fullQuery().append(" WHERE descricao LIKE ?;").toString();
    }

    private StringBuilder fullQuery() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, descricao FROM ").append(tableName);
        return sql;
    }

    private String queryMostSoldTop10() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT TOP(10) ");
        sql.append("tipo.id AS id, ");
        sql.append("tipo.descricao AS descricao, ");
        sql.append("SUM(item_ped.quantidade) AS qtd_vendida ");
        sql.append("FROM ").append(tableName).append(" tipo ");
        sql.append("INNER JOIN ").append(tableProdutoName).append(" prod ");
        sql.append("   ON prod.tipo_produto = tipo.id ");
        sql.append("INNER JOIN ").append(tableItemPedidoName).append(" item_ped ");
        sql.append("   ON item_ped.produto = prod.id ");
        sql.append("INNER JOIN ").append(tablePedidoName).append(" ped ");
        sql.append("   ON ped.id = item_ped.pedido ");
        sql.append("WHERE ped.status LIKE '%").append(StatusPedido.COMPLETO.name()).append("%' ");
        sql.append("GROUP BY tipo.id, tipo.descricao ");
        sql.append("ORDER BY SUM(item_ped.quantidade) DESC;");
        return sql.toString();
    }

    private String queryLeastSoldTop10() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT TOP(10) ");
        sql.append("tipo.id AS id, ");
        sql.append("tipo.descricao AS descricao, ");
        sql.append("SUM(item_ped.quantidade) AS qtd_vendida ");
        sql.append("FROM ").append(tableName).append(" tipo ");
        sql.append("INNER JOIN ").append(tableProdutoName).append(" prod ");
        sql.append("   ON prod.tipo_produto = tipo.id ");
        sql.append("INNER JOIN ").append(tableItemPedidoName).append(" item_ped ");
        sql.append("   ON item_ped.produto = prod.id ");
        sql.append("INNER JOIN ").append(tablePedidoName).append(" ped ");
        sql.append("   ON ped.id = item_ped.pedido ");
        sql.append("WHERE ped.status LIKE '%").append(StatusPedido.COMPLETO.name()).append("%' ");
        sql.append("GROUP BY tipo.id, tipo.descricao ");
        sql.append("ORDER BY SUM(item_ped.quantidade);");
        return sql.toString();
    }

    // --- Mapeadores de ResultSet (Mappers) ---

    private TipoProduto rsToTipoProduto(ResultSet rs) throws SQLException {
        TipoProduto tipoProduto = new TipoProduto();
        tipoProduto.setId(UUID.fromString(rs.getString("id")));
        tipoProduto.setDescricao(rs.getString("descricao"));
        return tipoProduto;
    }

    // --- Operações de Banco de Dados (CRUD) ---

    @Override
    public Optional<TipoProduto> add(TipoProduto tipoProduto) throws SQLException, ClassNotFoundException {
        if (tipoProduto == null || tipoProduto.getId() == null) return Optional.empty();

        String sql = "INSERT INTO " + tableName + " (id, descricao) VALUES (?, ?);";
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, tipoProduto.getId().toString());
            ps.setString(2, tipoProduto.getDescricao());

            int colunasAfetadas = ps.executeUpdate();

            if (colunasAfetadas > 0) {
                return Optional.of(tipoProduto);
            } else {
                return Optional.empty();
            }
        }
    }

    @Override
    public Optional<TipoProduto> findById(UUID id) throws SQLException, ClassNotFoundException {
        if (id == null) return Optional.empty();

        String sql = fullQueryById();
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, id.toString());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(rsToTipoProduto(rs));
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public Optional<List<TipoProduto>> searchAll() throws SQLException, ClassNotFoundException {
        String sql = fullQueryAll();
        List<TipoProduto> produtos = new ArrayList<>();

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                produtos.add(rsToTipoProduto(rs));
            }
        }
        return Optional.of(produtos);
    }

    @Override
    public Optional<TipoProduto> update(TipoProduto tipoProduto) throws SQLException, ClassNotFoundException {
        if (tipoProduto == null || tipoProduto.getId() == null) return Optional.empty();

        String sql = "UPDATE " + tableName + " SET descricao = ? WHERE id = ?;";
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, tipoProduto.getDescricao());
            ps.setString(2, tipoProduto.getId().toString());

            int colunasAfetadas = ps.executeUpdate();

            if (colunasAfetadas > 0) {
                return Optional.of(tipoProduto);
            } else {
                return Optional.empty();
            }
        }
    }

    @Override
    public Optional<TipoProduto> delete(TipoProduto tipoProduto) throws SQLException, ClassNotFoundException {
        if (tipoProduto == null || tipoProduto.getId() == null) return Optional.empty();

        String sql = "DELETE FROM " + tableName + " WHERE id = ?;";
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, tipoProduto.getId().toString());

            int colunasAfetadas = ps.executeUpdate();

            if (colunasAfetadas > 0) {
                return Optional.of(tipoProduto);
            } else {
                return Optional.empty();
            }
        }
    }

    @Override
    public Optional<List<TipoProduto>> findByDescricao(String descricao) throws SQLException, ClassNotFoundException {
        if (descricao == null || descricao.isBlank()) return Optional.empty();

        String sql = queryByDescricao();
        List<TipoProduto> tipoProdutos = new ArrayList<>();

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "%" + descricao + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tipoProdutos.add(rsToTipoProduto(rs));
                }
            }
        }
        return Optional.of(tipoProdutos);
    }

    @Override
    public Optional<List<TipoProduto>> findByMostSoldTop10() throws SQLException, ClassNotFoundException {
        String sql = queryMostSoldTop10();
        List<TipoProduto> produtos = new ArrayList<>();

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                produtos.add(rsToTipoProduto(rs));
            }
        }
        return Optional.of(produtos);
    }

    @Override
    public Optional<List<TipoProduto>> findByLeastSoldTop10() throws SQLException, ClassNotFoundException {
        String sql = queryLeastSoldTop10();
        List<TipoProduto> produtos = new ArrayList<>();

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                produtos.add(rsToTipoProduto(rs));
            }
        }
        return Optional.of(produtos);
    }
}