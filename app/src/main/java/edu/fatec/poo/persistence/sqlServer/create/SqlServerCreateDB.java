package edu.fatec.poo.persistence.sqlServer.create;

import edu.fatec.poo.persistence.connection.ADaoConnector;
import edu.fatec.poo.persistence.connection.ICreateDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlServerCreateDB implements ICreateDB {

    private final String dbName;
    private final ADaoConnector connector;

    public SqlServerCreateDB(ADaoConnector connector, String dbName) {
        this.connector = connector;
        this.dbName = dbName;
    }

    @Override
    public void createDatabase() throws SQLException, ClassNotFoundException {
        String sql = """
                IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = ?) 
                BEGIN 
                  CREATE DATABASE ? 
                END;
                """;

        try (Connection connection = connector.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)
        ) {
            st.setString(1, dbName);
            st.setString(2, dbName);
            st.executeUpdate();
            System.out.println("[SQL Server] Operação de criação concluída ou banco já existente.");
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[SQL Server] Erro ao criar banco no: " + e.getMessage());
            throw e;
        }
    }
}
