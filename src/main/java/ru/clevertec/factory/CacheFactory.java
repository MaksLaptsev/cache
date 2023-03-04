package ru.clevertec.factory;

import ru.clevertec.cache.LRUCache;
import ru.clevertec.cacheInterface.Cache;

public class CacheFactory {

    public Cache cache(){

        return new LRUCache<>(5);
    }


}
