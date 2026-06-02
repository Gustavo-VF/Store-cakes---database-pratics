package edu.fatec.poo.persistence.sqlServer.create;

import edu.fatec.poo.persistence.connection.ADaoConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlServerConnector extends ADaoConnector {

    public SqlServerConnector(String hostname, String porta, String dbName, String user, String senha) {
        super(hostname, porta, dbName, user, senha);
    }

    @Override
    public Connection getConnection()
            throws ClassNotFoundException, SQLException {
        Class.forName("net.sourceforge.jtds.jdbc.Driver");

        return DriverManager.getConnection(
                String.format(
                        "jdbc:jtds:sqlserver://%s:%s;databaseName=%s;user=%s;password=%s",
                        hostname, porta, dbName, user, senha
                )
        );
    }
}
