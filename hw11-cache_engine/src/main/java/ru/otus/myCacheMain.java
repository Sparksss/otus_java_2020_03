package ru.otus;

import java.util.Map;
import java.util.WeakHashMap;

public class myCacheMain {
    public static void main(String[] args) {
        Map<String, Object> week = new WeakHashMap<>();
        for(int i = 0; i < 10; i++) {
            week.put(" " + i, "lfmekfmlewmfe");
        }
//        System.gc();

        week.remove(" " + 9);
        for(int i = 0; i < week.size(); i++) {
            System.out.println(i);
            System.out.println(week.get(" " + i));
        }
    }
}
