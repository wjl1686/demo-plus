package com.example.demo.test.study;

class Print100 {
    // 共享锁
    private static Object lock = new Object();
    // 要打印的资源
    private static int n = 1;
    // 控制资源的顺序
    private static int flag = 1;

    public void first() {
        while (n <= 100) {
            synchronized (lock) {
                while (flag != 1) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (n < 101) {
                    System.out.println(Thread.currentThread().getName() + ":  " + n++);
                } else {
                    break;
                }
                flag = 2;
                lock.notifyAll();
            }
        }
    }

    public void second() {
        while (n <= 100) {
            synchronized (lock) {
                while (flag != 2) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (n < 101) {
                    System.out.println(Thread.currentThread().getName() + ":  " + n++);
                } else {
                    break;
                }
                flag = 3;
                lock.notifyAll();

            }
        }
    }

    public void third() {
        while (n <= 100) {
            synchronized (lock) {
                while (flag != 3) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (n < 101) {
                    System.out.println(Thread.currentThread().getName() + ":  " + n++);
                } else {
                    break;
                }
                flag = 1;
                lock.notifyAll();

            }
        }
    }
}

public class Test {

    public static void main(String[] args) {

        Print100 print100 = new Print100();

        new Thread(new Runnable() {
            @Override
            public void run() {
                print100.first();
            }
        }, "线程1").start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                print100.second();
            }
        }, "线程2").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                print100.third();
            }
        }, "线程3").start();
    }
}
