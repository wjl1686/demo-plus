package com.example.demo.common.interceptor;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public class Test {
    static final int HASH_BITS = 0x7fffffff;

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        ConcurrentHashMap<Object, Object> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put(":1", 1);

        // HashMap<Object, Object> objectObjectHashMap8 = new HashMap<>(2);
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("1", 1);

        System.out.println(HASH_BITS);
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {

        } finally {
            semaphore.release();
        }
        System.out.println(new Date());

    }
}
