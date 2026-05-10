package edu.fatec.poo.persistence;

import java.sql.SQLException;

public interface ICreateTable {
    public boolean tableExists(String nomeTabela) throws SQLException;

    public void createAll() throws SQLException;

    public void createCliente() throws SQLException;
}
