package com.softindex;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        IntLongHashMap myMap = new IntLongHashMap();
        Map<Integer, Long> standardMap = new HashMap<>();
        for (int i = 0; i < 1000000; i++) {
            int k = (int) (Math.random() * 10000000);
            long v = (long) (Math.random() * 10000000);
            myMap.put(k, v);
            standardMap.put(k, v);
        }

        standardMap.forEach((k, v) -> {
            if (v != myMap.get(k)) {
                System.out.println("does not match");
            }
        });
    }
}
