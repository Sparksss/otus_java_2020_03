import java.util.*;

/**
 * Created by Ilya Rogatkin, May 2020
 */

public class ExampleClass {
    public int number;
    private String name;
    private double digit;
    private char[] ch = {'a','b','c','d'};
    private Map<Integer, String> list = new HashMap<>();
    private List<Integer> numbers = new ArrayList<>();

    public ExampleClass(int number, String name, double digit) {
        this.number = number;
        this.name = name;
        this.digit = digit;
        this.list.put(1, "Orange");
        this.list.put(2, "Green");
        this.list.put(3, "Yellow");

        this.numbers.add(42);
        this.numbers.add(42);
        this.numbers.add(44);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExampleClass that = (ExampleClass) o;
        return number == that.number &&
                Double.compare(that.digit, digit) == 0 &&
                Objects.equals(name, that.name) &&
                Arrays.equals(ch, that.ch) &&
                list.equals(that.list) &&
                numbers.equals(that.numbers);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(number, name, digit, list, numbers);
        result = 31 * result + Arrays.hashCode(ch);
        return result;
    }
}
