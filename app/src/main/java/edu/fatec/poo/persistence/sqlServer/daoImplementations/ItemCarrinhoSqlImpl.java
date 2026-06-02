package edu.fatec.poo.persistence.sqlServer.daoImplementations;

import edu.fatec.poo.model.*;
import edu.fatec.poo.persistence.connection.ADaoConnector;
import edu.fatec.poo.persistence.daoIntefaces.ItemCarrinhoDAO;
import edu.fatec.poo.persistence.sqlServer.ConfiguredSqlConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ItemCarrinhoSqlImpl implements ItemCarrinhoDAO {

    private final String tableName = "item_carrinho";
    private final String tableNameCarrinho = "carrinho";
    private final String tableNameCliente = "cliente";
    private final String tableNameProduto = "produto";
    private final String tableNameTipoProduto = "tipo_produto";
    private final ADaoConnector connector;

    public ItemCarrinhoSqlImpl() {
        this.connector = new ConfiguredSqlConnector().getConector();
    }

    private String fullQueryAll() {
        return fullQuery().append(";").toString();
    }

    private String fullQueryById() {
        return fullQuery().append(" WHERE itc.id = ?;").toString();
    }

    private StringBuilder fullQuery() {
        StringBuilder sql = new StringBuilder();

        // 1. Projeção (SELECT e suas colunas)
        startSelect(sql);
        appendItemCarrinhoColumns(sql).append(", ");
        appendCarrinhoColumns(sql).append(", ");
        appendProdutoColumns(sql).append(", ");
        appendClienteColumns(sql); // O último campo não leva vírgula

        // 2. Origem (FROM)
        startFrom(sql);

        // 3. Relacionamentos (JOINS)
        joinCarrinho(sql);
        joinCliente(sql);
        joinProduto(sql);
        joinTipoProduto(sql);

        return sql;
    }

    private String queryByCarrinhoId() {
        StringBuilder sql = new StringBuilder();
        startSelect(sql);
        appendItemCarrinhoColumns(sql).append(", ");
        appendProdutoColumns(sql);

        startFrom(sql);
        joinProduto(sql);
        joinTipoProduto(sql);

        return sql.append(" WHERE itc.carrinho = ?;").toString();
    }

    private String queryByProdutoId() {
        StringBuilder sql = new StringBuilder();

        startSelect(sql);
        appendItemCarrinhoColumns(sql).append(", ");
        appendCarrinhoColumns(sql).append(", ");
        appendClienteColumns(sql);

        startFrom(sql);

        joinCarrinho(sql);
        joinCliente(sql);

        return sql.append(" WHERE itc.produto = ?;").toString();
    }

    private StringBuilder startSelect(StringBuilder sql) {
        return sql.append("SELECT ");
    }

    private StringBuilder startFrom(StringBuilder sql) {
        return sql.append(" FROM ").append(tableName).append(" itc ");
    }

    private StringBuilder appendItemCarrinhoColumns(StringBuilder sql) {
        return sql.append("itc.id AS id, itc.quantidade AS quantidade");
    }

    private StringBuilder appendCarrinhoColumns(StringBuilder sql) {
        return sql.append("carr.id AS carrinho_id");
    }

    private StringBuilder appendProdutoColumns(StringBuilder sql) {
        sql.append("prod.id AS produto_id, prod.nome AS produto_nome, prod.preco AS produto_preco, ");
        sql.append("tipo.id AS tipo_id, tipo.descricao AS tipo_descricao");
        return sql;
    }

    private StringBuilder appendClienteColumns(StringBuilder sql) {
        sql.append("cli.id AS cliente_id, cli.nome AS cliente_nome, cli.email AS cliente_email, ");
        sql.append("cli.senha AS cliente_senha, cli.endereco_logradouro AS cliente_logradouro, ");
        sql.append("cli.endereco_cep AS cliente_cep, cli.endereco_num AS cliente_numero, ");
        sql.append("cli.endereco_complemento AS cliente_complemento");
        return sql;
    }

    private StringBuilder joinCarrinho(StringBuilder sql) {
        return sql.append("INNER JOIN ").append(tableNameCarrinho).append(" carr ON itc.carrinho = carr.id ");
    }

    private StringBuilder joinCliente(StringBuilder sql) {
        return sql.append("INNER JOIN ").append(tableNameCliente).append(" cli ON carr.cliente = cli.id ");
    }

    private StringBuilder joinProduto(StringBuilder sql) {
        return sql.append("INNER JOIN ").append(tableNameProduto).append(" prod ON itc.produto = prod.id ");
    }

    private StringBuilder joinTipoProduto(StringBuilder sql) {
        return sql.append("INNER JOIN ").append(tableNameTipoProduto).append(" tipo ON prod.tipo_produto = tipo.id ");
    }

    private ItemCarrinho rsToItemCarrinhoFull(ResultSet rs) throws SQLException {
        ItemCarrinho itemCarrinho = mapItemCarrinho(rs);

        itemCarrinho.setCarrinho(mapCarrinho(rs));
        itemCarrinho.setProduto(mapProduto(rs));

        return itemCarrinho;
    }

    private ItemCarrinho rsToItemCarrinhoWithCarrinho(ResultSet rs, Carrinho carrinho) throws SQLException {
        ItemCarrinho itemCarrinho = mapItemCarrinho(rs);

        itemCarrinho.setCarrinho(carrinho);
        itemCarrinho.setProduto(mapProduto(rs));

        return itemCarrinho;
    }

    private ItemCarrinho rsToItemCarrinhoWithProduto(ResultSet rs, Produto produto) throws SQLException {
        ItemCarrinho itemCarrinho = mapItemCarrinho(rs);

        itemCarrinho.setCarrinho(mapCarrinho(rs));
        itemCarrinho.setProduto(produto);

        return itemCarrinho;
    }

    private ItemCarrinho mapItemCarrinho(ResultSet rs) throws SQLException {
        ItemCarrinho itemCarrinho = new ItemCarrinho();
        itemCarrinho.setId(UUID.fromString(rs.getString("id")));
        itemCarrinho.setQuantidade(rs.getInt("quantidade"));
        return itemCarrinho;
    }

    private Carrinho mapCarrinho(ResultSet rs) throws SQLException {
        Carrinho carrinho = new Carrinho();
        carrinho.setId(UUID.fromString(rs.getString("carrinho_id")));

        // O carrinho precisa do cliente populado
        carrinho.setCliente(mapCliente(rs));

        return carrinho;
    }

    private Cliente mapCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(UUID.fromString(rs.getString("cliente_id")));
        cliente.setNome(rs.getString("cliente_nome"));
        cliente.setEmail(rs.getString("cliente_email"));
        cliente.setSenha(rs.getString("cliente_senha"));
        cliente.setEnderecoLogradouro(rs.getString("cliente_logradouro"));
        cliente.setEnderecoCep(rs.getString("cliente_cep"));
        cliente.setEnderecoNum(rs.getInt("cliente_numero"));
        cliente.setEnderecoComplemento(rs.getString("cliente_complemento"));
        return cliente;
    }

    private Produto mapProduto(ResultSet rs) throws SQLException {
        Produto produto = new Produto();
        produto.setId(UUID.fromString(rs.getString("produto_id")));
        produto.setNome(rs.getString("produto_nome"));
        produto.setPreco(rs.getDouble("produto_preco"));

        // O produto precisa do seu tipo populado
        produto.setTipoProduto(mapTipoProduto(rs));

        return produto;
    }

    private TipoProduto mapTipoProduto(ResultSet rs) throws SQLException {
        TipoProduto tipoProduto = new TipoProduto();
        tipoProduto.setId(UUID.fromString(rs.getString("tipo_id")));
        tipoProduto.setDescricao(rs.getString("tipo_descricao"));
        return tipoProduto;
    }


    @Override
    public Optional<ItemCarrinho> add(ItemCarrinho itemCarrinho) throws SQLException, ClassNotFoundException {
        if (itemCarrinho == null || itemCarrinho.getId() == null) return Optional.empty();
        if (itemCarrinho.getCarrinho() == null || itemCarrinho.getCarrinho().getId() == null) return Optional.empty();
        if (itemCarrinho.getProduto() == null || itemCarrinho.getProduto().getId() == null) return Optional.empty();

        String sql = "INSERT INTO " + tableName + " (id, quantidade, carrinho, produto) VALUES (?, ?, ?, ?);";
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, itemCarrinho.getId().toString());
            ps.setInt(2, itemCarrinho.getQuantidade());
            ps.setString(3, itemCarrinho.getCarrinho().getId().toString());
            ps.setString(4, itemCarrinho.getProduto().getId().toString());

            int colunasAfetadas = ps.executeUpdate();
            return colunasAfetadas > 0 ? Optional.of(itemCarrinho) : Optional.empty();
        }
    }

    @Override
    public Optional<ItemCarrinho> findById(UUID id) throws SQLException, ClassNotFoundException {
        if (id == null) return Optional.empty();

        String sql = fullQueryById();

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, id.toString());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ItemCarrinho itemCarrinho = rsToItemCarrinhoFull(rs);
                    return Optional.of(itemCarrinho);
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public Optional<List<ItemCarrinho>> searchAll() throws SQLException, ClassNotFoundException {
        String sql = fullQueryAll();
        List<ItemCarrinho> itemCarrinhos = new ArrayList<>();

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                itemCarrinhos.add(rsToItemCarrinhoFull(rs));
            }
        }
        return Optional.of(itemCarrinhos);
    }

    @Override
    public Optional<ItemCarrinho> update(ItemCarrinho itemCarrinho) throws SQLException, ClassNotFoundException {
        if (itemCarrinho == null || itemCarrinho.getId() == null) return Optional.empty();
        if (itemCarrinho.getCarrinho() == null || itemCarrinho.getCarrinho().getId() == null)
            return Optional.empty();
        if (itemCarrinho.getProduto() == null || itemCarrinho.getProduto().getId() == null)
            return Optional.empty();

        String sql = "UPDATE " + tableName + " " +
                "SET quantidade = ?, carrinho = ?, produto = ? " +
                "WHERE id = ?;";
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, itemCarrinho.getQuantidade());
            ps.setString(2, itemCarrinho.getCarrinho().getId().toString());
            ps.setString(3, itemCarrinho.getProduto().getId().toString());
            ps.setString(4, itemCarrinho.getId().toString());

            int colunasAfetadas = ps.executeUpdate();
            return colunasAfetadas > 0 ? Optional.of(itemCarrinho) : Optional.empty();
        }
    }

    @Override
    public Optional<ItemCarrinho> delete(ItemCarrinho itemCarrinho) throws SQLException, ClassNotFoundException {
        if (itemCarrinho == null || itemCarrinho.getId() == null) return Optional.empty();

        String sql = "DELETE FROM " + tableName + " WHERE id = ?;";

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, itemCarrinho.getId().toString());

            int colunasAfetadas = ps.executeUpdate();
            return colunasAfetadas > 0 ? Optional.of(itemCarrinho) : Optional.empty();
        }
    }

    @Override
    public Optional<List<ItemCarrinho>> findByCarrinho(Carrinho carrinho) throws SQLException, ClassNotFoundException {
        if (carrinho == null || carrinho.getId() == null) return Optional.empty();

        String sql = queryByCarrinhoId();
        List<ItemCarrinho> itemCarrinhos = new ArrayList<>();

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, carrinho.getId().toString());

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    itemCarrinhos.add(rsToItemCarrinhoWithCarrinho(rs, carrinho));
                }
            }
        }
        return Optional.of(itemCarrinhos);
    }

    @Override
    public Optional<List<ItemCarrinho>> findByProduto(Produto produto) throws SQLException, ClassNotFoundException {
        if (produto == null || produto.getId() == null) return Optional.empty();

        String sql = queryByProdutoId();
        List<ItemCarrinho> itemCarrinhos = new ArrayList<>();

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, produto.getId().toString());

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    itemCarrinhos.add(rsToItemCarrinhoWithProduto(rs, produto));
                }
            }
        }
        return Optional.of(itemCarrinhos);
    }
}
