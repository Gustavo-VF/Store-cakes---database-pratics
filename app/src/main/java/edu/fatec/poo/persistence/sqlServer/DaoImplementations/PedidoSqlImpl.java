package edu.fatec.poo.persistence.sqlServer.DaoImplementations;

import edu.fatec.poo.model.Cliente;
import edu.fatec.poo.model.Pedido;
import edu.fatec.poo.model.StatusPedido;
import edu.fatec.poo.persistence.DaoIntefaces.PedidoDAO;
import edu.fatec.poo.persistence.connection.ADaoConnector;
import edu.fatec.poo.persistence.sqlServer.ConfiguredSqlConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PedidoSqlImpl implements PedidoDAO {

    private final String tableName = "pedido";
    private final String tableNameCliente = "cliente";
    private final ADaoConnector connector;

    public PedidoSqlImpl() {
        this.connector = new ConfiguredSqlConnector().getConector();
    }

    @Override
    public Optional<Pedido> add(Pedido pedido) throws SQLException, ClassNotFoundException {
        if (pedido == null || pedido.getId() == null) return Optional.empty();
        if (pedido.getCliente() == null || pedido.getCliente().getId() == null) return Optional.empty();

        String sql = "INSERT INTO " + tableName + " " +
                "(id, cliente, preco_total, data, status) " +
                "VALUES (?, ?, ?, ?, ?);";
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, pedido.getId().toString());
            ps.setString(2, pedido.getCliente().getId().toString());
            ps.setDouble(3, pedido.getPrecoTotal());
            ps.setDate(4, Date.valueOf(pedido.getData()));
            ps.setString(5, pedido.getStatus().name());

            int colunasAfetadas = ps.executeUpdate();
            return colunasAfetadas > 0 ? Optional.of(pedido) : Optional.empty();
        }
    }

    @Override
    public Optional<Pedido> findById(UUID id) throws SQLException, ClassNotFoundException {
        if (id == null) return Optional.empty();

        String sql = "SELECT " +
                "p.id, p.cliente, p.preco_total, p.data, p.status, " +
                "c.id AS cliente_id, c.nome AS cliente_nome, c.email AS cliente_email, c.senha AS cliente_senha, " +
                "c.telefone AS cliente_telefone, c.endereco_logradouro AS cliente_logradouro, " +
                "c.endereco_cep AS cliente_cep, c.endereco_num AS cliente_numero, " +
                "c.endereco_complemento AS cliente_complemento " +
                "FROM " + tableName + " p " +
                "INNER JOIN " + tableNameCliente + " c ON p.cliente = c.id " +
                "WHERE p.id = ?;";

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, id.toString());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Pedido pedido = new Pedido();
                    pedido.setId(UUID.fromString(rs.getString("id")));
                    pedido.setPrecoTotal(rs.getDouble("preco_total"));
                    pedido.setData(rs.getDate("data").toLocalDate());
                    pedido.setStatus(StatusPedido.valueOf(rs.getString("status")));

                    Cliente cliente = new Cliente();
                    cliente.setId(UUID.fromString(rs.getString("cliente_id")));
                    cliente.setNome(rs.getString("cliente_nome"));
                    cliente.setEmail(rs.getString("cliente_email"));
                    cliente.setSenha(rs.getString("cliente_senha"));
                    cliente.setEnderecoLogradouro(rs.getString("cliente_logradouro"));
                    cliente.setEnderecoCep(rs.getString("cliente_cep"));
                    cliente.setEnderecoNum(rs.getInt("cliente_numero"));
                    cliente.setComplemento(rs.getString("cliente_complemento"));

                    pedido.setCliente(cliente);
                    return Optional.of(pedido);
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public Optional<List<Pedido>> searchAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT " +
                "p.id, p.cliente, p.preco_total, p.data, p.status, " +
                "c.id AS cliente_id, c.nome AS cliente_nome, c.email AS cliente_email, " +
                "c.telefone AS cliente_telefone, c.endereco_logradouro AS cliente_logradouro, " +
                "c.endereco_cep AS cliente_cep, c.endereco_num AS cliente_numero, " +
                "c.endereco_complemento AS cliente_complemento " +
                "FROM " + tableName + " p " +
                "INNER JOIN " + tableNameCliente + " c ON p.cliente = c.id;";
        List<Pedido> pedidos = new ArrayList<>();

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(UUID.fromString(rs.getString("id")));
                pedido.setPrecoTotal(rs.getDouble("preco_total"));
                pedido.setData(rs.getDate("data").toLocalDate());
                pedido.setStatus(StatusPedido.valueOf(rs.getString("status")));

                Cliente cliente = new Cliente();
                cliente.setId(UUID.fromString(rs.getString("cliente_id")));
                cliente.setNome(rs.getString("cliente_nome"));
                cliente.setEmail(rs.getString("cliente_email"));
                cliente.setEnderecoLogradouro(rs.getString("cliente_logradouro"));
                cliente.setEnderecoCep(rs.getString("cliente_cep"));
                cliente.setEnderecoNum(rs.getInt("cliente_numero"));
                cliente.setComplemento(rs.getString("cliente_complemento"));

                pedido.setCliente(cliente);
                pedidos.add(pedido);
            }
        }
        return Optional.of(pedidos);
    }

    @Override
    public Optional<Pedido> update(Pedido pedido) throws SQLException, ClassNotFoundException {
        if (pedido == null || pedido.getId() == null) return Optional.empty();
        if (pedido.getCliente() == null || pedido.getCliente().getId() == null) return Optional.empty();

        String sql = "UPDATE " + tableName + " " +
                "SET cliente = ?, preco_total = ?, data = ?, status = ? " +
                "WHERE id = ?;";
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, pedido.getCliente().getId().toString());
            ps.setDouble(2, pedido.getPrecoTotal());
            ps.setDate(3, Date.valueOf(pedido.getData()));
            ps.setString(4, pedido.getStatus().name());
            ps.setString(5, pedido.getId().toString());

            int colunasAfetadas = ps.executeUpdate();
            return colunasAfetadas > 0 ? Optional.of(pedido) : Optional.empty();
        }
    }

    @Override
    public Optional<Pedido> delete(Pedido pedido) throws SQLException, ClassNotFoundException {
        if (pedido == null || pedido.getId() == null) return Optional.empty();

        String sql = "DELETE FROM " + tableName + " WHERE id = ?;";

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, pedido.getId().toString());

            int colunasAfetadas = ps.executeUpdate();
            return colunasAfetadas > 0 ? Optional.of(pedido) : Optional.empty();
        }
    }
}