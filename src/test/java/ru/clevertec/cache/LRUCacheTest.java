package ru.clevertec.cache;

import org.junit.jupiter.api.Test;
import ru.clevertec.cacheInterface.Cache;

import static org.assertj.core.api.Assertions.assertThat;

public class LRUCacheTest {

    @Test
    public void addSomeElement(){
        Cache<Integer, String> lruCache = new LRUCache<>(5);
        lruCache.add(1,"test1");
        lruCache.add(2,"test2");
        lruCache.add(3,"test3");
        assertThat(lruCache.get(1)).isEqualTo("test1");
        assertThat(lruCache.get(2)).isEqualTo("test2");
        assertThat(lruCache.get(3)).isEqualTo("test3");
    }

    @Test
    public void addDataToCacheThanCacheIsFull(){
        Cache<Integer, String> lruCache = new LRUCache<>(3);
        lruCache.add(1,"test1");
        lruCache.add(2,"test2");
        lruCache.add(3,"test3");
        lruCache.get(1);
        lruCache.get(2);
        lruCache.add(4,"test4");
        lruCache.add(5,"test5");

        assertThat(lruCache.containsKey(3)).isEqualTo(false);
    }

}
