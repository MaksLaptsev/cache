package ru.clevertec.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Aspect
public class DaoPersonCacheAspect {
    private final Cache<Integer, Person> cache;

    public DaoPersonCacheAspect() {
        this.cache = new CacheFactory<Integer,Person>().cacheInitialize();
    }

    @Pointcut("execution(* ru.clevertec.dao.PersonDao.*(..))")
    public void personDaoMethod(){}

    @Pointcut("@annotation(ru.clevertec.annotation.CrudAnnotation)")
    public void personDaoPUT(){}

    @Around("personDaoMethod() && personDaoPUT()")
    public Object personDaoPUT(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("CACHE SIZE IS: "+cache.size());
        MethodType methodType = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(CrudAnnotation.class).type();

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
            System.out.println("Person update and save - id: "+person.getId());
            return true;
        }else {
            System.out.println("UnCorrect EMAIL ");
            System.out.println("Person doesnt save");
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
            System.out.println("UnCorrect EMAIL ");
            System.out.println("Person doesnt save");
            return false;
        }
    }

    private Person get(ProceedingJoinPoint joinPoint) throws Throwable {
        Class<?>[] classes = ((MethodSignature)joinPoint.getSignature()).getMethod().getParameterTypes();
        if (classes[0].equals(int.class)){
            if (cache.containsKey((Integer) joinPoint.getArgs()[0])){
                System.out.println("Get Person id: "+joinPoint.getArgs()[0]+" from CACHE");
                return cache.get((Integer) joinPoint.getArgs()[0]);
            }else {
                Person person = (Person) joinPoint.proceed();
                cache.add(person.getId(),person);
                System.out.println("Get Person id: "+person.getId()+" from BD and add to CACHE");
                return person;
            }
        } else if (classes[0].equals(Person.class)) {
            Person person = (Person) joinPoint.getArgs()[0];
            if(cache.containsKey(person.getId())){
                System.out.println("Get Person id: "+person.getId()+" from CACHE");
                return cache.get(person.getId());
            }else {
                Person person1 = (Person) joinPoint.proceed();
                cache.add(person1.getId(),person1);
                System.out.println("Get Person id: "+person1.getId()+" from BD and add to CACHE");
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


}
