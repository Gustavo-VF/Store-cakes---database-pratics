package edu.fatec.poo.persistence;

import java.sql.Connection;
import java.sql.SQLException;

public interface DaoConnection {
    public Connection getConnection() throws ClassNotFoundException, SQLException;
}
