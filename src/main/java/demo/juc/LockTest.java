package demo.juc;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @description: TODO
 * @author: LiuH
 * @date: 2023/2/21 09:39
 */
public class LockTest {

    static HashMap<String, String> LOCAL_CACHE = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        while (i < 4) {
            new Thread(() -> {
                try {
                    test("a");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
            new Thread(() -> {
                try {
                    test("b");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
            new Thread(() -> {
                try {
                    test("c");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
            i++;
        }
    }

    private static String getFromMap(String k) throws InterruptedException {
        if (LOCAL_CACHE.containsKey(k)) {
            return k;
        }
        return "";
    }

    private static void test(String k) throws InterruptedException {
        String a = getFromMap(k);
        if (a.isEmpty()) {
            System.out.println("尝试取锁：" + k);
            synchronized (k) {
                a = getFromMap(k);
                System.out.println("取锁成功：" + k + "后取值：" + a);
                TimeUnit.SECONDS.sleep(1);
                if (a.isEmpty()) {
                    TimeUnit.SECONDS.sleep(4);
                    LOCAL_CACHE.put(k, "a");
                }
                System.out.println("归还锁" + k);
            }
        }
    }
}
