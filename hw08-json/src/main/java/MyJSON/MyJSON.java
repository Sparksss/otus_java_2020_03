/**
 * Created by Ilya Rogatkin, May 2020
 */

package MyJSON;

import Checker.Check;
import Checker.Cheker;

import javax.json.*;
import java.lang.reflect.*;
import java.util.*;

public class MyJSON {

    private final Cheker check = new Check();

    public String toJson(Object obj) {
        if(obj == null) return null;
        JsonValue jsonValue = createJsonValue(obj.getClass(), obj);
        return jsonValue != null ? jsonValue.toString() : null;
    }

    private JsonValue createJsonValue(Class<?> type, Object obj) {
        boolean isNullValue = this.check.isNull(obj);

        if (check.isPrimitive(type)) {
            return this.createPrimitiveValue(obj);
        } else if (type.isArray()) {
            return isNullValue ? JsonArray.EMPTY_JSON_ARRAY : this.createJsonArray(obj);
        } else if(Collection.class.isAssignableFrom(type)) {
            Collection collection = (Collection) obj;
            return isNullValue ? JsonArray.EMPTY_JSON_ARRAY : this.createJsonArray(collection.toArray());
        } else if(Map.class.isAssignableFrom(type)) {
            return isNullValue ? JsonValue.EMPTY_JSON_OBJECT : this.createMapValue(obj);
        } else {
            try {
                return this.createJsonObject(obj);
            } catch (Exception err) {
                System.out.println(err.getMessage());
            }
        }
        return JsonValue.NULL;
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
            if(objectValue != null && !check.isSerializedField(modifiers)) {
                jsonObjectBuilder.add(field.getName(), createJsonValue(field.getType(), objectValue));
            }
        }
        return jsonObjectBuilder.build();
    }

    private JsonArray createJsonArray(Object obj) {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        int size = Array.getLength(obj);
        for(int i = 0; i < size; i++) {
            Object val = Array.get(obj, i);
            if(this.check.isNull(val)) {
                jab.add(JsonValue.NULL);
            } else {
                jab.add(createJsonValue(val.getClass(), val));
            }
        }
        return jab.build();
    }

    private JsonValue createMapValue(Object obj) {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        Map<?, ?> map = (Map<?,?>) obj;
        for(Map.Entry<?,?> pair : map.entrySet()) {
            String key = pair.getKey().toString();
            Object value = pair.getValue();
            objectBuilder.add(key, createJsonValue(value.getClass(), value));
        }
        return objectBuilder.build();
    }
}
