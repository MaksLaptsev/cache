package ru.clevertec.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to load and deserialize objects from a file
 * @param <T> type of Objects
 */
public class LoadEntityFromJson<T> {
    static private final Logger logger = Logger.getLogger(LoadEntityFromJson.class);
    private final ObjectMapper mapper;

    public LoadEntityFromJson() {
        this.mapper = new ObjectMapper();
    }

    /**
     * Method for getting a list collection with the necessary objects
     * @param nameFile the name of the file where objects are stored as json
     * @param tClass class of object
     * @return - List with objects
     * @param <T> type of objects
     */
    public <T> List<T> getListObject(String nameFile, Class<T> tClass) {
        URL path = Thread.currentThread().getContextClassLoader().getResource(nameFile);
        CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(ArrayList.class,tClass);

        List<T> list = null;
        try {
            logger.debug("Initialization Map with entity from file: "+nameFile);
            list = mapper.readValue(path, collectionType);
        } catch (IOException e) {
            logger.error("Not found file with name: "+nameFile);
        }
        return list;
    }

}
