package ru.otus.jdbc.mapper;

import ru.otus.annotations.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private Class<T> clazz;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return this.clazz.getName();
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
        return Arrays.asList(this.clazz.getFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        List<Field> fields = new ArrayList<>();
        for(Field field: this.clazz.getDeclaredFields()) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            boolean IsFieldHaveIdAnnotation = false;
            for(Annotation annotation : annotations) {
                if(annotation instanceof Id) {
                    IsFieldHaveIdAnnotation = true;
                    break;
                }
            }
            if(!IsFieldHaveIdAnnotation) {
                fields.add(field);
            }
        }
        return fields;
    }
}
