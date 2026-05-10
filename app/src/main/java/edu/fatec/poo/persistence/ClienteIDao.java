package edu.fatec.poo.persistence;

import edu.fatec.poo.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteIDao implements IDao<Cliente> {

    private final Connection c;

    public ClienteIDao(IDaoConnection IDaoConnection) throws SQLException, ClassNotFoundException {
        c = IDaoConnection.getConnection();

        initDatabase();
    }

    private void initDatabase() throws SQLException {
        DatabaseMetaData meta = c.getMetaData();
        String dbName = meta.getDatabaseProductName().toLowerCase();

        if (!tableExists("cliente")) {
            if (dbName.contains("microsoft")) {
                createTableSQLServer();
            } else if (dbName.contains("mysql") || dbName.contains("mariadb")) {
                createTableMySQL();
            } else {
                throw new SQLException("Banco de dados '" + dbName + "' não configurado para inicialização automática.");
            }
        }
    }

    private void createTableMySQL() throws SQLException {
        String sql = """
                CREATE TABLE cliente (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    email VARCHAR(100) UNIQUE NOT NULL,
                    telefone INT,
                    endereco_logradouro VARCHAR(150),
                    endereco_cep VARCHAR(9),
                    endereco_num INT,
                    endereco_complemento VARCHAR(50)
                );
                """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.execute();
        }
    }

    private void createTableSQLServer() throws SQLException {
        String sql = """
                CREATE TABLE cliente (
                    id INT IDENTITY(1,1) PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    email VARCHAR(100) UNIQUE NOT NULL,
                    telefone INT,
                    endereco_logradouro VARCHAR(150),
                    endereco_cep VARCHAR(9),
                    endereco_num INT,
                    endereco_complemento VARCHAR(50)
                );
                """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.execute();
        }
    }

    private boolean tableExists(String tableName) throws SQLException {
        DatabaseMetaData meta = c.getMetaData();
        try (ResultSet rs = meta.getTables(null, null, tableName, new String[]{"TABLE"})) {
            return rs.next();
        }
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
