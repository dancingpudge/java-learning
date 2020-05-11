package demo.juc.condition;

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
                System.out.println(Thread.currentThread().getName() + "等待");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "被唤醒");
            } catch (Exception e) {
            } finally {
                lock.unlock();
            }
        }
    }

    static class thDemo2 implements Runnable {
        Lock lock;
        Condition condition;

        public thDemo2(Lock lock, Condition condition) {
            this.lock = lock;
            this.condition = condition;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + "执行完唤醒");
                condition.signal();
            } catch (Exception e) {
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(new thDemo(lock, condition), "th1").start();
        new Thread(new thDemo2(lock, condition), "th2").start();
    }

}
