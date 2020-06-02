import MyJSON.MyJSON;
import com.google.gson.Gson;

/**
 * Created by Ilya Rogatkin, May 2020
 */

public class JSONMain {
    public static void main(String[] args) {
        ExampleClass ec = new ExampleClass(42,123.5634D);
        MyJSON myJSON = new MyJSON();
        String s =  myJSON.toJson(ec);
        System.out.println(s);

//        Gson gson = new Gson();
//        String json = gson.toJson(ec);
//        System.out.println(json);
    }
}
