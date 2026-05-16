package edu.fatec.poo.persistence.sqlServer;

import edu.fatec.poo.persistence.connection.ADaoConnector;
import edu.fatec.poo.persistence.connection.ICreateTable;

import java.sql.*;

public class sqlServerCreateTable implements ICreateTable {

    private final ADaoConnector connector;

    public sqlServerCreateTable(ADaoConnector connector) {
        this.connector = connector;
    }

    @Override
    public void createTableAll() throws SQLException, ClassNotFoundException {
        createTabelCliente();
        //TODO
    }

    @Override
    public void createTabelCliente() throws SQLException, ClassNotFoundException {
        String sql = """
                    CREATE TABLE IF NOT EXISTS cliente (
                        id INT IDENTITY(1,1) PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL,
                        email VARCHAR(100) UNIQUE NOT NULL,
                        telefone VARCHAR(20),
                        endereco_logradouro VARCHAR(150),
                        endereco_cep VARCHAR(9),
                        endereco_num INT,
                        endereco_complemento VARCHAR(50)
                    );
                """;
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.execute();
            System.out.println("[SQL Server] Tabela Cliente criada com sucesso ou já existente.");
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[SQL Server] Erro ao criar tabela Cliente no: " + e.getMessage());
            throw e;
        }
    }
}
