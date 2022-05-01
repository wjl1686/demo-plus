package com.example.demo.common.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightRandom {
    public static final Map<String, Integer> WEIGHT_LIST = new HashMap<String, Integer>();

    static {
        // 权重之和为50
        WEIGHT_LIST.put("192.168.0.1", 1);
        WEIGHT_LIST.put("192.168.0.2", 8);
        WEIGHT_LIST.put("192.168.0.3", 3);
        WEIGHT_LIST.put("192.168.0.4", 6);
        WEIGHT_LIST.put("192.168.0.5", 5);
        WEIGHT_LIST.put("192.168.0.6", 5);
        WEIGHT_LIST.put("192.168.0.7", 4);
        WEIGHT_LIST.put("192.168.0.8", 7);
        WEIGHT_LIST.put("192.168.0.9", 2);
        WEIGHT_LIST.put("192.168.0.10", 9);
    }

    public static String getServer() {
        // 生成一个随机数作为list的下标值
        List<String> ips = new ArrayList<String>();
        for (String ip : WEIGHT_LIST.keySet()) {
            Integer weight = WEIGHT_LIST.get(ip);
            // 按权重进行复制
            for (int i = 0; i < weight; i++) {
                ips.add(ip);
            }
        }
        java.util.Random random = new java.util.Random();
        int randomPos = random.nextInt(ips.size());
        return ips.get(randomPos);
    }

    public static void main(String[] args) {
        // 连续调用10次
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < 10; i++) {
            System.out.println(getServer());
            System.out.println(random.nextInt(2));
        }
        }


}
