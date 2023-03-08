package ru.clevertec.cacheInterface;

public interface Cache <K,V>{
    /**
     * Getting value based on a key
     * @param key an object acting as a key
     * @return the desired object
     */
    V get(K key);

    /**
     * Add new data in cache
     * @param key an object acting as a key
     * @param value an object acting as a value
     */
    void add(K key,V value);

    /**
     * Checking if there is data in the cache
     * @param key an object acting as a key
     * @return true/false
     */
    boolean containsKey(K key);

    /**
     * Deleting data from the cache
     * @param key an object acting as a key
     * @return returns a deleted object(V) from the cache
     */
    V remove(K key);

    /**
     * Returns the current cache size
     * @return int size
     */
    int size();

    /**
     * Returns the type of algorithm on which the cache is implemented
     * @return enum cache type
     */
    Enum<?> getType();

}
