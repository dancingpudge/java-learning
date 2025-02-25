package com.shark.demo.gc;

/**
 * @Description 查看GC日志
 * -XX:+PrintGCDateStamps -XX:+PrintGCDetails -Xloggc:./gclogs
 * @Author liuhu
 * @Date 2020/4/29 1:12
 **/
public class GCDemo {
    static class thd {
        public int i;

        public thd(int j) {
            this.i = j;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            thd thd = new thd(i);
            new Thread(() -> System.out.println(Thread.currentThread().getName() + ":" + thd.i), "th" + i).start();
        }
    }
}