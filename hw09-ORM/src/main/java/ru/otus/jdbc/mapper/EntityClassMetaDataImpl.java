package ru.otus.jdbc.mapper;

import ru.otus.annotations.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> clazz;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return this.clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor()  {
        try {
            return this.clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException exp) {
            System.out.println(exp.getMessage());
            return null;
        }
    }

    @Override
    public Field getIdField() {
        for(Field f: this.getAllFields()) {
            Annotation[] annotations = f.getDeclaredAnnotations();
            for(Annotation annotation : annotations) {
                if(annotation instanceof Id) {
                    return f;
                }
            }
        }
        return null;
    }

    @Override
    public List<Field> getAllFields() {
        List<Field> fields = new ArrayList<>();
        for(Field f : this.clazz.getDeclaredFields()) {
            f.setAccessible(true);
            fields.add(f);
        }
        return fields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        List<Field> fields = new ArrayList<>();
        for(Field field: this.getAllFields()) {
            Annotation idAnnotation = field.getAnnotation(Id.class);
            if(idAnnotation == null) {
                fields.add(field);
            }
        }
        return fields;
    }
}
