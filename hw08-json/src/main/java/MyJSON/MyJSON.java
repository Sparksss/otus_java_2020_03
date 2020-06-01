/**
 * Created by Ilya Rogatkin, May 2020
 */

package MyJSON;

import javax.json.*;
import java.lang.reflect.*;
//import javax.json.JsonArray;
//import javax.json.JsonNumber;

//import javax.json.JsonString;
//import javax.json.JsonStructure;
//import javax.json.JsonValue;

public class MyJSON {
    public JsonObject toJson(Object obj) {
        if(obj == null) return null;
        JsonObjectBuilder builderJson = Json.createObjectBuilder();

        JsonValue jsonValue = createJsonValue(obj.getClass(), obj);
        return null;
    }

    public JsonObject toJsonExample() {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("firstName", "Duke")
                .add("age", 28)
                .add("streetAddress", "100 Internet Dr")
                .add("phoneNumbers",
                        Json.createArrayBuilder()
                                .add(Json.createObjectBuilder()
                                        .add("type", "home")
                                        .add("number", "222-222-2222")))
                .build();

        System.out.println("jsonObject:" + jsonObject + "\n");
        return jsonObject;
    }

    private static void readFromFile() {
        try (JsonReader jsonReader = Json.createReader(MyJSON.class.getClassLoader().getResourceAsStream("jsondata.json"))) {
            JsonStructure jsonFromTheFile = jsonReader.read();
            System.out.println("\n json from the file:");
            System.out.println(jsonFromTheFile);
            System.out.println("property:" + jsonFromTheFile.getValue("/firstName"));
        }
    }

    private JsonValue createJsonValue(Class<?> type, Object obj) {
      if(obj instanceof Number) {
          Number number = (Number) obj;
          if(obj instanceof Double || obj instanceof Float) {
              return Json.createValue(number.doubleValue());
          } else if(obj instanceof Integer) {
              return Json.createValue(number.intValue());
          }
      } else if(obj instanceof Boolean) {
          return obj.equals(true) ? JsonValue.TRUE : JsonValue.FALSE;
      }
      return null;
    }

}
