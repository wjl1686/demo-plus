package com.example.demo.test.study;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestStu {

    public static void main(String[] args) {
        //1、两个比较大的List<User>对象，User对象中，有id、name、idcard。
        // idcard相同，我们就认为User是同一个人，两个List中有每个list内部相同的user,也有两个list中都存在的user，
        // 写代码找出相同的user，并打印，要求效率高

        Map<String, User> hashMap = new HashMap<>();
        List<User> list1 = new ArrayList<>();
        List<User> list2 = new ArrayList<>();
        User user = new User();
        user.setIdcard("111");
        user.setName("张三");
        User user0 = new User();
        user0.setIdcard("111");
        user0.setName("张三0");
        User user1 = new User();
        user1.setIdcard("222");
        user1.setName("李斯");
        User user2 = new User();
        user2.setIdcard("222");
        user2.setName("mingzi333");
        User user3 = new User();
        user3.setIdcard("444");
        user3.setName("mingzi444");
        User user4 = new User();
        user4.setIdcard("555");
        user4.setName("mingzi555");
        list1.add(user);
        list1.add(user0);
        list1.add(user1);

        list2.add(user2);
        list2.add(user3);


        for (Integer i = 0; i < list1.size(); i++) {
            String idcard = list1.get(i).getIdcard();
            if (!hashMap.containsKey(idcard)) {
                hashMap.put(idcard, list1.get(i));
            } else {
                System.out.println(list1.get(i));
                System.out.println(hashMap.get(idcard));
            }
        }

        for (int i = 0; i < list2.size(); i++) {

            String idcard = list2.get(i).getIdcard();
            if (!hashMap.containsKey(idcard)) {
                hashMap.put(idcard, list2.get(i));
            } else {
                System.out.println(list2.get(i));
                System.out.println(hashMap.get(idcard));
            }


        }

    }
}
