package edu.fatec.poo.persistence;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class ADaoConnection implements IDaoConnection {

    protected Connection connection;

    protected String hostname;
    protected String dbName;
    protected String user;
    protected String senha;

    public ADaoConnection(String hostname, String dbName, String user, String senha) {
        this.hostname = hostname;
        this.dbName = dbName;
        this.user = user;
        this.senha = senha;
    }

    @Override
    public void closeConnection() throws ClassNotFoundException, SQLException {
        connection.close();
    }

    public Connection getSafeConnection() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            this.connection = getConnection();
        }
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
