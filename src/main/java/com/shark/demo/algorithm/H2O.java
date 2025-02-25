package com.shark.demo.algorithm;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @description: demo <br>
 * @date: 2021/6/18 2:51 下午 <br>
 * @author: liuhui <br>
 * @version: 0.0.1-SNAPSHOT <br>
 */
public class H2O {
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
    static boolean b = true;

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                System.out.print("h");
                try {
                    if (!b) {
                        b = !b;
                        cyclicBarrier.await();
                    } else {
                        b = !b;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    cyclicBarrier.await();
                    System.out.print("o");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
