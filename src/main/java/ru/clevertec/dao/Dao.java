package ru.clevertec.dao;

import java.util.List;

/**
 * typical crud operations
 * @param <T> the object that is being worked with
 */
public interface Dao <T>{
    /**
     * Getting an object based on an id
     * @param id object id
     * @return desired object
     */
    T getById(int id);

    /**
     * Getting all objects from bd
     * @return list desired objects
     */
    List<T> getAll();

    /**
     * Delete an object based on an id
     * @param id object id
     */
    void deleteById(int id);

    /**
     * Deleting a passed object from bd
     * @param o The object to be deleted
     */
    void delete(T o);

    /**
     * Delete an object based on an id
     * @param id object id
     */
    void delete(int id);

    /**
     * Updating an object in bd
     * @param o Updated object
     */
    void update(T o);

    /**
     * Saving an object in bd
     * @param o The object to be saved in the database
     */
    void save(T o);
}
