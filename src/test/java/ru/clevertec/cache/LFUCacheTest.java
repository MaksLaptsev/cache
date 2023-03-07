package ru.clevertec.cache;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.factory.CacheType;

class LFUCacheTest {
    private LFUCache<Integer, String> lfuCache;
    @BeforeEach
    void setUp() {
        lfuCache = new LFUCache<>(5);
        lfuCache.add(1,"1");
        lfuCache.add(2,"2");
        lfuCache.add(3,"3");
        lfuCache.add(4,"4");
        lfuCache.add(5,"5");
    }

    @Test
    void containsKey() {
        Assertions.assertThat(lfuCache.containsKey(1)).isEqualTo(true);
    }

    @Test
    void get() {
        Assertions.assertThat("2").isEqualTo(lfuCache.get(2));
    }

    @Test
    void add() {
        lfuCache.add(6,"6");
        Assertions.assertThat(lfuCache.containsKey(6)).isEqualTo(true);
    }

    @Test
    void remove() {
        lfuCache.remove(5);
        Assertions.assertThat(lfuCache.containsKey(5)).isEqualTo(false);
    }

    @Test
    void size() {
        Assertions.assertThat(lfuCache.size()).isEqualTo(5);
    }

    @Test
    void getType() {
        Assertions.assertThat(lfuCache.getType()).isEqualTo(CacheType.LFU);
    }
}