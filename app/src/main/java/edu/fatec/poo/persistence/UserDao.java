package edu.fatec.poo.persistence;

import edu.fatec.poo.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<User> {

    private Connection c;

    public UserDao(DaoConnection daoConnection) throws SQLException, ClassNotFoundException {
        c = daoConnection.getConnection();

        initDatabase();
    }

    private void initDatabase() throws SQLException {
        DatabaseMetaData meta = c.getMetaData();
        String dbName = meta.getDatabaseProductName().toLowerCase();

        if (!tableExists("user")) {
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
                CREATE TABLE user (
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
                CREATE TABLE [user] (
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
    public void add(User user) throws SQLException {
        //TODO
        String sql = """
                INSERT INTO user
                (nome, email, telefone, endereco_logradouro, endereco_cep, endereco_num, endereco_complemento)
                VALUES
                (?,?,?,?,?,?,?);
                """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, user.getNome());
            ps.setString(2, user.getEmail());
            ps.setInt(3, user.getTelefone());
            ps.setString(4, user.getEnderecoLogradouro());
            ps.setString(5, user.getEnderecoCep());
            ps.setInt(6, user.getEnderecoNum());
            ps.setString(7, user.getComplemento());

            ps.execute();
        }
    }

    @Override
    public User search(User user) throws SQLException {
        return searchByField("id", user.getId());
    }

    @Override
    public User searchById(Long id) throws SQLException {
        return searchByField("id", id);
    }

    public User searchByEmail(String email) throws SQLException {
        return searchByField("email", email);
    }

    public User searchByNome(String nome) throws SQLException {
        return searchByField("nome", nome);
    }

    private User searchByField(String fieldName, Object value) throws SQLException {
        String sql = "SELECT * FROM user WHERE " + fieldName + " = ?;";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, value);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    map(user, rs);
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public void update(User user) throws SQLException {
        String sql = """
                UPDATE user SET
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
            ps.setString(1, user.getNome());
            ps.setString(2, user.getEmail());
            ps.setInt(3, user.getTelefone());
            ps.setString(4, user.getEnderecoLogradouro());
            ps.setString(5, user.getEnderecoCep());
            ps.setInt(6, user.getEnderecoNum());
            ps.setString(7, user.getComplemento());
            ps.setLong(8, user.getId());

            ps.execute();
        }
    }

    @Override
    public void delete(User user) throws SQLException {
        String sql = "DELETE FROM user WHERE id = ?;";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, user.getId());

            ps.execute();
        }
    }

    @Override
    public List<User> searchAll() throws SQLException {
        return executeQuery("SELECT * FROM user;");
    }

    public List<User> searchAllSortedByName() throws SQLException {
        return executeQuery("SELECT * FROM user ORDER BY nome ASC;");
    }

    private List<User> executeQuery(String sql) throws SQLException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                map(user, rs);
                users.add(user);
            }
        }
        return users;
    }

    private void map(User user, ResultSet resultSet) throws SQLException {
        user.setId(resultSet.getInt("id"));
        user.setNome(resultSet.getString("nome"));
        user.setEmail(resultSet.getString("email"));
        user.setTelefone(resultSet.getInt("telefone"));
        user.setEnderecoLogradouro(resultSet.getString("endereco_logradouro"));
        user.setEnderecoCep(resultSet.getString("endereco_cep"));
        user.setEnderecoNum(resultSet.getInt("endereco_num"));
        user.setComplemento(resultSet.getString("endereco_complemento"));
    }
}
