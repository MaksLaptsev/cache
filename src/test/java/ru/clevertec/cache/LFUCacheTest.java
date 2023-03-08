package ru.clevertec.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.factory.CacheType;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(lfuCache.containsKey(1)).isEqualTo(true);
    }

    @Test
    void get() {
        assertThat("2").isEqualTo(lfuCache.get(2));
    }

    @Test
    void add() {
        lfuCache.add(6,"6");
        assertThat(lfuCache.containsKey(6)).isEqualTo(true);
    }

    @Test
    void remove() {
        lfuCache.remove(5);
        assertThat(lfuCache.containsKey(5)).isEqualTo(false);
    }

    @Test
    void size() {
        assertThat(lfuCache.size()).isEqualTo(5);
    }

    @Test
    void getType() {
        assertThat(lfuCache.getType()).isEqualTo(CacheType.LFU);
    }

}