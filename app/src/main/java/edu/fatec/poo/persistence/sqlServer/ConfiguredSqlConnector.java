package edu.fatec.poo.persistence.sqlServer;

import edu.fatec.poo.persistence.connection.ADaoConnector;
import edu.fatec.poo.persistence.connection.ICreateDB;
import edu.fatec.poo.persistence.connection.ICreateTable;
import edu.fatec.poo.persistence.sqlServer.create.SqlServerConnector;
import edu.fatec.poo.persistence.sqlServer.create.SqlServerCreateDB;
import edu.fatec.poo.persistence.sqlServer.create.sqlServerCreateTable;

import java.sql.Connection;

public class ConfiguredSqlConnector {

    private final String dbName = "store_cakes";
    private final String porta = "3306";
    private ADaoConnector conector;

    public ConfiguredSqlConnector() {
        conector = new SqlServerConnector(
                "localhost",
                porta,
                dbName,
                "root",
                "12345678"
        );
    }

    public void buildMariaDb() {
        ADaoConnector connector;
        try {
            connector = new SqlServerConnector(
                    "localhost",
                    porta,
                    "sys",
                    "root",
                    "12345678"
            );
            try (Connection connection = connector.getConnection()) {
                ICreateDB createDB = new SqlServerCreateDB(connector, dbName);
                createDB.createDatabase();
            }

            connector = new SqlServerConnector(
                    "localhost",
                    porta,
                    dbName,
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
