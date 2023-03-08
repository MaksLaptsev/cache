package ru.clevertec.dao;

import ru.clevertec.annotation.CrudAnnotation;
import ru.clevertec.annotation.MethodType;
import ru.clevertec.entity.Person;
import ru.clevertec.service.LoadEntityFromJson;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PersonDao implements Dao<Person>{
    private Map<Integer,Person> personMap;
    public PersonDao() {
        personMap = new LoadEntityFromJson<Person>().getListObject("JsonPersonsList.json",Person.class).stream()
                .collect(Collectors.toMap(Person::getId, Function.identity()));
    }

    @Override
    @CrudAnnotation(type = MethodType.GET)
    public Person getById(int id) {
        return personMap.get(id);
    }

    @Override
    public List<Person> getAll() {
        return personMap.values().stream().toList();
    }

    @Override
    @CrudAnnotation(type = MethodType.DELETE)
    public void deleteById(int id) {
        personMap.remove(id);
    }

    @Override
    @CrudAnnotation(type = MethodType.DELETE)
    public void delete(Person o) {
        personMap.remove(o.getId(),o);
    }
    @Override
    @CrudAnnotation(type = MethodType.DELETE)
    public void delete(int id) {
        personMap.remove(id);
    }

    @Override
    @CrudAnnotation(type = MethodType.PUT)
    public void update(Person o) {
        personMap.remove(o.getId());
        personMap.put(o.getId(),o);
    }

    @Override
    @CrudAnnotation(type = MethodType.POST)
    public void save(Person o) {
        personMap.put(o.getId(),o);
    }

}
