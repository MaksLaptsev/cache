package ru.clevertec.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.clevertec.entity.Person;
import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
class XmlParserUtilTest {
    private static XmlParserUtil parserUtil;
    private static Person person;

    @BeforeAll
    static void setUp() {
        parserUtil = XmlParserUtil.getInstance();
        person = Person.builder()
                .id(1)
                .name("Georg")
                .lastName("Last")
                .email("employee1@mydomain.by")
                .phoneNumber("+375221885412")
                .build();
    }

    @Test
    void getStringXmlFromObj() throws JsonProcessingException {
        assertThat(parserUtil.getStringXmlFromObj(person))
                .isEqualTo("<Person><id>1</id><name>Georg</name><lastName>Last</lastName><email>employee1@mydomain.by</email><phoneNumber>+375221885412</phoneNumber></Person>");
    }

    @Test
    void writeAsFileXmlFromObj() {
        parserUtil.writeAsFileXmlFromObj(person);
        assertThat(new File("Person.xml").exists()).isEqualTo(true);
        new File("Person.xml").delete();
    }

    @Test
    void setWriteFile() {
        XmlParserUtil xmlParserUtil = mock(XmlParserUtil.class);
        xmlParserUtil.setWriteFile(true);
        verify(xmlParserUtil).setWriteFile(true);

    }

    @Test
    void isWriteFile() {
        parserUtil.setWriteFile(false);
        assertThat(parserUtil.isWriteFile()).isEqualTo(false);
    }

}