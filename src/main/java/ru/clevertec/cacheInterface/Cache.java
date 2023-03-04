package ru.clevertec.cacheInterface;

public interface Cache <K,V>{
    V get(K key);
    void add(K key,V value);
    boolean containsKey(K key);
    V remove(K key);
}
