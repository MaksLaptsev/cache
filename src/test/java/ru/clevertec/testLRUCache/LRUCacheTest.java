package ru.clevertec.testLRUCache;

import org.junit.jupiter.api.Test;
import ru.clevertec.cache.LRUCache;
import org.assertj.core.api.Assertions;
import ru.clevertec.cacheInterface.Cache;

public class LRUCacheTest {

    @Test
    public void addSomeElement(){
        Cache<Integer, String> lruCache = new LRUCache<>(5);
        lruCache.add(1,"test1");
        lruCache.add(2,"test2");
        lruCache.add(3,"test3");
        Assertions.assertThat(lruCache.get(1)).isEqualTo("test1");
        Assertions.assertThat(lruCache.get(2)).isEqualTo("test2");
        Assertions.assertThat(lruCache.get(3)).isEqualTo("test3");
    }

    @Test
    public void addDataToCacheThanCacheIsFull(){
        Cache<Integer, String> lruCache = new LRUCache<>(3);
        lruCache.add(1,"test1");
        lruCache.add(2,"test2");
        lruCache.add(3,"test3");
        lruCache.add(4,"test4");
        lruCache.get(1);
        Assertions.assertThat(lruCache.containsKey(2)).isEqualTo(false);
    }
}
