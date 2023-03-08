package ru.clevertec.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import ru.clevertec.annotation.CorrectEmail;
import ru.clevertec.annotation.CrudAnnotation;
import ru.clevertec.annotation.MethodType;
import ru.clevertec.cacheInterface.Cache;
import ru.clevertec.entity.Person;
import ru.clevertec.factory.CacheFactory;
import ru.clevertec.util.XmlParserUtil;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used as an aspect to synchronize the operation of the dao with the cache
 */
@Aspect
public class DaoPersonCacheAspect {
    static private final Logger logger = Logger.getLogger(DaoPersonCacheAspect.class);
    private final Cache<Integer, Person> cache;
    private final XmlParserUtil xmlParserUtil;


    public DaoPersonCacheAspect() {
        this.cache = new CacheFactory<Integer,Person>().cacheInitialize();
        xmlParserUtil = XmlParserUtil.getInstance();
        logger.info("Initialize XmlParseUtil and Cache with type: "+cache.getType());
    }

    @Pointcut("execution(* ru.clevertec.dao.PersonDao.*(..))")
    public void personDaoMethod(){}

    @Pointcut("@annotation(ru.clevertec.annotation.CrudAnnotation)")
    public void personDaoPUT(){}

    /**
     * In this method, synchronization occurs between the methods of the dao class and the cache
     * @param joinPoint the executable method that was intercepted by the aspect
     * @return returns the desired object
     * @throws Throwable
     */
    @Around("personDaoMethod() && personDaoPUT()")
    public Object personDaoPUT(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodType methodType = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(CrudAnnotation.class).type();
        /**
         * determining the methods required for the call based on the annotations of the source method from dao
         */
        boolean b = switch (methodType){
            case PUT -> put(joinPoint);
            case GET -> false;
            case DELETE -> delete(joinPoint);
            case POST -> post(joinPoint);
        };

        if (!methodType.equals(MethodType.GET) && b){
            return joinPoint.proceed();
        }else if (methodType.equals(MethodType.GET)){
            return get(joinPoint);
        } else {
            return null;
        }
    }


    private boolean put(ProceedingJoinPoint joinPoint){
        Class<?>[] classes = ((MethodSignature)joinPoint.getSignature()).getMethod().getParameterTypes();
        Person person = (Person) joinPoint.getArgs()[0];
        if (checkCorrectPerson(person)){
            cache.remove(person.getId());
            cache.add(person.getId(),person);
            logger.info("Person update and save in BD and CACHE - id: "+person.getId());
            return true;
        }else {
            logger.warn("UnCorrect EMAIL - "+person.getEmail());
            logger.warn("Person doesnt save");
            return false;
        }

    }

    private boolean delete(ProceedingJoinPoint joinPoint){
        Class<?>[] classes = ((MethodSignature)joinPoint.getSignature()).getMethod().getParameterTypes();
        if (classes[0].equals(int.class)){
            cache.remove((Integer) joinPoint.getArgs()[0]);
            System.out.println("Person delete if: "+joinPoint.getArgs()[0]);
            return true;
        } else if (classes[0].equals(Person.class)) {
            Person person = (Person) joinPoint.getArgs()[0];
            cache.remove(person.getId());
            System.out.println("Person delete id: "+person.getId());
            return true;
        }else return false;

    }

    private boolean post(ProceedingJoinPoint joinPoint){
        Class<?>[] classes = ((MethodSignature)joinPoint.getSignature()).getMethod().getParameterTypes();
        Person person = (Person) joinPoint.getArgs()[0];
        if (checkCorrectPerson(person)){
            cache.remove(person.getId());
            cache.add(person.getId(),person);
            System.out.println("Person save id: "+person.getId());
            return true;
        }else {
            logger.warn("UnCorrect EMAIL - "+person.getEmail());
            logger.warn("Person doesnt save");
            return false;
        }
    }

    private Person get(ProceedingJoinPoint joinPoint) throws Throwable {
        Class<?>[] classes = ((MethodSignature)joinPoint.getSignature()).getMethod().getParameterTypes();
        if (classes[0].equals(int.class)){
            if (cache.containsKey((Integer) joinPoint.getArgs()[0])){
                logger.info("Get Person id: "+joinPoint.getArgs()[0]+" from CACHE");
                Person person = cache.get((Integer) joinPoint.getArgs()[0]);
                isWriteToFileXml(person);
                return person;
            }else {
                Person person = (Person) joinPoint.proceed();
                cache.add(person.getId(),person);
                logger.info("Get Person id: "+person.getId()+" from BD and add to CACHE");
                isWriteToFileXml(person);
                return person;
            }
        } else if (classes[0].equals(Person.class)) {
            Person person = (Person) joinPoint.getArgs()[0];
            if(cache.containsKey(person.getId())){
                logger.info("Get Person id: "+person.getId()+" from CACHE");
                Person person1 = cache.get(person.getId());
                isWriteToFileXml(person1);
                return person1;
            }else {
                Person person1 = (Person) joinPoint.proceed();
                cache.add(person1.getId(),person1);
                logger.info("Get Person id: "+person1.getId()+" from BD and add to CACHE");
                isWriteToFileXml(person1);
                return person1;
            }
        }else return null;
    }


    private boolean checkCorrectPerson(Person person) {
        Field[] fields = person.getClass().getDeclaredFields();
        for (Field f : fields){
            f.setAccessible(true);
            if(f.isAnnotationPresent(CorrectEmail.class)){
                CorrectEmail correctEmail = f.getAnnotation(CorrectEmail.class);
                Pattern pattern = Pattern.compile(correctEmail.emailRegex());
                try {
                    Matcher matcher = pattern.matcher((CharSequence) f.get(person));
                    return matcher.find();
                }catch (IllegalAccessException ignored){}
            }
        }
        return false;
    }

    private void isWriteToFileXml(Person person){
        if (xmlParserUtil.isWriteFile()){
            xmlParserUtil.writeAsFileXmlFromObj(person);
            logger.info("Person with id: "+person.getId()+ " written to a file");
        }
    }


}
