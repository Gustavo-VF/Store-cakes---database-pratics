package edu.fatec.poo.persistence.connection;

import java.sql.SQLException;

public interface ICreateTable {

    void createTableAll() throws SQLException, ClassNotFoundException;

    void createTabelCliente() throws SQLException, ClassNotFoundException;
}
