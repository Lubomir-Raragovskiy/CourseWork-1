package com.example.coursework.dao;

import com.example.coursework.entity.Supplier;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface Dao<K, T> {

    int before = 0;

    int after = 0;

    List<T> findAll();

    Optional<T> findById(K id, Connection connection);

    boolean delete(K id, Connection connection);

    int update(T entity, Connection connection);

    boolean save(T entity, Connection connection);

}
