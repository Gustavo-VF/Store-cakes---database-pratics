package edu.fatec.poo.persistence.sqlServer.DaoImplementations;

import edu.fatec.poo.model.*;
import edu.fatec.poo.persistence.DaoIntefaces.ItemPedidoDAO;
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

public class ItemPedidoSqlImpl implements ItemPedidoDAO {

    private final String tableName = "item_pedido";
    private final String tableNamePedido = "pedido";
    private final String tableNameCliente = "cliente";
    private final String tableNameProduto = "produto";
    private final String tableNameTipoProduto = "tipo_produto";
    private final ADaoConnector connector;

    public ItemPedidoSqlImpl() {
        this.connector = new ConfiguredSqlConnector().getConector();
    }

    @Override
    public Optional<ItemPedido> add(ItemPedido itemPedido) throws SQLException, ClassNotFoundException {
        if (itemPedido == null || itemPedido.getId() == null) return Optional.empty();
        if (itemPedido.getProduto() == null || itemPedido.getProduto().getId() == null) return Optional.empty();
        if (itemPedido.getPedido() == null || itemPedido.getPedido().getId() == null) return Optional.empty();

        String sql = "INSERT INTO " + tableName + " " +
                "(id, quantidade, preco_unitario, pedido, produto) " +
                "VALUES (?, ?, ?, ?, ?);";
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, itemPedido.getId().toString());
            ps.setInt(2, itemPedido.getQuantidade());
            ps.setDouble(3, itemPedido.getPrecoUnitario());
            ps.setString(4, itemPedido.getPedido().getId().toString());
            ps.setString(5, itemPedido.getProduto().getId().toString());

            int colunasAfetadas = ps.executeUpdate();
            return colunasAfetadas > 0 ? Optional.of(itemPedido) : Optional.empty();
        }
    }

    @Override
    public Optional<ItemPedido> findById(UUID id) throws SQLException, ClassNotFoundException {
        if (id == null) return Optional.empty();

        String sql = fullQuerryById();

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, id.toString());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ItemPedido itemPedido = rsToItemPedidoFull(rs);
                    return Optional.of(itemPedido);
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public Optional<List<ItemPedido>> searchAll() throws SQLException, ClassNotFoundException {

        List<ItemPedido> produtos = new ArrayList<>();
        String sql = fullQuerryAll();

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ItemPedido itemPedido = rsToItemPedidoFull(rs);
                produtos.add(itemPedido);
            }
        }
        return Optional.of(produtos);
    }


    @Override
    public Optional<ItemPedido> update(ItemPedido itemPedido) throws SQLException, ClassNotFoundException {
        if (itemPedido == null || itemPedido.getId() == null) return Optional.empty();
        if (itemPedido.getPedido() == null || itemPedido.getPedido().getId() == null) return Optional.empty();
        if (itemPedido.getProduto() == null || itemPedido.getProduto().getId() == null) return Optional.empty();

        String sql = "UPDATE " + tableName + " " +
                "SET quantidade = ?, preco_unitario = ?, pedido = ?, produto = ? " +
                "WHERE id = ?;";
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, itemPedido.getQuantidade());
            ps.setDouble(2, itemPedido.getPrecoUnitario());
            ps.setString(3, itemPedido.getPedido().getId().toString());
            ps.setString(4, itemPedido.getProduto().getId().toString());
            ps.setString(5, itemPedido.getId().toString());

            int colunasAfetadas = ps.executeUpdate();
            return colunasAfetadas > 0 ? Optional.of(itemPedido) : Optional.empty();
        }
    }

    @Override
    public Optional<ItemPedido> delete(ItemPedido itemPedido) throws SQLException, ClassNotFoundException {
        if (itemPedido == null || itemPedido.getId() == null) return Optional.empty();

        String sql = "DELETE FROM " + tableName + " WHERE id = ?;";

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, itemPedido.getId().toString());

            int colunasAfetadas = ps.executeUpdate();
            return colunasAfetadas > 0 ? Optional.of(itemPedido) : Optional.empty();
        }
    }

    private ItemPedido rsToItemPedidoFull(ResultSet rs) throws SQLException {
        // Montar Pedido
        Pedido pedido = new Pedido();
        pedido.setId(UUID.fromString(rs.getString("pedido_id")));
        pedido.setPrecoTotal(rs.getDouble("pedido_preco_total"));
        pedido.setData(rs.getDate("pedido_data").toLocalDate());
        pedido.setStatus(StatusPedido.valueOf(rs.getString("pedido_status")));

        // Montar Cliente do Pedido
        Cliente cliente = new Cliente();
        cliente.setId(UUID.fromString(rs.getString("cliente_id")));
        cliente.setNome(rs.getString("cliente_nome"));
        cliente.setEmail(rs.getString("cliente_email"));
        cliente.setSenha(rs.getString("cliente_senha"));
        cliente.setEnderecoLogradouro(rs.getString("cliente_logradouro"));
        cliente.setEnderecoCep(rs.getString("cliente_cep"));
        cliente.setEnderecoNum(rs.getInt("cliente_numero"));
        cliente.setEnderecoComplemento(rs.getString("cliente_complemento"));
        pedido.setCliente(cliente);

        // Montar Produto
        Produto produto = new Produto();
        produto.setId(UUID.fromString(rs.getString("produto_id")));
        produto.setNome(rs.getString("produto_nome"));
        produto.setPreco(rs.getDouble("produto_preco"));

        // Montar Tipo do Produto
        TipoProduto tipoProduto = new TipoProduto();
        tipoProduto.setId(UUID.fromString(rs.getString("tipo_id")));
        tipoProduto.setDescricao(rs.getString("tipo_descricao"));
        produto.setTipoProduto(tipoProduto);

        // Montar o ItemPedido
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(UUID.fromString(rs.getString("id")));
        itemPedido.setQuantidade(rs.getInt("quantidade"));
        itemPedido.setPrecoUnitario(rs.getDouble("preco_unitario"));
        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);

        return itemPedido;
    }

    private String fullQuerryAll() {
        return fullQuerry().append(";").toString();
    }

    private String fullQuerryById() {
        return fullQuerry().append(" WHERE itp.id = ?;").toString();
    }

    private StringBuilder fullQuerry() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT itp.id, itp.quantidade, itp.preco_unitario, ");
        sql.append("prod.id AS produto_id, prod.nome AS produto_nome, prod.preco AS produto_preco, ");
        sql.append("tipo.id AS tipo_id, tipo.descricao AS tipo_descricao, ped.id AS pedido_id, ");
        sql.append("ped.preco_total AS pedido_preco_total, ped.data AS pedido_data, ped.status AS pedido_status, ");
        sql.append("cli.id AS cliente_id, cli.nome AS cliente_nome, cli.email AS cliente_email, cli.senha AS cliente_senha, ");
        sql.append("cli.endereco_logradouro AS cliente_logradouro, cli.endereco_cep AS cliente_cep, ");
        sql.append("cli.endereco_num AS cliente_numero, cli.endereco_complemento AS cliente_complemento ");
        sql.append("FROM ").append(tableName).append(" itp ");
        sql.append("INNER JOIN ").append(tableNameProduto).append(" prod ON itp.produto = prod.id ");
        sql.append("INNER JOIN ").append(tableNameTipoProduto).append(" tipo ON prod.tipo_produto = tipo.id ");
        sql.append("INNER JOIN ").append(tableNamePedido).append(" ped ON itp.pedido = ped.id ");
        sql.append("INNER JOIN ").append(tableNameCliente).append(" cli ON ped.cliente = cli.id ");
        return sql;
    }
}
