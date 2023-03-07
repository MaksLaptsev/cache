import ru.clevertec.entity.Person;
import ru.clevertec.service.PersonService;
import ru.clevertec.util.XmlParserUtil;

public class main {
    public static void main(String[] args){
        Person person = new Person(1,"Qwerty","Last","asdasd@fasd","+375221885412");
        Person person1 = new Person(1,"Qwerty","Last","asdasd@fasd.com","+375221885412");
        PersonService personService = new PersonService();


        personService.PUT(person);
        personService.PUT(person1);
        XmlParserUtil.getInstance().setWriteFile(true);
        personService.GET(2);

    }
}
