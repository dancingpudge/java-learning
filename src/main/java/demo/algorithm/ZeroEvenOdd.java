package demo.algorithm;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * @description: demo <br>
 * @date: 2021/2/23 11:56 下午 <br>
 * @author: liuhui <br>
 * @version: 0.0.1-SNAPSHOT <br>
 */
class ZeroEvenOdd {
    private int n;
    private volatile int i = 0;
    private volatile boolean b = true;
    private ReentrantLock lock = new ReentrantLock();
    Condition conditionZero = lock.newCondition();
    Condition conditionEven = lock.newCondition();
    Condition conditionOdd = lock.newCondition();

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (; i < n;) {
            lock.lock();
            try {
                if (b) {
                    printNumber.accept(0);
                    b = !b;
                    i++;
                    if (i % 2 == 0) {
                        conditionOdd.signal();
                    } else {
                        conditionEven.signal();
                    }
                    conditionZero.await();
                } else {
                    Thread.yield();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (; i <= n; ) {
            lock.lock();
            try {
                if (i % 2 == 1 && !b) {
                    printNumber.accept(i);
                    b = !b;
                    conditionZero.signal();
                    conditionEven.await();
                }else {
                    Thread.yield();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (; i <= n;) {
            lock.lock();
            try {
                if (i % 2 == 0 && !b) {
                    printNumber.accept(i);
                    b = !b;
                    conditionZero.signal();
                    conditionOdd.await();
                }else {
                    Thread.yield();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(8);
        new Thread(() -> {
            try {
                zeroEvenOdd.zero(System.out::print);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                zeroEvenOdd.even(System.out::print);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                zeroEvenOdd.odd(System.out::print);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


}