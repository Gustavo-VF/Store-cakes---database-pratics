package edu.fatec.poo.persistence.sqlServer.daoImplementations;

import edu.fatec.poo.model.Cliente;
import edu.fatec.poo.model.Pedido;
import edu.fatec.poo.model.StatusPedido;
import edu.fatec.poo.persistence.connection.ADaoConnector;
import edu.fatec.poo.persistence.daoIntefaces.PedidoDAO;
import edu.fatec.poo.persistence.sqlServer.ConfiguredSqlConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PedidoSqlImpl implements PedidoDAO {

    private final String tableName = "pedido";
    private final String tableNameCliente = "cliente";
    private final String tableNameItemPedido = "item_pedido"; // Nome padrão para os itens do pedido
    private final String tableNameProduto = "produto";       // Tabela de onde vem o preço atual
    private final ADaoConnector connector;

    public PedidoSqlImpl() {
        this.connector = new ConfiguredSqlConnector().getConector();
    }

    private String fullQueryAll() {
        return fullQuery().append(";").toString();
    }

    private String fullQueryById() {
        return fullQuery().append(" WHERE ped.id = ?;").toString();
    }

    private StringBuilder fullQuery() {
        StringBuilder sql = new StringBuilder();

        startSelect(sql);
        appendPedidoColumns(sql).append(", ");
        appendSumPrecoTotal(sql).append(", ");
        appendClienteColumns(sql);

        startFrom(sql);
        joinCliente(sql);

        return sql;
    }

    private String queryByCliente() {
        StringBuilder sql = new StringBuilder();

        startSelect(sql);
        appendPedidoColumns(sql).append(", ");
        appendSumPrecoTotal(sql);

        startFrom(sql);

        return sql.append(" WHERE ped.cliente = ?;").toString();
    }

    private StringBuilder startSelect(StringBuilder sql) {
        return sql.append("SELECT ");
    }

    private StringBuilder startFrom(StringBuilder sql) {
        return sql.append(" FROM ").append(tableName).append(" ped ");
    }

    private StringBuilder appendPedidoColumns(StringBuilder sql) {
        return sql.append("ped.id, ped.cliente, ped.data, ped.status");
    }

    private StringBuilder appendSumPrecoTotal(StringBuilder sql) {
        sql.append("(SELECT COALESCE(SUM(item.quantidade * prod.preco), 0) ");
        sql.append(" FROM ").append(tableNameItemPedido).append(" item ");
        sql.append(" INNER JOIN ").append(tableNameProduto).append(" prod ON item.produto = prod.id ");
        sql.append(" WHERE item.pedido = ped.id) AS preco_total");
        return sql;
    }

    private StringBuilder appendClienteColumns(StringBuilder sql) {
        sql.append("cli.id AS cliente_id, cli.nome AS cliente_nome, cli.email AS cliente_email, cli.senha AS cliente_senha, ");
        sql.append("cli.endereco_logradouro AS cliente_logradouro, cli.endereco_cep AS cliente_cep, ");
        sql.append("cli.endereco_num AS cliente_numero, cli.endereco_complemento AS cliente_complemento");
        return sql;
    }

    private StringBuilder joinCliente(StringBuilder sql) {
        return sql.append("INNER JOIN ").append(tableNameCliente).append(" cli ON ped.cliente = cli.id ");
    }

    private Pedido rsToPedidoFull(ResultSet rs) throws SQLException {
        Pedido pedido = mapPedido(rs);
        pedido.setCliente(mapCliente(rs));
        return pedido;
    }

    private Pedido rsToPedidoWithCliente(ResultSet rs, Cliente cliente) throws SQLException {
        Pedido pedido = mapPedido(rs);
        pedido.setCliente(cliente);
        return pedido;
    }

    private Pedido mapPedido(ResultSet rs) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setId(UUID.fromString(rs.getString("id")));
        pedido.setPrecoTotal(rs.getDouble("preco_total"));
        pedido.setData(rs.getDate("data").toLocalDate());
        pedido.setStatus(StatusPedido.valueOf(rs.getString("status")));
        return pedido;
    }

    private Cliente mapCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(UUID.fromString(rs.getString("cliente_id")));
        cliente.setNome(rs.getString("cliente_nome"));
        cliente.setEmail(rs.getString("cliente_email"));

        try {
            cliente.setSenha(rs.getString("cliente_senha"));
        } catch (SQLException e) {
            cliente.setSenha(null);
        }

        cliente.setEnderecoLogradouro(rs.getString("cliente_logradouro"));
        cliente.setEnderecoCep(rs.getString("cliente_cep"));
        cliente.setEnderecoNum(rs.getInt("cliente_numero"));
        cliente.setEnderecoComplemento(rs.getString("cliente_complemento"));
        return cliente;
    }
    
    @Override
    public Optional<Pedido> add(Pedido pedido) throws SQLException, ClassNotFoundException {
        if (pedido == null || pedido.getId() == null) return Optional.empty();
        if (pedido.getCliente() == null || pedido.getCliente().getId() == null) return Optional.empty();

        String sql = "INSERT INTO " + tableName + " (id, cliente, data, status) VALUES (?, ?, ?, ?, ?);";
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, pedido.getId().toString());
            ps.setString(2, pedido.getCliente().getId().toString());
            ps.setDouble(3, pedido.getPrecoTotal());
            ps.setDate(4, Date.valueOf(pedido.getData()));
            ps.setString(5, pedido.getStatus().name());

            int colunasAfetadas = ps.executeUpdate();

            if (colunasAfetadas > 0) {
                return Optional.of(pedido);
            } else {
                return Optional.empty();
            }
        }
    }

    @Override
    public Optional<Pedido> findById(UUID id) throws SQLException, ClassNotFoundException {
        if (id == null) return Optional.empty();

        String sql = fullQueryById();

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, id.toString());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(rsToPedidoFull(rs));
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public Optional<List<Pedido>> searchAll() throws SQLException, ClassNotFoundException {
        String sql = fullQueryAll();
        List<Pedido> pedidos = new ArrayList<>();

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                pedidos.add(rsToPedidoFull(rs));
            }
        }
        return Optional.of(pedidos);
    }

    @Override
    public Optional<Pedido> update(Pedido pedido) throws SQLException, ClassNotFoundException {
        if (pedido == null || pedido.getId() == null) return Optional.empty();
        if (pedido.getCliente() == null || pedido.getCliente().getId() == null) return Optional.empty();

        String sql = "UPDATE " + tableName + " SET cliente = ?, data = ?, status = ? WHERE id = ?;";
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, pedido.getCliente().getId().toString());
            ps.setDouble(2, pedido.getPrecoTotal());
            ps.setDate(3, Date.valueOf(pedido.getData()));
            ps.setString(4, pedido.getStatus().name());
            ps.setString(5, pedido.getId().toString());

            int colunasAfetadas = ps.executeUpdate();

            if (colunasAfetadas > 0) {
                return Optional.of(pedido);
            } else {
                return Optional.empty();
            }
        }
    }

    @Override
    public Optional<List<Pedido>> findByCliente(Cliente cliente) throws SQLException, ClassNotFoundException {
        if (cliente == null || cliente.getId() == null) return Optional.empty();

        String sql = queryByCliente();
        List<Pedido> pedidos = new ArrayList<>();

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, cliente.getId().toString());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(rsToPedidoWithCliente(rs, cliente));
                }
            }
        }
        return Optional.of(pedidos);
    }

    @Override
    public Optional<Pedido> delete(Pedido pedido) throws SQLException, ClassNotFoundException {
        if (pedido == null || pedido.getId() == null) return Optional.empty();

        String sql = "DELETE FROM " + tableName + " WHERE id = ?;";

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, pedido.getId().toString());

            int colunasAfetadas = ps.executeUpdate();

            if (colunasAfetadas > 0) {
                return Optional.of(pedido);
            } else {
                return Optional.empty();
            }
        }
    }
}