package ru.clevertec.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoadEntityFromJson<T> {
    private final ObjectMapper mapper;

    public LoadEntityFromJson() {
        this.mapper = new ObjectMapper();
    }

    public <T> List<T> getListObject(String nameFile, Class<T> tClass) {
        URL path = Thread.currentThread().getContextClassLoader().getResource(nameFile);
        CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(ArrayList.class,tClass);

        List<T> list = null;
        try {
            list = mapper.readValue(path, collectionType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

}
