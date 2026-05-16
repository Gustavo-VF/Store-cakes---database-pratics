package edu.fatec.poo.persistence.entityDao;

import edu.fatec.poo.model.IEntity;
import edu.fatec.poo.persistence.connection.ADaoConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericDao<T extends IEntity> implements IDao<T> {

    protected final ADaoConnector connector;

    protected final String tableName;


    public GenericDao(ADaoConnector connector, String tableName) throws SQLException, ClassNotFoundException {
        this.connector = connector;
        this.tableName = tableName;
    }


    protected abstract T map(ResultSet rs) throws SQLException;


    protected abstract List<Object> getParameters(T object);


    @Override
    public T search(T object) throws SQLException, ClassNotFoundException {
        return searchById(object.getId());
    }

    @Override
    public T searchById(Long id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
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
    public List<T> searchAll() throws SQLException, ClassNotFoundException {
        List<T> list = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    @Override
    public void delete(T object) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM " + tableName + " WHERE id = ?;";
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, object.getId());
            ps.execute();
        }
    }
}