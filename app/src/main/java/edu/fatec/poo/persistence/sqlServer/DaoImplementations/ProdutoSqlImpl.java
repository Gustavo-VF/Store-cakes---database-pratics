package edu.fatec.poo.persistence.sqlServer.DaoImplementations;

import edu.fatec.poo.model.Produto;
import edu.fatec.poo.model.TipoProduto;
import edu.fatec.poo.persistence.DaoIntefaces.ProdutoDAO;
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

public class ProdutoSqlImpl implements ProdutoDAO {
    private final String tableName = "produto";
    private final String innerTable1 = "tipo_produto";
    private final ADaoConnector connector;

    public ProdutoSqlImpl() {
        this.connector = new ConfiguredSqlConnector().getConector();
    }

    @Override
    public Optional<Produto> add(Produto produto) throws SQLException, ClassNotFoundException {
        if (produto == null || produto.getId() == null) return Optional.empty();
        if (produto.getTipoProduto() == null || produto.getTipoProduto().getId() == null) return Optional.empty();

        String sql = "INSERT INTO " + tableName + " (id, nome, preco, tipo_produto) VALUES (?, ?, ?, ?);";
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, produto.getId().toString());
            ps.setString(2, produto.getNome());
            ps.setDouble(3, produto.getPreco());
            ps.setString(4, produto.getTipoProduto().getId().toString());

            int colunasAfetadas = ps.executeUpdate();
            return colunasAfetadas > 0 ? Optional.of(produto) : Optional.empty();
        }
    }

    @Override
    public Optional<Produto> findById(UUID id) throws SQLException, ClassNotFoundException {
        if (id == null) return Optional.empty();

        String sql = "SELECT " +
                "p.id, p.nome, p.preco, p.tipo_produto, " +
                "t.descricao AS tipo_descricao " +
                "FROM " + tableName + " p " +
                "INNER JOIN " + innerTable1 + " t " +
                "ON p.tipo_produto = t.id " +
                "WHERE p.id = ?;";

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, id.toString());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Produto produto = new Produto();
                    produto.setId(UUID.fromString(rs.getString("id")));
                    produto.setNome(rs.getString("nome"));
                    produto.setPreco(rs.getDouble("preco"));

                    TipoProduto tipoProduto = new TipoProduto();
                    tipoProduto.setId(UUID.fromString(rs.getString("tipo_produto")));
                    tipoProduto.setDescricao(rs.getString("tipo_descricao"));
                    produto.setTipoProduto(tipoProduto);

                    return Optional.of(produto);
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public Optional<List<Produto>> searchAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT " +
                "p.id, p.nome, p.preco, p.tipo_produto, " +
                "t.descricao AS tipo_descricao " +
                "FROM " + tableName + " p " +
                "INNER JOIN " + innerTable1 + " t " +
                "ON p.tipo_produto = t.id;";
        List<Produto> produtos = new ArrayList<>();

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(UUID.fromString(rs.getString("id")));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getDouble("preco"));

                TipoProduto tipoProduto = new TipoProduto();
                tipoProduto.setId(UUID.fromString(rs.getString("tipo_produto")));
                tipoProduto.setDescricao(rs.getString("tipo_descricao"));
                produto.setTipoProduto(tipoProduto);

                produtos.add(produto);
            }
        }
        return Optional.of(produtos);
    }

    @Override
    public Optional<Produto> update(Produto produto) throws SQLException, ClassNotFoundException {
        if (produto == null || produto.getId() == null) return Optional.empty();
        if (produto.getTipoProduto() == null || produto.getTipoProduto().getId() == null) return Optional.empty();

        String sql = "UPDATE " + tableName + " " +
                "SET nome = ?, preco = ?, tipo_produto = ? " +
                "WHERE id = ?;";
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco());
            ps.setString(3, produto.getTipoProduto().getId().toString());
            ps.setString(4, produto.getId().toString());

            int colunasAfetadas = ps.executeUpdate();
            return colunasAfetadas > 0 ? Optional.of(produto) : Optional.empty();
        }
    }

    @Override
    public Optional<Produto> delete(Produto produto) throws SQLException, ClassNotFoundException {
        if (produto == null || produto.getId() == null) return Optional.empty();

        String sql = "DELETE FROM " + tableName + " WHERE id = ?;";

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, produto.getId().toString());

            int colunasAfetadas = ps.executeUpdate();
            return colunasAfetadas > 0 ? Optional.of(produto) : Optional.empty();
        }
    }
}
