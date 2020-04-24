package ru.otus.reflection;

import ru.otus.ClassTest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;


public class Reflection {
    public static void main(String[] args) {
        startTest(ClassTest.class.getCanonicalName());
    }

    public static void startTest(String className) {

        System.out.println("-----------------------------------------------------------------");
        System.out.println("startTest");

        int total = 0;
        int failCount = 0;
        int successCount = 0;


        try {
            Class<?> clazz = Class.forName(className);
            Method[] methods = clazz.getDeclaredMethods();
            ArrayList<Method> beforeMethods = getAnnotatedMethods(methods, "Before");
            ArrayList<Method> testMethods = getAnnotatedMethods(methods, "Test");
            ArrayList<Method> afterMethods = getAnnotatedMethods(methods, "After");
            total = testMethods.size();


            for(Method test : testMethods) {
                try {
                    runTests(clazz, beforeMethods, test, afterMethods);
                    successCount++;
                } catch (Exception e) {
                    failCount++;
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("-----------------------------------------------------------------");
        System.out.println("Total tests : " + total);
        System.out.println("Successful tests : " + successCount);
        System.out.println("Failed tests : " + failCount);
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Test Finished");
        System.out.println("-----------------------------------------------------------------");
    }

    private static ArrayList<Method> getAnnotatedMethods(Method[] methods, String findAnnotation){
        ArrayList<Method> annotatedMethods = new ArrayList<>();
        for(Method method : methods ){
            method.setAccessible(true);
            Annotation[] annotations = method.getDeclaredAnnotations();
            for(Annotation annotation : annotations ) {
                String annotationName = annotation.toString();
                if(annotationName.contains(findAnnotation)) {
                    annotatedMethods.add(method);
                }
            }
        }
        return annotatedMethods;
    }

    private static <T> T getInstance(Class<T> type) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<T> constr = type.getDeclaredConstructor();
        constr.setAccessible(true);
        return constr.newInstance();
    }

    private static <T> void runTests(Class<T> clazz, ArrayList<Method> beforeMethods, Method test, ArrayList<Method> afterMethods) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Object testObject = getInstance(clazz);
        for(Method before : beforeMethods) {
            before.invoke(testObject);
        }

        test.invoke(testObject);

        for(Method after : afterMethods) {
            after.invoke(testObject);
        }
    }
}
