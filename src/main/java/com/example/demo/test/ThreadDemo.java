package com.example.demo.test;

public class ThreadDemo extends Thread {

    private String name;

    public ThreadDemo(String name) {

        this.name = name;
    }

    @Override
    public void run() {

        System.out.println(name);
    }


    public static void main(String[] args) {
     // new ThreadDemo("monkey老师").run();
       new ThreadDemo("monkey老师").start();
    }

}
