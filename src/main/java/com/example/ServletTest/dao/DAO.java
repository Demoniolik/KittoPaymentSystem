package com.example.ServletTest.dao;

import com.example.ServletTest.exception.DatabaseException;

import java.util.List;

public interface DAO<T> {
    T get(long id) throws DatabaseException;
    List<T> getAll() throws DatabaseException;
    T save(T t) throws DatabaseException;
    void update(T t, String[] params);
    void delete(T t);
}
