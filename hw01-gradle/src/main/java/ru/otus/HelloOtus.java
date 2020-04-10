package ru.otus;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.List;

public class HelloOtus {
    public static void main(String[] args) {
        Multimap<String, String> multimap = ArrayListMultimap.create();
        List<String> fruits = new ArrayList<>();
        fruits.add("banana");
        fruits.add("apple");
        fruits.add("orange");
        fruits.add("pineapple");

        List<String> vegetables = new ArrayList<>();
        vegetables.add("tomato");
        vegetables.add("broccoli");
        vegetables.add("carrots");
        vegetables.add("garlic");

        for(String fruit : fruits) {
            multimap.put("fruits", fruit);
        }
        for(String vegetable : vegetables) {
            multimap.put("vegetables", vegetable);
        }

        System.out.println(multimap.get("fruits"));
        System.out.println(multimap.get("vegetables"));
    }
}
