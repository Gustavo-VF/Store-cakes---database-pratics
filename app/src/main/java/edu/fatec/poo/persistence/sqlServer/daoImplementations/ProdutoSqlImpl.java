package edu.fatec.poo.persistence.sqlServer.daoImplementations;

import edu.fatec.poo.model.Cliente;
import edu.fatec.poo.model.Produto;
import edu.fatec.poo.model.TipoProduto;
import edu.fatec.poo.persistence.connection.ADaoConnector;
import edu.fatec.poo.persistence.daoIntefaces.ProdutoDAO;
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
    private final String tableProdutoName = "tipo_produto";
    private final String tableNameCliente = "cliente";
    private final ADaoConnector connector;

    public ProdutoSqlImpl() {
        this.connector = new ConfiguredSqlConnector().getConector();
    }

    private String fullQueryAll() {
        return fullQuery().append(";").toString();
    }

    private String fullQueryById() {
        return fullQuery().append(" WHERE prod.id = ?;").toString();
    }

    private String fullQueryByName() {
        return fullQuery().append(" WHERE prod.nome LIKE ?;").toString();
    }

    private String fullQueryByTipo() {
        return fullQuery().append(" WHERE prod.tipo_produto = ?;").toString();
    }

    private String fullQueryByVendedor() {
        return fullQuery().append(" WHERE prod.vendedor = ?;").toString();
    }

    private String fullQueryByPreco() {
        return fullQuery().append(" WHERE prod.preco >= ? AND prod.preco <= ?;").toString();
    }

    private StringBuilder fullQuery() {
        StringBuilder sql = new StringBuilder();

        startSelect(sql);
        appendProdutoColumns(sql).append(", ");
        appendTipoProdutoColumns(sql).append(", ");
        appendVendedorColumns(sql);

        startFrom(sql);
        joinTipoProduto(sql);
        joinVendedor(sql);

        return sql;
    }

    private StringBuilder startSelect(StringBuilder sql) {
        return sql.append("SELECT ");
    }

    private StringBuilder startFrom(StringBuilder sql) {
        return sql.append(" FROM ").append(tableName).append(" prod ");
    }

    private StringBuilder appendProdutoColumns(StringBuilder sql) {
        return sql.append("prod.id, prod.nome, prod.preco, prod.tipo_produto");
    }

    private StringBuilder appendVendedorColumns(StringBuilder sql) {
        sql.append("vend.id AS vendedor_id, vend.nome AS vendedor_nome, vend.email AS vendedor_email, ");
        sql.append("vend.senha AS vendedor_senha, vend.endereco_logradouro AS vendedor_logradouro, ");
        sql.append("vend.endereco_cep AS vendedor_cep, vend.endereco_num AS vendedor_numero, ");
        sql.append("vend.endereco_complemento AS vendedor_complemento");
        return sql;
    }

    private StringBuilder joinVendedor(StringBuilder sql) {
        return sql.append("INNER JOIN ").append(tableNameCliente).append(" vend ON prod.vendedor = vend.id ");
    }

    private StringBuilder appendTipoProdutoColumns(StringBuilder sql) {
        return sql.append("tipo.descricao AS tipo_descricao");
    }

    private StringBuilder joinTipoProduto(StringBuilder sql) {
        return sql.append("INNER JOIN ").append(tableProdutoName).append(" tipo ON prod.tipo_produto = tipo.id ");
    }

    private Produto rsToProdutoFull(ResultSet rs) throws SQLException {
        Produto produto = mapProduto(rs);
        produto.setTipoProduto(mapTipoProduto(rs));
        produto.setVendedor(mapVendedor(rs));
        return produto;
    }

    private Produto mapProduto(ResultSet rs) throws SQLException {
        Produto produto = new Produto();
        produto.setId(UUID.fromString(rs.getString("id")));
        produto.setNome(rs.getString("nome"));
        produto.setPreco(rs.getDouble("preco"));
        return produto;
    }

    private Cliente mapVendedor(ResultSet rs) throws SQLException {
        Cliente vendedor = new Cliente();
        vendedor.setId(UUID.fromString(rs.getString("vendedor_id")));
        vendedor.setNome(rs.getString("vendedor_nome"));
        vendedor.setEmail(rs.getString("vendedor_email"));
        vendedor.setSenha(rs.getString("vendedor_senha"));
        vendedor.setEnderecoLogradouro(rs.getString("vendedor_logradouro"));
        vendedor.setEnderecoCep(rs.getString("vendedor_cep"));
        vendedor.setEnderecoNum(rs.getInt("vendedor_numero"));
        vendedor.setEnderecoComplemento(rs.getString("vendedor_complemento"));
        return vendedor;
    }

    private TipoProduto mapTipoProduto(ResultSet rs) throws SQLException {
        TipoProduto tipoProduto = new TipoProduto();
        tipoProduto.setId(UUID.fromString(rs.getString("tipo_produto")));
        tipoProduto.setDescricao(rs.getString("tipo_descricao"));
        return tipoProduto;
    }

    @Override
    public Optional<Produto> add(Produto produto) throws SQLException, ClassNotFoundException {
        if (produto == null || produto.getId() == null) return Optional.empty();
        if (produto.getTipoProduto() == null || produto.getTipoProduto().getId() == null) return Optional.empty();
        if (produto.getVendedor() == null || produto.getVendedor().getId() == null) return Optional.empty();

        String sql = "INSERT INTO " + tableName + " (id, nome, preco, tipo_produto, vendedor) VALUES (?, ?, ?, ?);";
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, produto.getId().toString());
            ps.setString(2, produto.getNome());
            ps.setDouble(3, produto.getPreco());
            ps.setString(4, produto.getTipoProduto().getId().toString());
            ps.setString(5, produto.getVendedor().getId().toString());

            int colunasAfetadas = ps.executeUpdate();

            if (colunasAfetadas > 0) {
                return Optional.of(produto);
            } else {
                return Optional.empty();
            }
        }
    }

    @Override
    public Optional<Produto> findById(UUID id) throws SQLException, ClassNotFoundException {
        if (id == null) return Optional.empty();

        String sql = fullQueryById();
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, id.toString());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(rsToProdutoFull(rs));
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public Optional<List<Produto>> searchAll() throws SQLException, ClassNotFoundException {
        String sql = fullQueryAll();
        List<Produto> produtos = new ArrayList<>();

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                produtos.add(rsToProdutoFull(rs));
            }
        }
        return Optional.of(produtos);
    }

    @Override
    public Optional<Produto> update(Produto produto) throws SQLException, ClassNotFoundException {
        if (produto == null || produto.getId() == null) return Optional.empty();
        if (produto.getTipoProduto() == null || produto.getTipoProduto().getId() == null) return Optional.empty();
        if (produto.getVendedor() == null || produto.getVendedor().getId() == null) return Optional.empty();

        String sql = "UPDATE " + tableName + " SET nome = ?, preco = ?, tipo_produto = ? ,vendedor = ? WHERE id = ?;";
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco());
            ps.setString(3, produto.getTipoProduto().getId().toString());
            ps.setString(4, produto.getVendedor().getId().toString());
            ps.setString(5, produto.getId().toString());

            int colunasAfetadas = ps.executeUpdate();

            if (colunasAfetadas > 0) {
                return Optional.of(produto);
            } else {
                return Optional.empty();
            }
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

            if (colunasAfetadas > 0) {
                return Optional.of(produto);
            } else {
                return Optional.empty();
            }
        }
    }


    @Override
    public Optional<List<Produto>> findByName(String name) throws SQLException, ClassNotFoundException {
        if (name == null || name.isBlank()) return Optional.empty();

        List<Produto> produtos = new ArrayList<>();

        String sql = fullQueryByName();
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    produtos.add(rsToProdutoFull(rs));
                }
            }
        }
        return Optional.of(produtos);
    }


    @Override
    public Optional<List<Produto>> findByTipoProduto(TipoProduto tipoProduto) throws SQLException, ClassNotFoundException {
        if (tipoProduto == null || tipoProduto.getId() == null) return Optional.empty();

        List<Produto> produtos = new ArrayList<>();

        String sql = fullQueryByTipo();
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, tipoProduto.getId().toString());

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    produtos.add(rsToProdutoFull(rs));
                }
            }
        }
        return Optional.of(produtos);
    }

    @Override
    public Optional<List<Produto>> findByVendedor(Cliente vendedor) throws SQLException, ClassNotFoundException {
        if (vendedor == null || vendedor.getId() == null) return Optional.empty();

        List<Produto> produtos = new ArrayList<>();

        String sql = fullQueryByVendedor();
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, vendedor.getId().toString());

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    produtos.add(rsToProdutoFull(rs));
                }
            }
        }
        return Optional.of(produtos);
    }


    @Override
    public Optional<List<Produto>> findByPreco(Double precoMin, Double precoMax) throws SQLException, ClassNotFoundException {
        if (precoMin == null || precoMin < 0 || precoMax == null || precoMax < 0 || precoMin > precoMax)
            return Optional.empty();

        List<Produto> produtos = new ArrayList<>();

        String sql = fullQueryByPreco();
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDouble(1, precoMin);
            ps.setDouble(2, precoMax);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    produtos.add(rsToProdutoFull(rs));
                }
            }
        }
        return Optional.of(produtos);
    }
}