import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.entity.Person;
import ru.clevertec.service.PersonService;
import java.io.IOException;



public class main {
    public static void main(String[] args) throws IOException, NoSuchFieldException {
        ObjectMapper objectMapper = new ObjectMapper();
        Person person = new Person(1,"Qwerty","Last","asdasd@fasd","+375221885412");
        Person person1 = new Person(1,"Qwerty","Last","asdasd@fasd.com","+375221885412");
        PersonService personService = new PersonService();
//        System.out.println(personService.GET(2));

        personService.PUT(person);
        personService.PUT(person1);

        personService.GET(2);
        personService.GET(6);
        personService.GET(3);
        personService.GET(8);
        personService.GET(2);
        personService.DELETE(4);
        personService.GET(6);
        personService.DELETE(2);
        personService.GET(3);





    }
}
