package edu.fatec.poo.persistence.DaoIntefaces;

import edu.fatec.poo.model.TipoProduto;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface TipoProdutoDAO {
    TipoProduto add() throws SQLException, ClassNotFoundException;

    TipoProduto findById(UUID id) throws SQLException, ClassNotFoundException;

    List<TipoProduto> findByName(String name) throws SQLException, ClassNotFoundException;

    List<TipoProduto> searchAll() throws SQLException, ClassNotFoundException;

    TipoProduto update(TipoProduto tipoProduto) throws SQLException, ClassNotFoundException;

    TipoProduto delete(TipoProduto tipoProduto) throws SQLException, ClassNotFoundException;
}
