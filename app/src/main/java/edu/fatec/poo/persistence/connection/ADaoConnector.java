package edu.fatec.poo.persistence.connection;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class ADaoConnector {

    protected final String porta;
    protected final String hostname;
    protected final String dbName;
    protected final String user;
    protected final String senha;

    public ADaoConnector(String hostname, String porta, String dbName, String user, String senha) {
        this.hostname = hostname;
        this.porta = porta;
        this.dbName = dbName;
        this.user = user;
        this.senha = senha;
    }

    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
}