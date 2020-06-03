import MyJSON.MyJSON;
import com.google.gson.Gson;

/**
 * Created by Ilya Rogatkin, May 2020
 */

public class JSONMain {
    public static void main(String[] args) {
        ExampleClass obj = new ExampleClass(42,"John",123.5634D);
        MyJSON myJSON = new MyJSON();
        String myJSONString =  myJSON.toJson(obj);
        System.out.println(myJSONString);

        Gson gson = new Gson();
        String json = gson.toJson(obj);
        System.out.println(json);
        ExampleClass obj2 = gson.fromJson(json, ExampleClass.class);
        System.out.println(obj.equals(obj2));
    }
}
