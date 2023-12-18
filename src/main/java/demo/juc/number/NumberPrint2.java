package demo.juc.number;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: demo.juc.number <br>
 * @date: 2021/3/30 11:50 下午 <br>
 * @author: liuhui <br>
 * @version: 0.0.1-SNAPSHOT <br>
 */
public class NumberPrint2 {
    static volatile int i = 0;

    public static void main(String[] args) {
        ReentrantLock rLock = new ReentrantLock();

        new Thread(() -> {
            while (i < 100) {
                rLock.lock();
                System.out.println(Thread.currentThread().getName() + i++);
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                rLock.unlock();
            }
        }).start();

        new Thread(() -> {
            while (i < 100) {
                rLock.lock();
                System.out.println(Thread.currentThread().getName() + i++);
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                rLock.unlock();
            }
        }).start();
    }


}
