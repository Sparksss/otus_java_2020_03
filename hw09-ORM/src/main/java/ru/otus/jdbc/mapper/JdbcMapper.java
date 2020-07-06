package ru.otus.jdbc.mapper;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface JdbcMapper<T> {
    void insert(T objectData) throws SQLException;

    void update(T objectData) throws SQLException;

    void insertOrUpdate(T objectData);

    T findById(long id, Class<T> clazz);
}
