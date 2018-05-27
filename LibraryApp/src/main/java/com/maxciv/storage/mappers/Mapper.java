package com.maxciv.storage.mappers;

import java.sql.SQLException;
import java.util.Map;

public interface Mapper<T> {

    T findById(final int id) throws SQLException;
    Map<Integer, T> findAll() throws SQLException;
    void update(T item) throws SQLException;
    void update() throws SQLException;
    void closeConnection() throws SQLException;
    void clear();
}
