package ru.clevertec.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import ru.clevertec.entity.Person;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoadEntityFromJson<T> {
    private ObjectMapper mapper;

    public LoadEntityFromJson() {
        this.mapper = new ObjectMapper();
    }

    public <T> List<T> getListObject(String nameFile, Class<T> tClass) throws IOException {
        ClassLoader classLoader =ClassLoader.getSystemClassLoader();
        URL path = Thread.currentThread().getContextClassLoader().getResource(nameFile);
        CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(ArrayList.class,tClass);
        List<T> list = mapper.readValue(path, collectionType);
        return list;
    }

}
