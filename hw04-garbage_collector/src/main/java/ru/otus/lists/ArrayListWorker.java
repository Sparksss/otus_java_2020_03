/**
 * Created by Ilya Rogatkin, Apr 2020
 */

package ru.otus.lists;

import java.util.ArrayList;
import java.util.List;

public class ArrayListWorker {
    private List<Integer> numbers = new ArrayList<>();

    public void addRandomElements(int count) {
        for(int i = 0; i < count; i++) {
            Integer n = Math.toIntExact(Math.round(Math.random() * 100));
            numbers.add(n);
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
