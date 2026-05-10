package edu.fatec.poo.persistence.entityDao;

import edu.fatec.poo.model.Cliente;
import edu.fatec.poo.persistence.ADaoConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteIDao implements IDao<Cliente> {

    private final Connection c;

    public ClienteIDao(ADaoConnection aDaoConnection) throws SQLException, ClassNotFoundException {
        c = aDaoConnection.getC();
    }

    @Override
    public void add(Cliente cliente) throws SQLException {
        //TODO
        String sql = """
                INSERT INTO cliente
                (nome, email, telefone, endereco_logradouro, endereco_cep, endereco_num, endereco_complemento)
                VALUES
                (?,?,?,?,?,?,?);
                """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getEmail());
            ps.setInt(3, cliente.getTelefone());
            ps.setString(4, cliente.getEnderecoLogradouro());
            ps.setString(5, cliente.getEnderecoCep());
            ps.setInt(6, cliente.getEnderecoNum());
            ps.setString(7, cliente.getComplemento());

            ps.execute();
        }
    }

    @Override
    public Cliente search(Cliente cliente) throws SQLException {
        return searchByField("id", cliente.getId());
    }

    @Override
    public Cliente searchById(Long id) throws SQLException {
        return searchByField("id", id);
    }

    public Cliente searchByEmail(String email) throws SQLException {
        return searchByField("email", email);
    }

    public Cliente searchByNome(String nome) throws SQLException {
        return searchByField("nome", nome);
    }

    private Cliente searchByField(String fieldName, Object value) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE " + fieldName + " = ?;";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, value);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    map(cliente, rs);
                    return cliente;
                }
            }
        }
        return null;
    }

    @Override
    public void update(Cliente cliente) throws SQLException {
        String sql = """
                UPDATE cliente SET
                nome = ?, 
                email = ?, 
                telefone = ?, 
                endereco_logradouro = ?, 
                endereco_cep = ?, 
                endereco_num = ?, 
                endereco_complemento = ? 
                WHERE id = ?;
                """;

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getEmail());
            ps.setInt(3, cliente.getTelefone());
            ps.setString(4, cliente.getEnderecoLogradouro());
            ps.setString(5, cliente.getEnderecoCep());
            ps.setInt(6, cliente.getEnderecoNum());
            ps.setString(7, cliente.getComplemento());
            ps.setLong(8, cliente.getId());

            ps.execute();
        }
    }

    @Override
    public void delete(Cliente cliente) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id = ?;";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, cliente.getId());

            ps.execute();
        }
    }

    @Override
    public List<Cliente> searchAll() throws SQLException {
        return executeQuery("SELECT * FROM cliente;");
    }

    public List<Cliente> searchAllSortedByName() throws SQLException {
        return executeQuery("SELECT * FROM cliente ORDER BY nome ASC;");
    }

    private List<Cliente> executeQuery(String sql) throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                map(cliente, rs);
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    private void map(Cliente cliente, ResultSet resultSet) throws SQLException {
        cliente.setId(resultSet.getInt("id"));
        cliente.setNome(resultSet.getString("nome"));
        cliente.setEmail(resultSet.getString("email"));
        cliente.setTelefone(resultSet.getInt("telefone"));
        cliente.setEnderecoLogradouro(resultSet.getString("endereco_logradouro"));
        cliente.setEnderecoCep(resultSet.getString("endereco_cep"));
        cliente.setEnderecoNum(resultSet.getInt("endereco_num"));
        cliente.setComplemento(resultSet.getString("endereco_complemento"));
    }
}
