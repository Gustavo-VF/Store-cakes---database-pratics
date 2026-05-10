package edu.fatec.poo.persistence.sqlServer;

import edu.fatec.poo.persistence.ICreateTable;
import edu.fatec.poo.persistence.IDaoConnection;

import java.sql.*;

public class sqlServerCreateTable implements ICreateTable {

    private final Connection c;

    public sqlServerCreateTable(IDaoConnection IDaoConnection) throws SQLException, ClassNotFoundException {
        c = IDaoConnection.getConnection();
    }

    @Override
    public boolean tableExists(String nomeTabela) throws SQLException {
        DatabaseMetaData meta = c.getMetaData();
        try (ResultSet rs = meta.getTables(null, null, nomeTabela, new String[]{"TABLE"})) {
            return rs.next();
        }
    }

    @Override
    public void createAll() throws SQLException {
        createCliente();
        //TODO
    }

    @Override
    public void createCliente() throws SQLException {
        String sql = """
                CREATE TABLE cliente (
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
}
