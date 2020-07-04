package ru.otus.jdbc.mapper;

import ru.otus.annotations.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private Class<T> clazz;

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
        Field field = null;
        try {
            Field f = this.clazz.getField("id");
            Annotation[] annotations = f.getDeclaredAnnotations();
            for(Annotation annotation : annotations) {
                if(annotation instanceof Id) {
                   field = f;
                   break;
                }
            }
        } catch (NoSuchFieldException exp) {
            System.out.println(exp.getMessage());
            return null;
        }
        return field;
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
        boolean isIdField = false;
        for(Field field: this.getAllFields()) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for(Annotation annotation : annotations) {
                if(annotation instanceof Id) {
                    isIdField = true;
                    break;
                }
            }
            if(!isIdField) {
                fields.add(field);
            }
            isIdField = false;

        }
        return fields;
    }
}
