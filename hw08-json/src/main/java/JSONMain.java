import MyJSON.MyJSON;
import com.google.gson.Gson;

/**
 * Created by Ilya Rogatkin, May 2020
 */

public class JSONMain {
    public static void main(String[] args) {
        ExampleClass ec = new ExampleClass();
        MyJSON myJSON = new MyJSON();
        myJSON.toJson(ec);

//        Gson gson = new Gson();
//        String json = gson.toJson(ec);
//        System.out.println(json);
    }
}
