package edu.fatec.poo.persistence.sqlServer;

import edu.fatec.poo.persistence.connection.ADaoConnector;
import edu.fatec.poo.persistence.connection.ICreateDB;
import edu.fatec.poo.persistence.connection.ICreateTable;
import edu.fatec.poo.persistence.sqlServer.create.SqlServerConnector;
import edu.fatec.poo.persistence.sqlServer.create.SqlServerCreateDB;
import edu.fatec.poo.persistence.sqlServer.create.SqlServerCreateTable;

public class ConfiguredSqlConnector {

    private final String dbName = "store_cakes";
    private final String porta = "1433";
    private final String hostname = "localhost";
    private final String user = "sa";
    private final String senha = "qwer@1234";

    private ADaoConnector conector;

    public ConfiguredSqlConnector() {

        conector = new SqlServerConnector(hostname, porta, dbName, user, senha);
    }

    public void buildDb() {
        ADaoConnector connector;
        try {
            ADaoConnector bootstrapConnector = new SqlServerConnector(hostname, porta, "master", user, senha);

            ICreateDB createDB = new SqlServerCreateDB(bootstrapConnector, dbName);
            createDB.createDatabase();

            ICreateTable createTable = new SqlServerCreateTable(conector);
            createTable.createTableAll();
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
