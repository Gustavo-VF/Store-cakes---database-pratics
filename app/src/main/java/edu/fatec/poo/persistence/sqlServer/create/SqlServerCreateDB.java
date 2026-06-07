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
        this.dbName = dbName.replaceAll("[^a-zA-Z0-9_]", "");
    }

    @Override
    public void createDatabase() throws SQLException, ClassNotFoundException {
        StringBuilder sql = new StringBuilder();
        sql.append("IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = '").append(dbName).append("')");
        sql.append("BEGIN ");
        sql.append("    CREATE DATABASE ").append(dbName).append(" ");
        sql.append("END;");

        try (Connection connection = connector.getConnection();
             PreparedStatement st = connection.prepareStatement(sql.toString())
        ) {
            st.executeUpdate();
            System.out.println("[SQL Server] Operação de criação concluída ou banco já existente.");
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[SQL Server] Erro ao criar banco: " + e.getMessage());
            throw e;
        }
    }
}
