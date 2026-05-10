package edu.fatec.poo.persistence.sqlServer;

import edu.fatec.poo.persistence.ADaoConnection;
import edu.fatec.poo.persistence.ICreateDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class sqlServerCreateDB implements ICreateDB {

    private final Connection c;

    public sqlServerCreateDB(ADaoConnection aDaoConnection) throws SQLException, ClassNotFoundException {
        c = aDaoConnection.getC();
    }

    @Override
    public void createDatabase() throws SQLException {
        String sql = "IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'store_cakes') " +
                "BEGIN " +
                "  CREATE DATABASE store_cakes " +
                "END";

        try (Statement st = c.createStatement()) {
            st.executeUpdate(sql);
            System.out.println("[SQL Server] Operação de criação concluída ou banco já existente.");
        } catch (SQLException e) {
            // O SQL Server pode exigir que você esteja no banco 'master' para executar isso
            System.err.println("Erro ao criar banco no SQL Server: " + e.getMessage());
            throw e;
        }
    }
}
