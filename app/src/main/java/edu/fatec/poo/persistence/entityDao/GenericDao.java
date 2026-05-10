package edu.fatec.poo.persistence.entityDao;

import edu.fatec.poo.model.IEntity;
import edu.fatec.poo.persistence.ADaoConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericDao<T extends IEntity> implements IDao<T> {

    protected Connection connection;
    protected String tableName;

    public GenericDao(ADaoConnection aDaoConnection, String tableName) throws SQLException, ClassNotFoundException {
        this.connection = aDaoConnection.getSafeConnection();
        this.tableName = tableName;
    }

    protected abstract T map(ResultSet rs) throws SQLException;

    protected abstract List<Object> getParameters(T object);

    @Override
    public T search(T object) throws SQLException {
        return searchById(object.getId());
    }

    @Override
    public T searchById(Long id) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<T> searchAll() throws SQLException {
        List<T> list = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    @Override
    public void delete(T object) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, object.getId());

            ps.execute();
        }
    }
}