package demo.designpattern.singleton;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liuhu
 */
public class DclSingleton {

    static Lock lock = new ReentrantLock();
    private static volatile DclSingleton instance;

    public static DclSingleton getInstance() {
        if (null == instance) {
            lock.lock();
            try {
                if (null == instance) {
                    instance = new DclSingleton();
                }
            } catch (Exception e) {
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> System.out.println(
                    Thread.currentThread().getName() + "          " + DclSingleton.getInstance())
                    , "thName" + i
            ).start();
        }
    }
}
