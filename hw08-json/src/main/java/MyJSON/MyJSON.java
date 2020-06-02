/**
 * Created by Ilya Rogatkin, May 2020
 */

package MyJSON;

import javax.json.*;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.ArrayList;
//import javax.json.JsonArray;
//import javax.json.JsonNumber;

//import javax.json.JsonString;
//import javax.json.JsonStructure;
//import javax.json.JsonValue;

public class MyJSON {
    public String toJson(Object obj) {
        if(obj == null) return null;
        JsonValue jsonValue = createJsonValue(obj.getClass(), obj);
        return jsonValue != null ? jsonValue.toString() : null;
    }

    private JsonValue createJsonValue(Class<?> type, Object obj) {
        if(this.isPrimitive(type)) {
            return this.createPrimitiveValue(obj);
        } else if(type.isArray()) {
            return this.createJsonArray(obj);
        } else {
            try {
                return this.createJsonObject(obj);
            } catch (Exception err) {
                return JsonValue.NULL;
            }
        }
    }

    private boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive() ||
                Number.class.isAssignableFrom(clazz) ||
                Boolean.class.isAssignableFrom(clazz) ||
                Character.class.isAssignableFrom(clazz);
    }

    private JsonValue createPrimitiveValue(Object obj) {
        if(obj instanceof Number) {
            Number number = (Number) obj;
            if(obj instanceof Double || obj instanceof Float) {
                return Json.createValue(number.doubleValue());
            } else {
                return Json.createValue(number.longValue());
            }
        } else if(obj instanceof Boolean) {
            return obj.equals(true) ? JsonValue.TRUE : JsonValue.FALSE;
        }

        return Json.createValue(obj.toString());
    }

    private JsonValue createJsonObject(Object obj) throws IllegalAccessException {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        Field[] fields = obj.getClass().getDeclaredFields();

        for(Field field : fields) {
            field.setAccessible(true);
            int modifiers = field.getModifiers();
            Object objectValue = field.get(obj);
            if(objectValue != null && isNotSerialized(modifiers)) {
                jsonObjectBuilder.add(field.getName(), createJsonValue(field.getType(), objectValue));
            }
        }

        return jsonObjectBuilder.build();
    }

    private boolean isNotSerialized(int modifiers) {
        return !Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers);
    }

    private JsonArray createJsonArray(Object obj) {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        int size = Array.getLength(obj);
        for(int i = 0; i < size; i++) {
            Object val = Array.get(obj, i);
            jab.add(createJsonValue(val.getClass(), val));
        }
        return jab.build();
    }

    private static void readFromFile() {
        try (JsonReader jsonReader = Json.createReader(MyJSON.class.getClassLoader().getResourceAsStream("jsondata.json"))) {
            JsonStructure jsonFromTheFile = jsonReader.read();
            System.out.println("\n json from the file:");
            System.out.println(jsonFromTheFile);
            System.out.println("property:" + jsonFromTheFile.getValue("/firstName"));
        }
    }
}
