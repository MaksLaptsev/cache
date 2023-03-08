package ru.clevertec.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.entity.Person;
import ru.clevertec.service.LoadEntityFromJson;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


class PersonDaoTest {
    private PersonDao personDao;
    private List<Person> list;
    @BeforeEach
    void setUp() {
        personDao = new PersonDao();
        list =new LoadEntityFromJson<Person>().getListObject("JsonPersonsList.json",Person.class);
    }

    @ParameterizedTest
    @MethodSource("argsGetById")
    void getById(Integer id, Person person) {
        assertThat(personDao.getById(id)).isEqualTo(person);
    }

    @Test
    void getAll() {
        assertThat(personDao.getAll()).isEqualTo(list);
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3})
    void deleteById(int id) {
        PersonDao personDao1 = mock(PersonDao.class);
        personDao1.deleteById(id);
        verify(personDao1).deleteById(id);
    }

    @ParameterizedTest
    @MethodSource("argsPersons")
    void delete(Person person) {
        PersonDao personDao1 = mock(PersonDao.class);
        personDao1.delete(person);
        verify(personDao1).delete(person);
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3})
    void testDelete(int id) {
        PersonDao personDao1 = mock(PersonDao.class);
        personDao1.deleteById(id);
        verify(personDao1).deleteById(id);
    }

    @ParameterizedTest
    @MethodSource("argsPersons")
    void update(Person person) {
        PersonDao personDao1 = mock(PersonDao.class);
        personDao1.update(person);
        verify(personDao1).update(person);
    }

    @ParameterizedTest
    @MethodSource("argsPersons")
    void save(Person person) {
        PersonDao personDao1 = mock(PersonDao.class);
        personDao1.save(person);
        verify(personDao1).save(person);
    }

    static Stream<Arguments> argsGetById(){
        return Stream.of(
                Arguments.of(
                        1,
                        Person.builder()
                                .id(1)
                                .name("Georg")
                                .lastName("Last")
                                .email("employee1@mydomain.by")
                                .phoneNumber("+375221885412")
                                .build()
                ),
                Arguments.of(
                        2,
                        Person.builder()
                                .id(2)
                                .name("Vasya")
                                .lastName("Biggest")
                                .email("Vasya@gmail.com")
                                .phoneNumber("+375221885412")
                                .build()
                )
        );
    }
    static Stream<Arguments> argsPersons(){
        return Stream.of(
                Arguments.of(
                        Person.builder()
                                .id(2)
                                .name("Vasya")
                                .lastName("Biggest")
                                .email("Vasya@gmail.com")
                                .phoneNumber("+375221885412")
                                .build()
                ),
                Arguments.of(
                        Person.builder()
                                .id(1)
                                .name("Georg")
                                .lastName("Last")
                                .email("employee1@mydomain.by")
                                .phoneNumber("+375221885412")
                                .build()
                )
        );
    }

}