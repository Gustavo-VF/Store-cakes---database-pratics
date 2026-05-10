package edu.fatec.poo.persistence;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class ADaoConnection implements IDaoConnection {

    protected Connection c;

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
        c.close();
    }

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        return getC();
    }

    public Connection getC() throws SQLException, ClassNotFoundException {
        if (c == null) {
            getConnection();
        }
        return c;
    }

    public void setC(Connection c) {
        this.c = c;
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
