package ru.clevertec.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

public class XmlParserUtil {
    private final XmlMapper xmlMapper;
    private final String path;

    public XmlParserUtil() {
        this.xmlMapper = new XmlMapper();
        path = Paths.get("").toAbsolutePath()+"\\";
    }

    public String getStringXmlFromObj(Object o) throws JsonProcessingException {
        return xmlMapper.writeValueAsString(o);
    }
    public void writeAsFileXmlFromObj(Object o) throws IOException {
        String fileName = o.getClass().getSimpleName();
        xmlMapper.writeValue(new File(path+fileName),o);
    }
}
