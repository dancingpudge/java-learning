package com.shark.demo.juc.executor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description //TODO
 * @Author liuhu
 * @Date 2020/5/1 22:55
 **/
public class SemaphoreDemo {
    static ExecutorService executor = new ThreadPoolExecutor(
            2,
            Runtime.getRuntime().availableProcessors() + 1,
            1,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(500),
            new DefaultThreadFactory(),
            new ThreadPoolExecutor.DiscardPolicy()
    );

    /**
     * The default thread factory
     */
    static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "MyPool-" + poolNumber.getAndIncrement();
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }


    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            try {
                semaphore.tryAcquire(10, TimeUnit.SECONDS);

                Thread t = new Thread(() -> {
                    System.out.println(Thread.currentThread().getName());
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        semaphore.release();
                    }
                }, "thName" + finalI);

                System.out.println("=====" + t.getName());
                executor.submit(t);
            } catch (Exception e) {
            } finally {
                semaphore.release();
            }
        }
    }
}
