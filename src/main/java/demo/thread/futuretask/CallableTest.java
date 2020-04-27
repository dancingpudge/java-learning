package demo.thread.futuretask;

import java.util.concurrent.*;

public class CallableTest {
    public static void main(String[] args) {
        FutureTask futureTask = new FutureTask(() -> 1);
        ExecutorService executorService = new ThreadPoolExecutor(
                1,
                2,
                3L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(6),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        executorService.submit(futureTask);
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
/*
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                System.out.println(finalI);
                System.out.println(Thread.currentThread().getName() + "的循环变量i的值 ：" + 10 / finalI);
            }, "").start();
        }
        //因为Callable接口是函数式接口，可以使用Lambda表达式
        FutureTask<Integer> task = new FutureTask<>(() -> {
            int i = 0;
            for (; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "的循环变量i的值 ：" + 10 / i);
            }
            return i;
        });
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + " 的循环变量i ： " + i);
            if (i == 20) {
                new Thread(task, "有返回值的线程").start();
            }
        }
        try {
            System.out.println("子线程返回值 ： " + task.get());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}