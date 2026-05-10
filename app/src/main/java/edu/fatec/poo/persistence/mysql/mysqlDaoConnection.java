package edu.fatec.poo.persistence.mysql;

import edu.fatec.poo.persistence.DaoConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class mysqlDaoConnection implements DaoConnection {
    private Connection c;

    private String hostname;
    private String dbName;
    private String user;
    private String senha;

    public mysqlDaoConnection(String hostname, String dbName, String user, String senha) {
        this.hostname = hostname;
        this.dbName = dbName;
        this.user = user;
        this.senha = senha;
    }
    
    @Override
    public Connection getConnection()
            throws ClassNotFoundException, SQLException {


        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = String.format(
                "jdbc:mysql://%s:3306/%s?useTimezone=true&serverTimezone=UTC&useSSL=false",
                hostname, dbName
        );

        c = DriverManager.getConnection(url, user, senha);

        return c;
    }

    public Connection getC() {
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
