package com.example.ServletTest.dao;

import java.util.List;

public interface DAO<T> {
    T get(long id);
    List<T> getAll();
    void save();
    void update(T t, String[] params);
    void delete(T t);
}
