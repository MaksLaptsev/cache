package ru.clevertec.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.log4j.Logger;
import ru.clevertec.cache.LFUCache;
import ru.clevertec.cache.LRUCache;
import ru.clevertec.cacheInterface.Cache;
import java.io.IOException;
import java.net.URL;

public class CacheFactory<K,V> {
    private static final Logger logger = Logger.getLogger(CacheFactory.class);
    private String initializeParamsFileName = "application.yml";
    public Cache<K,V> cacheInitialize() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        URL path = Thread.currentThread().getContextClassLoader().getResource(initializeParamsFileName);
        try {
            YamlParams yamlParams = mapper.readValue(path,YamlParams.class);
            logger.debug("Load params: type and size for cache from file: "+initializeParamsFileName);
            return getCacheWithType(yamlParams.typeCache, yamlParams.cacheSize);
        }catch (IOException e){
            logger.error("Error to read YamlParamsFile, fileName: "+initializeParamsFileName);
            logger.warn("Initialize with default size: 10 and Type: LRU");
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
