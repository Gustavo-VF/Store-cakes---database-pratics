package edu.fatec.poo.persistence.connection;

import java.sql.SQLException;

public interface ICreateTable {

    void createTableAll() throws SQLException, ClassNotFoundException;

    void createTableCliente() throws SQLException, ClassNotFoundException;

    void createTableTipoProduto() throws SQLException, ClassNotFoundException;

    void createTableProduto() throws SQLException, ClassNotFoundException;

    void createTablePedido() throws SQLException, ClassNotFoundException;

    void createTableItemPedido() throws SQLException, ClassNotFoundException;

}
