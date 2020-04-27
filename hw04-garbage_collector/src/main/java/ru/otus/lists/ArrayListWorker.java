/**
 * Created by Ilya Rogatkin, Apr 2020
 */

package ru.otus.lists;

import java.util.ArrayList;
import java.util.List;

public class ArrayListWorker {
    private static List<Integer> numbers = new ArrayList<>();

    public void addRandomElements(int count) {
        Integer counter = 0;
        for(int i = 0; i < count; i++) {
            counter += Integer.valueOf(1);
            Integer c = 10;
            Integer a = 10;
            Integer f = a + c;
            numbers.add(counter);
        }
    }

    public void print() {
        for(int i = 0; i < numbers.size(); i++){
            System.out.print(numbers.get(i) + " ");
            if(i % 5 == 0) System.out.println();
        }
    }

    public void deleteElements(int count) {
        for(int i = 0; i < numbers.size(); i++){
            numbers.remove(i);
        }
    }

    public int getSizeList() {
        return numbers.size();
    }
}
