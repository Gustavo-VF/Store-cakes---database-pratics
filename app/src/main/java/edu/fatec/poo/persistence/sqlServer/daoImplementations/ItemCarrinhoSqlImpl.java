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

    private String fullQuerryAll() {
        return fullQuerry().append(";").toString();
    }

    private String fullQuerryById() {
        return fullQuerry().append(" WHERE itc.id = ?;").toString();
    }

    private StringBuilder fullQuerry() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT itc.id AS id, itc.quantidade AS quantidade, ");
        sql.append("carr.id AS carrinho_id, ");
        sql.append("prod.id AS produto_id, prod.nome AS produto_nome, prod.preco AS produto_preco, ");
        sql.append("tipo.id AS tipo_id, tipo.descricao AS tipo_descricao, ped.id AS pedido_id, ");
        sql.append("cli.id AS cliente_id, cli.nome AS cliente_nome, cli.email AS cliente_email, cli.senha AS cliente_senha, ");
        sql.append("cli.endereco_logradouro AS cliente_logradouro, cli.endereco_cep AS cliente_cep, ");
        sql.append("cli.endereco_num AS cliente_numero, cli.endereco_complemento AS cliente_complemento ");
        sql.append("FROM ").append(tableName).append(" itc ");
        sql.append("INNER JOIN ").append(tableNameCarrinho).append(" carr ON itc.carrinho = carr.id ");
        sql.append("INNER JOIN ").append(tableNameCliente).append(" cli ON carr.cliente = cli.id ");
        sql.append("INNER JOIN ").append(tableNameProduto).append(" prod ON itc.produto = prod.id ");
        sql.append("INNER JOIN ").append(tableNameTipoProduto).append(" tipo ON prod.tipo_produto = tipo.id ");
        return sql;
    }

    private ItemCarrinho rsToItemCarrinhoFull(ResultSet rs) throws SQLException {
        // montar Carrinho
        Carrinho carrinho = new Carrinho();
        carrinho.setId(UUID.fromString(rs.getString("carrinho_id")));

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
        carrinho.setCliente(cliente);

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

        // Montar o ItemCarrinho
        ItemCarrinho itemCarrinho = new ItemCarrinho();
        itemCarrinho.setCarrinho(carrinho);
        itemCarrinho.setProduto(produto);
        itemCarrinho.setQuantidade(rs.getInt("quantidade"));
        itemCarrinho.setId(UUID.fromString(rs.getString("id")));

        return itemCarrinho;
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

        String sql = fullQuerryById();

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
        String sql = fullQuerryAll();
        List<ItemCarrinho> itemCarrinhos = new ArrayList<>();

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ItemCarrinho itemCarrinho = new ItemCarrinho();
                itemCarrinhos.add(itemCarrinho);
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

}
