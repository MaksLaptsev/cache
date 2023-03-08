package ru.clevertec.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Singleton class
 *      used to write an object to a file
 */
public class XmlParserUtil {
    private final Logger logger = Logger.getLogger(XmlParserUtil.class);
    private static volatile XmlParserUtil INSTANCE;
    private final XmlMapper xmlMapper;
    private final String path;
    private boolean writeFile;

    private XmlParserUtil() {
        this.xmlMapper = new XmlMapper();
        path = Paths.get("").toAbsolutePath()+"\\";
        writeFile = false;
    }

    /**
     * Getting a reference to a class object if it does not exist,
     *      creating an object and returning a reference to it
     * @return an object of this class
     */
    public static XmlParserUtil getInstance(){
        if (INSTANCE == null){
            synchronized (XmlParserUtil.class){
                if(INSTANCE==null){
                    INSTANCE = new XmlParserUtil();
                }
            }
        }
        return INSTANCE;
    }

    public String getStringXmlFromObj(Object o) throws JsonProcessingException {
        return xmlMapper.writeValueAsString(o);
    }

    /**
     * Writing an object to a file
     * @param o the object to be recorded
     */
    public void writeAsFileXmlFromObj(Object o) {
        String fileName = o.getClass().getSimpleName();
        try {
            xmlMapper.writeValue(new File(path + fileName + "." + "xml"),o);
            logger.info("The path to write "+path);
        }catch (IOException e){
            logger.error(e.getMessage());
            logger.warn("Object doesnt write in file");
        }
    }

    /**
     * Setting a flag indicating whether to write an object to a file
     * @param b true if yes, false if not
     */
    public void setWriteFile(boolean b){
        this.writeFile = b;
    }

    /**
     * Checking whether an object needs to be written to a file
     * @return true if yes, false if not
     */
    public boolean isWriteFile(){
        return this.writeFile;
    }

}
