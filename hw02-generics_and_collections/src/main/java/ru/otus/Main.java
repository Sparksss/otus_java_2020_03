package ru.otus;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Check method copy and addAll
        List<Integer> diyArrayList = new DIYArrayList<>();
        Collections.addAll(diyArrayList, 1,2,3,4,5,6,7,8,9,10, 11,12,13,14,15,16,17,18,19,20);
        DIYArrayList<Integer> diyArrayList1 = new DIYArrayList<>();
        diyArrayList1.add(777);
        diyArrayList1.add(777);
        diyArrayList1.add(777);
        Collections.addAll(diyArrayList1, 21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42);
        Collections.copy(diyArrayList1, diyArrayList);
        for (int i = 0; i < diyArrayList1.size(); i++) {
            System.out.print(diyArrayList1.get(i) + " ");
        }
        System.out.println();

        // Check method sort
        DIYArrayList<Integer> diyArrayList2 = new DIYArrayList<>();
        Collections.addAll(diyArrayList2, 123,76,34,98,654,76,23,9,34,1789, 325, 9541, 124578, 9632, 541236, 9873, 7894, 852369, 741258, 9631452);
        Collections.sort(diyArrayList2,null);
        for(int i = 0; i < diyArrayList2.size(); i++) {
            System.out.print(diyArrayList2.get(i) + " ");
        }
        System.out.println();
    }

}
