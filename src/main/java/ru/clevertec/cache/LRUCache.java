package ru.clevertec.cache;

import ru.clevertec.cacheInterface.Cache;
import ru.clevertec.factory.CacheType;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K,V> extends LinkedHashMap<K,V> implements Cache<K,V> {
    private final int maxSize;
    private final CacheType cacheType = CacheType.LRU;

    public LRUCache(int cacheSize){
        super(cacheSize,1f,true);
        this.maxSize = cacheSize;
    }

    @Override
    public void add(K key, V value) {
        super.put(key,value);
    }

    @Override
    public Enum<?> getType() {
        return cacheType;
    }

    @Override
    public V get(Object key){
        return super.get(key);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size()-1 >= maxSize;
    }

}
