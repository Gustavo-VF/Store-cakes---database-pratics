package edu.fatec.poo.persistence.sqlServer.daoImplementations;

import edu.fatec.poo.model.Cliente;
import edu.fatec.poo.persistence.connection.ADaoConnector;
import edu.fatec.poo.persistence.daoIntefaces.ClienteDAO;
import edu.fatec.poo.persistence.sqlServer.ConfiguredSqlConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClienteSqlImpl implements ClienteDAO {
    private final String tableName = "cliente";
    private final ADaoConnector connector;

    public ClienteSqlImpl() {
        this.connector = new ConfiguredSqlConnector().getConector();
    }

    /*
        private UUID Id;
        private String nome;
        private String email;
        private String senha;
        private String enderecoLogradouro;
        private String enderecoCep;
        private int enderecoNum;
        private String enderecoComplemento;
     */
    @Override
    public Optional<Cliente> add(Cliente cliente) throws SQLException, ClassNotFoundException {
        if (cliente == null || cliente.getId() == null) return Optional.empty();

        String sql = "INSERT INTO " + tableName + " " +
                "(id, nome, email, senha, endereco_logradouro, endereco_cep, endereco_num, endereco_complemento) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, cliente.getId().toString());
            ps.setString(2, cliente.getNome());
            ps.setString(3, cliente.getEmail());
            ps.setString(4, cliente.getSenha());
            ps.setString(5, cliente.getEnderecoLogradouro());
            ps.setString(6, cliente.getEnderecoCep());
            ps.setInt(7, cliente.getEnderecoNum());
            ps.setString(8, cliente.getEnderecoComplemento());

            int colunasAfetadas = ps.executeUpdate();
            return colunasAfetadas > 0 ? Optional.of(cliente) : Optional.empty();
        }
    }


    @Override
    public Optional<Cliente> findById(UUID id) throws SQLException, ClassNotFoundException {
        if (id == null) return Optional.empty();

        String sql = fullQuerryById();

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, id.toString());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = rsToClienteFull(rs);
                    return Optional.of(cliente);
                } else {
                    return Optional.empty();
                }
            }
        }
    }


    @Override
    public Optional<List<Cliente>> searchAll() throws SQLException, ClassNotFoundException {
        String sql = fullQuerryAll();
        List<Cliente> clientes = new ArrayList<>();

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = rsToClienteFull(rs);
                clientes.add(cliente);
            }
        }
        return Optional.of(clientes);
    }

    @Override
    public Optional<Cliente> update(Cliente cliente) throws SQLException, ClassNotFoundException {
        if (cliente == null || cliente.getId() == null) return Optional.empty();

        String sql = "UPDATE " + tableName + " " +
                "SET nome = ?, email = ?, senha = ?, endereco_logradouro = ?, endereco_cep = ?, " +
                "endereco_num = ? , endereco_complemento = ? " +
                "WHERE id = ?;";
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getSenha());
            ps.setString(4, cliente.getEnderecoLogradouro());
            ps.setString(5, cliente.getEnderecoCep());
            ps.setInt(6, cliente.getEnderecoNum());
            ps.setString(7, cliente.getEnderecoComplemento());
            ps.setString(8, cliente.getId().toString());

            int colunasAfetadas = ps.executeUpdate();
            return colunasAfetadas > 0 ? Optional.of(cliente) : Optional.empty();
        }
    }

    @Override
    public Optional<Cliente> delete(Cliente cliente) throws SQLException, ClassNotFoundException {
        if (cliente == null || cliente.getId() == null) return Optional.empty();

        String sql = "DELETE FROM " + tableName + " WHERE id = ?;";

        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, cliente.getId().toString());

            int colunasAfetadas = ps.executeUpdate();
            return colunasAfetadas > 0 ? Optional.of(cliente) : Optional.empty();
        }
    }

    private Cliente rsToClienteFull(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(UUID.fromString(rs.getString("id")));
        cliente.setNome(rs.getString("nome"));
        cliente.setEmail(rs.getString("email"));
        cliente.setSenha(rs.getString("senha"));
        cliente.setEnderecoLogradouro(rs.getString("endereco_logradouro"));
        cliente.setEnderecoCep(rs.getString("endereco_cep"));
        cliente.setEnderecoNum(rs.getInt("endereco_num"));
        cliente.setEnderecoComplemento(rs.getString("endereco_complemento"));

        return cliente;
    }

    private StringBuilder fullQuerry() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("id, nome, email, senha, endereco_logradouro, endereco_cep, endereco_num, endereco_complemento ");
        sql.append("FROM ").append(tableName).append(" ");
        return sql;
    }


    private String fullQuerryAll() {
        return fullQuerry().append(";").toString();
    }

    private String fullQuerryById() {
        return fullQuerry().append(" WHERE id = ?;").toString();
    }
}
