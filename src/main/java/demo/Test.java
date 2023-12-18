package demo;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: demo <br>
 * @date: 2021/6/7 5:03 下午 <br>
 * @author: liuhui <br>
 * @version: 0.0.1-SNAPSHOT <br>
 */
public class Test {

    @Test
    public void test1() {
        final int TOTAL_NUM = 1000;
        AtomicInteger i = new AtomicInteger(1);
        new Thread(() -> {
            while (i.get() < TOTAL_NUM) {
                if (i.get() % 2 == 1) {
                    System.out.println(Thread.currentThread().getName() + i.get());
                    i.getAndIncrement();
                }
            }
        }, "奇数：").start();

        new Thread(() -> {
            while (i.get() < TOTAL_NUM) {
                if (i.get() % 2 == 0) {
                    System.out.println(Thread.currentThread().getName() + i.get());
                    i.getAndIncrement();
                }
            }
        }, "偶数：").start();
    }

    @Test
    public void test2() {
        new Thread(new test2p(true)).start();
        new Thread(new test2p(false)).start();
    }

    public static int i = 0;
    public static ReentrantLock reentrantLock = new ReentrantLock();

    static class test2p implements Runnable {

        boolean b;

        test2p(boolean b) {
            this.b = b;
        }

        @Override
        public void run() {
            while (i < 100) {
                reentrantLock.lock();
                try {
                    if ((b && i % 2 == 0) || (!b && i % 2 == 1)) {
                        System.out.println(i);
                    }
                    b = !b;
                    i++;
                } finally {
                    reentrantLock.unlock();
                }
            }
        }
    }

    public static Object lock = new Object();
    public static boolean isLocked = true;
    public static int N = 100;

    @Test
    public void test3() {
        new Thread(() -> {
            for (int i = 0; i < N; i = i + 2) {
                synchronized (lock) {
                    if (isLocked) {
                        System.out.println(i);
                        isLocked = !isLocked;
                        lock.notifyAll();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Thread.yield();
                    }
                }

            }
        }).start();
        new Thread(() -> {
            for (int i = 1; i < N; i = i + 2) {
                synchronized (lock) {
                    if (!isLocked) {
                        System.out.println(i);
                        isLocked = !isLocked;
                        lock.notifyAll();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Thread.yield();
                    }
                }

            }
        }).start();
    }

}
