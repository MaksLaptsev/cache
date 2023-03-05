package ru.clevertec.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.clevertec.cache.LFUCache;
import ru.clevertec.cache.LRUCache;
import ru.clevertec.cacheInterface.Cache;
import java.io.IOException;
import java.net.URL;

public class CacheFactory<K,V> {
    private String initializeParamsFileName = "application.yml";
    public Cache<K,V> cacheInitialize() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        URL path = Thread.currentThread().getContextClassLoader().getResource(initializeParamsFileName);
        try {
            YamlParams yamlParams = mapper.readValue(path,YamlParams.class);
            return getCacheWithType(yamlParams.typeCache, yamlParams.cacheSize);
        }catch (IOException e){
            System.out.println("Error to read YamlParamsFile");
            System.out.println("Initialize with default size: 10 and Type: LRU");
        }
        return getCacheWithType("LRU",10);
    }
    public Cache<K,V> cacheInitialize(String filename) throws IOException {
        this.initializeParamsFileName = filename;
        return cacheInitialize();
    }

    private Cache<K,V> getCacheWithType(String type, int size){
        CacheType cacheType = CacheType.valueOf(type);
        return switch (cacheType) {
            case LFU -> new LFUCache<>(size);
            case LRU -> new LRUCache<>(size);
        };
    }
    @Getter
    @Setter
    @NoArgsConstructor
    static class YamlParams{
        private int cacheSize;
        private String typeCache;
    }


}
