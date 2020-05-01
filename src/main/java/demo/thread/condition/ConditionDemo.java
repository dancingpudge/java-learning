package demo.thread.condition;

import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description //TODO
 * @Author liuhu
 * @Date 2020/5/1 0:06
 **/
public class ConditionDemo {

    static class thDemo implements Runnable {
        Lock lock;
        Condition condition;

        public thDemo(Lock lock, Condition condition) {
            this.lock = lock;
            this.condition = condition;
        }
        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName());
                condition.signal();
            } catch (Exception e) {
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Condition condition3 = lock.newCondition();
        for (int i = 0; i < 10; i++) {
            Thread thread1 = new Thread(new thDemo(lock, condition2));
            thread1.start();
            condition1.await();

            Thread thread2 = new Thread(new thDemo(lock, condition3));
            thread2.start();
            condition2.await();

            Thread thread3 = new Thread(new thDemo(lock, condition1));
            thread3.start();
            condition3.await();
        }
    }

}
