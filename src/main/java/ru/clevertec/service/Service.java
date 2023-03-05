package ru.clevertec.service;

import ru.clevertec.entity.Person;

public interface Service {
        Person GET (int id);
        void POST(Person person);
        void DELETE(int id);
        void PUT(Person person);
}
