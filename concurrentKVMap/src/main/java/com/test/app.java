package com.test;

import com.test.api.KVStore;
import com.test.impl.KVStoreImpl;

public class app {
    public static void main(String[] args) {
        KVStore<Integer,Integer> testMap = new KVStoreImpl<Integer, Integer>();

        System.out.println("size = "+testMap.size());

        testMap.put(1,10);
        testMap.put(2,20);
        testMap.put(3,30);

        System.out.println(testMap.get(1));
        System.out.println(testMap.get(2));
        System.out.println(testMap.get(3));

        testMap.put(1,40);

        testMap.delete(2);

        System.out.println(testMap.get(1));
        System.out.println(testMap.get(2));
        System.out.println(testMap.get(3));
    }
}
