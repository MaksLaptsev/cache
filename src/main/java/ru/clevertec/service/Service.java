package ru.clevertec.service;

import ru.clevertec.entity.Person;

/**
 * typical crud operations
 */
public interface Service<T> {
        /**
         * Getting an object based on an id
         * @param id object id
         * @return desired object
         */
        T GET (int id);

        /**
         * Saving an object in bd
         * @param o The object to be saved in the database
         */
        void POST(T o);

        /**
         * Delete an object based on an id
         * @param id object id
         */
        void DELETE(int id);

        /**
         * Updating an object in bd
         * @param t The object to be updated in the database
         */
        void PUT(T t);
}
