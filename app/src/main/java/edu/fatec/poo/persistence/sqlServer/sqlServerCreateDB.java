package edu.fatec.poo.persistence.sqlServer;

import edu.fatec.poo.persistence.connection.ADaoConnector;
import edu.fatec.poo.persistence.connection.ICreateDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class sqlServerCreateDB implements ICreateDB {

    private final ADaoConnector connector;

    public sqlServerCreateDB(ADaoConnector connector) {
        this.connector = connector;
    }

    @Override
    public void createDatabase() throws SQLException, ClassNotFoundException {
        String sql = "IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'store_cakes') " +
                "BEGIN " +
                "  CREATE DATABASE store_cakes " +
                "END";

        try (Connection connection = connector.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)
        ) {
            st.executeUpdate();
            System.out.println("[SQL Server] Operação de criação concluída ou banco já existente.");
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[SQL Server] Erro ao criar banco no: " + e.getMessage());
            throw e;
        }
    }
}
