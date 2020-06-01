/**
 * Created by Ilya Rogatkin, May 2020
 */

package MyJSON;

import javax.json.Json;
//import javax.json.JsonArray;
//import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;
//import javax.json.JsonString;
//import javax.json.JsonStructure;
//import javax.json.JsonValue;

public class MyJSON {
    public JsonObject toJson(Object obj) {

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
}
