package edu.fatec.poo.persistence.daoIntefaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BasicDAO<T> {
    Optional<T> add(T object) throws SQLException, ClassNotFoundException;

    Optional<T> findById(UUID id) throws SQLException, ClassNotFoundException;

    Optional<List<T>> searchAll() throws SQLException, ClassNotFoundException;

    Optional<T> update(T object) throws SQLException, ClassNotFoundException;

    Optional<T> delete(T object) throws SQLException, ClassNotFoundException;
}
