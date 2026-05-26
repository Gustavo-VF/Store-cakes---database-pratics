package edu.fatec.poo.persistence.sqlServer.daoImplementations;

import edu.fatec.poo.model.Carrinho;
import edu.fatec.poo.model.Cliente;
import edu.fatec.poo.persistence.connection.ADaoConnector;
import edu.fatec.poo.persistence.daoIntefaces.CarrinhoDAO;
import edu.fatec.poo.persistence.sqlServer.ConfiguredSqlConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CarrinhoSqlImpl implements CarrinhoDAO {

    private final String tableName = "carrinho";
    private final String tableNameCliente = "cliente";
    private final ADaoConnector connector;

    public CarrinhoSqlImpl() {
        this.connector = new ConfiguredSqlConnector().getConector();
    }

    @Override
    public Optional<Carrinho> add(Carrinho carrinho) throws SQLException, ClassNotFoundException {
        if (carrinho == null || carrinho.getId() == null) return Optional.empty();
        if (carrinho.getCliente() == null || carrinho.getCliente().getId() == null) return Optional.empty();

        String sql = "INSERT INTO " + tableName + " " +
                "(id, cliente) " +
                "VALUES (?, ?);";
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, carrinho.getId().toString());
            ps.setString(2, carrinho.getCliente().getId().toString());

            int colunasAfetadas = ps.executeUpdate();
            return colunasAfetadas > 0 ? Optional.of(carrinho) : Optional.empty();
        }
    }


    @Override
    public Optional<Carrinho> findById(UUID id) throws SQLException, ClassNotFoundException {
        if (id == null) return Optional.empty();

        String sql = fullQuerryById();

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, id.toString());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Carrinho carrinho = rsToCarrinhoFull(rs);
                    return Optional.of(carrinho);
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public Optional<List<Carrinho>> searchAll() throws SQLException, ClassNotFoundException {
        String sql = fullQuerryAll();
        List<Carrinho> carrinhos = new ArrayList<>();

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Carrinho carrinho = rsToCarrinhoFull(rs);
                carrinhos.add(carrinho);
            }
        }
        return Optional.of(carrinhos);
    }

    @Override
    public Optional<Carrinho> update(Carrinho carrinho) throws SQLException, ClassNotFoundException {
        if (carrinho == null || carrinho.getId() == null) return Optional.empty();
        if (carrinho.getCliente() == null || carrinho.getCliente().getId() == null) return Optional.empty();

        String sql = "UPDATE " + tableName + " " +
                "SET cliente = ? " +
                "WHERE id = ?;";
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, carrinho.getCliente().getId().toString());
            ps.setString(2, carrinho.getId().toString());

            int colunasAfetadas = ps.executeUpdate();
            return colunasAfetadas > 0 ? Optional.of(carrinho) : Optional.empty();
        }
    }

    @Override
    public Optional<Carrinho> delete(Carrinho carrinho) throws SQLException, ClassNotFoundException {
        if (carrinho == null || carrinho.getId() == null) return Optional.empty();

        String sql = "DELETE FROM " + tableName + " WHERE id = ?;";

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, carrinho.getId().toString());

            int colunasAfetadas = ps.executeUpdate();
            return colunasAfetadas > 0 ? Optional.of(carrinho) : Optional.empty();
        }
    }

    private Carrinho rsToCarrinhoFull(ResultSet rs) throws SQLException {
        Carrinho carrinho = new Carrinho();
        carrinho.setId(UUID.fromString(rs.getString("carrinho_id")));

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
        return carrinho;
    }

    private StringBuilder fullQuerry() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("car.id AS carrinho_id, ");
        sql.append("cli.id AS cliente_id, cli.nome AS cliente_nome, cli.email AS cliente_email, ");
        sql.append("cli.senha AS cliente_senha, ");
        sql.append("cli.endereco_logradouro AS cliente_logradouro, cli.endereco_cep AS cliente_cep, ");
        sql.append("cli.endereco_num AS cliente_numero, cli.endereco_complemento AS cliente_complemento ");
        sql.append("FROM ").append(tableName).append(" car ");
        sql.append("INNER JOIN ").append(tableNameCliente).append(" cli ON car.cliente = cli.id ");
        return sql;
    }


    private String fullQuerryAll() {
        return fullQuerry().append(";").toString();
    }

    private String fullQuerryById() {
        return fullQuerry().append(" WHERE car.id = ?;").toString();
    }
}
