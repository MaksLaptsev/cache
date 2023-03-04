import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.entity.Person;
import ru.clevertec.service.LoadEntityFromJson;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class main {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Person person = new Person(1,"Qwerty","Last","asdasd@fasd.com","+375221885412");
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        String json = objectMapper.writeValueAsString(Arrays.asList(person,person));
        System.out.println(json);

        LoadEntityFromJson<Person> loadP = new LoadEntityFromJson<>();
        List<Person> list = loadP.getListObject("JsonPersonsList.json",Person.class);


        System.out.println(list.size());
        Map<Integer, Person> personMap =  list.stream().collect(Collectors.toMap(Person::getId, Function.identity()));

    }
}
