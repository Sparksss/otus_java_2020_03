package ru.otus.reflection;

import ru.otus.ClassTest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Reflection {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Class<ClassTest> clazz = ClassTest.class;
//
//        System.out.println("Package Name: " + classString.getPackageName());
//
//        Constructor<?>[] constructors = classString.getConstructors();
//        System.out.println("--- constructors");
//        System.out.println(Arrays.toString(constructors));
//
        Method[] methods = clazz.getDeclaredMethods();
        System.out.println("--- methods");
        HashMap<String ,Annotation[]> annotations = new HashMap<>();

        Arrays.stream(methods).forEach(method -> System.out.println(method.getName()));
//
//        System.out.println("--- fields");
//        Field[] fields = classString.getDeclaredFields();
//        Arrays.stream(fields).forEach(field -> System.out.println(field.getName()));
//

        System.out.println("--- annotations:");
        Method annotatedMethod = clazz.getMethod("init");
        Annotation[] annotations = annotatedMethod.getDeclaredAnnotations();
        System.out.println(Arrays.toString(annotations));

//        Class<ClassTest> clazz = ClassTest.class;
//        Constructor<ClassTest> constructor = clazz.getDeclaredConstructor();
//        ClassTest object = constructor.newInstance();
//        System.out.println("value :" + object.getFirstField());
    }
}
