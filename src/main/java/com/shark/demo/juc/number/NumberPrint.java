package com.shark.demo.juc.number;


/**
 * @description: demo.juc <br>
 * @date: 2021/3/15 6:39 下午 <br>
 * @author: liuhui <br>
 * @version: 0.0.1-SNAPSHOT <br>
 */
public class NumberPrint {

    static volatile Integer i = 1;


    public static void main(String[] args) {
        Object lock = new Object();
        new Thread(() -> {
            while (i < 1000) {
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + ": " + i++);
                }
            }
        }, "t1").start();

        new Thread(() -> {
            while (i < 1000) {
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + ": " + i++);
                    lock.notifyAll();
                }
            }
        }, "t2").start();
    }
}
