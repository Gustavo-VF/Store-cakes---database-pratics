package edu.fatec.poo.persistence.sqlServer.DaoImplementations;

import edu.fatec.poo.model.TipoProduto;
import edu.fatec.poo.persistence.DaoIntefaces.TipoProdutoDAO;
import edu.fatec.poo.persistence.connection.ADaoConnector;
import edu.fatec.poo.persistence.sqlServer.ConfiguredSqlConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TipoProdutoSqlImpl implements TipoProdutoDAO {

    private final String dbName = "tipo_produto";
    private final ADaoConnector connector;

    TipoProdutoSqlImpl() {
        this.connector = new ConfiguredSqlConnector().getConector();
    }

    @Override
    public Optional<TipoProduto> add(TipoProduto tipoProduto) throws SQLException, ClassNotFoundException {
        if (tipoProduto == null || tipoProduto.getId() == null) {
            return Optional.empty();
        }

        String sql = "INSERT INTO " + dbName + " (id, descricao) VALUES (?, ?);";
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, tipoProduto.getId());
            ps.setString(2, tipoProduto.getDescricao());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0 ? Optional.of(tipoProduto) : Optional.empty();
        }
    }
}
