package edu.fatec.poo.persistence.entityDao;

import edu.fatec.poo.model.Cliente;
import edu.fatec.poo.persistence.connection.ADaoConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao extends GenericDao<Cliente> implements IDao<Cliente> {

    public ClienteDao(ADaoConnector connector) throws SQLException, ClassNotFoundException {
        super(connector, "cliente");
    }

    @Override
    public Cliente add(Cliente cliente) throws SQLException, ClassNotFoundException {
        //TODO
        String sql = """
                INSERT INTO cliente
                (nome, email, telefone, endereco_logradouro, endereco_cep, endereco_num, endereco_complemento)
                VALUES
                (?,?,?,?,?,?,?);
                """;
        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getEmail());
            ps.setInt(3, cliente.getTelefone());
            ps.setString(4, cliente.getEnderecoLogradouro());
            ps.setString(5, cliente.getEnderecoCep());
            ps.setInt(6, cliente.getEnderecoNum());
            ps.setString(7, cliente.getComplemento());

            ps.execute();
        }
        return cliente;
    }

    public Cliente searchByEmail(String email) throws SQLException, ClassNotFoundException {
        return searchByField("email", email);
    }

    public Cliente searchByNome(String nome) throws SQLException, ClassNotFoundException {
        return searchByField("nome", nome);
    }

    private Cliente searchByField(String fieldName, Object valor) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM cliente WHERE " + fieldName + " = ?;";
        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, valor);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    @Override
    public void update(Cliente cliente) throws SQLException, ClassNotFoundException {
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

        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            List<Object> parametros = getParameters(cliente);
            parametros.add(cliente.getId());
            for (int i = 0; i < parametros.size(); i++) {
                ps.setObject(i + 1, parametros.get(i));
            }

            ps.execute();
        }
    }

    private List<Cliente> searchAllSortedByName() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM cliente ORDER BY nome ASC;";
        List<Cliente> clientes = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                clientes.add(map(rs));
            }
        }
        return clientes;
    }

    public Cliente map(ResultSet resultSet) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(resultSet.getInt("id"));
        cliente.setNome(resultSet.getString("nome"));
        cliente.setEmail(resultSet.getString("email"));
        cliente.setTelefone(resultSet.getInt("telefone"));
        cliente.setEnderecoLogradouro(resultSet.getString("endereco_logradouro"));
        cliente.setEnderecoCep(resultSet.getString("endereco_cep"));
        cliente.setEnderecoNum(resultSet.getInt("endereco_num"));
        cliente.setComplemento(resultSet.getString("endereco_complemento"));
        return cliente;
    }

    @Override
    protected ArrayList<Object> getParameters(Cliente cliente) {
        ArrayList<Object> parametros = new ArrayList<>();
        parametros.add(cliente.getNome());
        parametros.add(cliente.getEmail());
        parametros.add(cliente.getTelefone());
        parametros.add(cliente.getEnderecoLogradouro());
        parametros.add(cliente.getEnderecoCep());
        parametros.add(cliente.getEnderecoNum());
        parametros.add(cliente.getComplemento());
        return parametros;
    }
}
