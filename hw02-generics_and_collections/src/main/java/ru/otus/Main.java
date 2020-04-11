package ru.otus;

import java.util.*;

public class Main {
    public static void main(String[] args) {
//        ArrayList<Integer> al = new ArrayList<>();
//        if(al.add(123)) {
//            System.out.println("Added success");
//        }
//        List<Integer> list = new ArrayList<>();
//        Collections.addAll(list, 1,12,34,556,54535);
//        for (Integer i : list)
//            System.out.println(i);
        DIYArrayList<Integer> diyArrayList = new DIYArrayList<>();
        Collections.addAll(diyArrayList, 1,2,3,4,5,6,7,8,9);

    }


}
