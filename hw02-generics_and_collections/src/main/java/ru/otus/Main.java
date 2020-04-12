package ru.otus;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Test ArrayList Generics
//        ArrayList<Integer> al = new ArrayList<>();
//        if(al.add(123)) {
//            System.out.println("Added success");
//        }
//        List<Integer> list = new ArrayList<>();
//        Collections.addAll(list, 1,12,34,556,54535);
//        for (Integer i : list)
//            System.out.println(i);
        // Check method COPY
//        List<Integer> diyArrayList = new DIYArrayList<>();
//        Collections.addAll(diyArrayList, 1,2,3,4,5,6,7,8,9);
//        DIYArrayList<Integer> diyArrayList1 = new DIYArrayList<>();
//        diyArrayList1.add(777);
//        diyArrayList1.add(777);
//        diyArrayList1.add(777);
//        Collections.addAll(diyArrayList1, 11,12,13,14,15,16,17,18,19,20);
//        Collections.copy(diyArrayList1, diyArrayList);
//        for (int i = 0; i < diyArrayList1.size(); i++) {
//            System.out.println(diyArrayList1.get(i));
//        }
        // for training ArrayList
//        List<Integer> numbers = new ArrayList<>();
//        Collections.addAll(numbers, 1,2,3,4,5);
//        List<Integer> numbers1 = new ArrayList<>();
//        Collections.addAll(numbers1, 9,8,7,6,5);
//        Collections.copy(numbers1, numbers);
//        System.out.println("dest List");
//        System.out.println(numbers1);

        DIYArrayList<Integer> diyArrayList2 = new DIYArrayList<>();
        Collections.addAll(diyArrayList2, 4,8,2,0,7,3,9);
        Collections.sort(diyArrayList2,null);
        for(int i = 0; i < diyArrayList2.size(); i++) {
            System.out.println(diyArrayList2.get(i));
        }
    }

}
