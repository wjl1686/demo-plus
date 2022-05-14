package com.example.demo.test.study;

import java.util.concurrent.Semaphore;

public class Test02 {
    //共享数据
    private static int n = 1;

    //创建三个信号量，其中first_to_second初始值为1，其它两个为0
    private Semaphore first_to_second = new Semaphore(1);
    private Semaphore second_to_third = new Semaphore(0);
    private Semaphore third_to_first = new Semaphore(0);

    public void first() throws InterruptedException {
        //初始值为1，获得许可，开始执行 (若值为0，则线程进入阻塞状态)
        first_to_second.acquire();

        while (n <= 100) {
            System.out.println(Thread.currentThread().getName() + ": " + n);
            n++;
        }

        //释放许可，second_to_third + 1
        second_to_third.release();

    }

    public void second() throws InterruptedException {
        //值为1，获得许可，开始执行 (若值为0，则线程进入阻塞状态)
        second_to_third.acquire();

        while (n <= 100) {
            System.out.println(Thread.currentThread().getName() + ": " + n);
            n++;
        }

        //释放许可，third_to_first + 1
        third_to_first.release();
    }

    public void third() throws InterruptedException {
        //值为1，获得许可，开始执行 (若值为0，则线程进入阻塞状态)
        third_to_first.acquire();

        while (n <= 100) {
            System.out.println(Thread.currentThread().getName() + ": " + n);
            n++;
        }

        //释放许可，first_to_second + 1
        first_to_second.release();
    }

    public static void main(String[] args) {

        Test02 print100 = new Test02();

        //线程1
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    print100.first();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "线程1").start();

        //线程2
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    print100.second();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "线程2").start();

        //线程3
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    print100.third();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "线程3").start();
    }
}
