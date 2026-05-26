package edu.fatec.poo.persistence.sqlServer.DaoImplementations;

import edu.fatec.poo.model.TipoProduto;
import edu.fatec.poo.persistence.DaoIntefaces.TipoProdutoDAO;
import edu.fatec.poo.persistence.connection.ADaoConnector;
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
    private final ADaoConnector connector;

    public TipoProdutoSqlImpl() {
        this.connector = new ConfiguredSqlConnector().getConector();
    }

    @Override
    public Optional<TipoProduto> add(TipoProduto tipoProduto) throws SQLException, ClassNotFoundException {
        if (tipoProduto == null || tipoProduto.getId() == null) return Optional.empty();


        String sql = "INSERT INTO " + tableName + " (id, descricao) VALUES (?, ?);";
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, tipoProduto.getId().toString());
            ps.setString(2, tipoProduto.getDescricao());

            int colunasAfetadas = ps.executeUpdate();
            return colunasAfetadas > 0 ? Optional.of(tipoProduto) : Optional.empty();
        }
    }

    @Override
    public Optional<TipoProduto> findById(UUID id) throws SQLException, ClassNotFoundException {
        if (id == null) return Optional.empty();

        String sql = "SELECT id,descricao FROM " + tableName + " WHERE id = ?;";

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, id.toString());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    TipoProduto tipoProduto = new TipoProduto();
                    tipoProduto.setId(UUID.fromString(rs.getString("id")));
                    tipoProduto.setDescricao(rs.getString("descricao"));
                    return Optional.of(tipoProduto);
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public Optional<List<TipoProduto>> searchAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT id, descricao FROM " + tableName + ";";
        List<TipoProduto> produtos = new ArrayList<>();

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TipoProduto tipoProduto = new TipoProduto();
                tipoProduto.setId(UUID.fromString(rs.getString("id")));
                tipoProduto.setDescricao(rs.getString("descricao"));
                produtos.add(tipoProduto);
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
            return colunasAfetadas > 0 ? Optional.of(tipoProduto) : Optional.empty();
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
            return colunasAfetadas > 0 ? Optional.of(tipoProduto) : Optional.empty();
        }
    }
}
