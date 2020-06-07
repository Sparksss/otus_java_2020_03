
import MyJSON.MyJSON;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MyJsonTest {

    MyJSON myjson;
    Gson gson;

    @BeforeEach
    public void setUp() {
        myjson = new MyJSON();
        gson = new Gson();
    }

    @Test
    public void checkEqualsByteType() {
        assertEquals(myjson.toJson((byte)1), gson.toJson((byte)1));
    }

    @Test
    public void checkEqualsShortType() {
        assertEquals(myjson.toJson((short)1f), gson.toJson((short)1f));
    }

    @Test
    public void checkIntType() {
        assertEquals(myjson.toJson(1), gson.toJson(1));
    }

    @Test
    public void checkLongType() {
        assertEquals(myjson.toJson(1L), gson.toJson(1L));
    }

    @Test
    public void checkFloatType() {
        assertEquals(myjson.toJson(1f), gson.toJson(1f));
    }

    @Test
    public void checkDoubleType() {
        assertEquals(myjson.toJson(1d), gson.toJson(1d));
    }

    @Test
    public void checkString() {
        assertEquals(myjson.toJson("aaa"), gson.toJson("aaa"));
    }

    @Test
    public void checkCharType() {
        assertEquals(myjson.toJson('a'), gson.toJson('a'));
    }

    @Test
    public void checkArrayInt() {
        assertEquals(myjson.toJson(new int[]{1,2,3}), gson.toJson(new int[]{1,2,3}));
    }

    @Test
    public void checkList() {
        assertEquals(myjson.toJson(List.of(1, 2 ,3)), gson.toJson(List.of(1, 2 ,3)));
    }

    @Test
    public void checkCollectionsSigletonList() {
        assertEquals(myjson.toJson(Collections.singletonList(1)), gson.toJson(Collections.singletonList(1)));
    }

    @Test
    public void checkMyObjectEquals() {
        ExampleClass example = new ExampleClass(42,"John",123.5634D);
        String jsonExample = myjson.toJson(example);
        ExampleClass example2 = gson.fromJson(jsonExample, ExampleClass.class);
        assertEquals(example, example2);
    }

    @Test
    public void checkMyObjectStringJsonEquals() {
        ExampleClass example = new ExampleClass(42,"John",123.5634D);
        String jsonExample = myjson.toJson(example);
        String gsonExample = gson.toJson(example);
        assertEquals(jsonExample, gsonExample);
    }
}
