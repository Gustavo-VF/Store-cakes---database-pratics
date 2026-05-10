package edu.fatec.poo.persistence.sqlServer;

import edu.fatec.poo.persistence.ADaoConnection;
import edu.fatec.poo.persistence.IDaoConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class sqlServerIDaoConnection extends ADaoConnection {

    public sqlServerIDaoConnection(String hostname, String dbName, String user, String senha) {
        super(hostname, dbName, user, senha);
    }

    @Override
    public Connection getConnection()
            throws ClassNotFoundException, SQLException {
        Class.forName("net.sorceforge.jtds.jdbc.Driver");

        c = DriverManager.getConnection(
                String.format(
                        "jdbc:jdts:sqlserver://%s:1433;databaseName=%s;user=%s;password=%s",
                        hostname, dbName, user, senha
                )
        );

        return c;
    }
}
