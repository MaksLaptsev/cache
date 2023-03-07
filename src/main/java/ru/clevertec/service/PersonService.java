package ru.clevertec.service;

import ru.clevertec.dao.PersonDao;
import ru.clevertec.entity.Person;

public class PersonService implements Service{

    private final PersonDao personDao;

    public PersonService() {
        this.personDao = new PersonDao();
    }

    @Override
    public Person GET(int id) {
        return personDao.getById(id);
    }

    @Override
    public void POST(Person person) {
        personDao.save(person);
    }

    @Override
    public void DELETE(int id) {
        personDao.delete(id);
    }

    @Override
    public void PUT(Person person) {
        personDao.update(person);
    }
}
