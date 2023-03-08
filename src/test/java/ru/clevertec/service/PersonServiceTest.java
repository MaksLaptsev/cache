package ru.clevertec.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.dao.PersonDao;
import ru.clevertec.entity.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)

class PersonServiceTest {

    @Mock
    private PersonDao personDao;
    @InjectMocks
    private PersonService personService;
    private Person person;
    @BeforeEach
    void setResources() {
        person = Person.builder()
                .id(1)
                .name("Name")
                .lastName("Lastname")
                .phoneNumber("+37521257321")
                .email("dasgasd2@gmail.com")
                .build();
        lenient().doReturn(person).when(personDao).getById(anyInt());
    }

    @Test
    void GET() {
        assertThat(personService.GET(anyInt())).isEqualTo(person);
    }

    @Test
    void POST() {
        personService.POST(person);
        verify(personDao).save(person);
    }

    @Test
    void DELETE() {
        personService.DELETE(anyInt());
        verify(personDao).delete(anyInt());
    }

    @Test
    void PUT() {
        personService.PUT(person);
        verify(personDao).update(person);
    }

}