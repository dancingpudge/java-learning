package com.shark.demo;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: LiuH
 * @date: 2025/2/25 15:35
 */
public class ExecutorServiceTest {
    static ExecutorService executorService = new ThreadPoolExecutor(1, 17
            , 1L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100));

    @Test
    public void submitTest() {
        for (int i = 200; i < 400; i++) {
            if (i > 110) {
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            submit(i);
        }
    }

    private static void submit(int i) {
        executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("线程" + i + "sleep后执行");
        });
    }
}
