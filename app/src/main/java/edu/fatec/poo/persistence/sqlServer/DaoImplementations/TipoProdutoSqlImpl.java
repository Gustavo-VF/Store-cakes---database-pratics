package edu.fatec.poo.persistence.sqlServer.DaoImplementations;

import edu.fatec.poo.persistence.DaoIntefaces.TipoProdutoDAO;
import edu.fatec.poo.persistence.connection.ADaoConnector;
import edu.fatec.poo.persistence.sqlServer.ConfiguredSqlConnector;

public class TipoProdutoSqlImpl implements TipoProdutoDAO {

    private final String dbName = "tipo_produto";
    private final ADaoConnector connector;

    TipoProdutoSqlImpl() {
        this.connector = new ConfiguredSqlConnector().getConector();
    }
}
