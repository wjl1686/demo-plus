package com.example.demo.test.study;

import java.io.File;
import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.List;

public class FileDemo3 {
    // 2、递归展示出电脑某一文件夹下所有文件的名称。
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        File file = new File("D:\\yonyou\\scan");
        List<String> objects = new ArrayList<>();
        showDir(file, objects);
        System.out.println(objects.size());
    }

    public static void showDir(File file, List<String> objects) {
        // 获取文件夹
        File[] files = file.listFiles();
        System.out.println("filesizs=" + files.length + "===" + "files=" + files);
        for (int i = 0; i < files.length; i++) {
            //是文件夹
            if (files[i].isDirectory()) {
                showDir(files[i], objects);
            } else {
                //是文件
                if (files[i].toString().endsWith(".java")) {
                    objects.add(files[i].toString());
                }
                System.out.println(files[i]);
            }
        }
    }

}