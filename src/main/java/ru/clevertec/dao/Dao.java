package ru.clevertec.dao;

import java.util.List;

public interface Dao <T>{
    T getById(int id);
    List<T> getAll();
    void deleteById(int id);
    void delete(T o);
    void update(T o);
    void save(T o);
}
