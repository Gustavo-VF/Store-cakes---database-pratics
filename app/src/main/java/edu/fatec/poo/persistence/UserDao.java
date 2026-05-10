package edu.fatec.poo.persistence;

import edu.fatec.poo.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<User> {

    private Connection c;

    public UserDao(DaoConnection daoConnection) throws SQLException, ClassNotFoundException {
        c = daoConnection.getConnection();
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
        String sql = "SELECT * FROM user WHERE id = ?;";

        try (PreparedStatement preparedStatement = c.prepareStatement(sql)) {
            preparedStatement.setLong(1, user.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    map(user, resultSet);
                } else {
                    user = null;
                }
            }
        }
        return user;
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
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user;";

        try (PreparedStatement preparedStatement = c.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    User user = new User();
                    map(user, resultSet);
                    users.add(user);
                }
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
