package edu.fatec.poo.persistence.connection;

import edu.fatec.poo.persistence.sqlServer.sqlServerConnector;
import edu.fatec.poo.persistence.sqlServer.sqlServerCreateDB;
import edu.fatec.poo.persistence.sqlServer.sqlServerCreateTable;

import java.sql.Connection;
import java.sql.SQLException;

public class CurrentConnection {

    private ADaoConnector conector;

    public CurrentConnection() {
        conector = new sqlServerConnector(
                "localhost",
                "3306",
                "Doacao",
                "root",
                "12345678"
        );
    }

    public void buildMariaDb() {
        ADaoConnector connector;
        try {
            connector = new sqlServerConnector(
                    "localhost",
                    "3306",
                    "sys",
                    "root",
                    "12345678"
            );
            try (Connection connection = connector.getConnection()) {
                ICreateDB createDB = new sqlServerCreateDB(connector);
                createDB.createDatabase();
            }

            connector = new sqlServerConnector(
                    "localhost",
                    "3306",
                    "Doacao",
                    "root",
                    "12345678"
            );
            try (Connection connection = connector.getConnection()) {
                ICreateTable createTable = new sqlServerCreateTable(connector);
                createTable.createTableAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ADaoConnector getConector() {
        return conector;
    }

    public void setConector(ADaoConnector conector) {
        this.conector = conector;
    }
}
