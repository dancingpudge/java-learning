package demo.thread;

import java.util.concurrent.FutureTask;

public class CallableTest {
    public static void main(String[] args) {
        //因为Callable接口是函数式接口，可以使用Lambda表达式
        FutureTask<Integer> task = new FutureTask<>(() -> {
            int i = 0;
            for (; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + "的循环变量i的值 ：" + i);
            }
            return i;
        });
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " 的循环变量i ： "+ i);
            if (i == 20) {
                new Thread(task, "有返回值的线程").start();
            }
        }
        try {
            System.out.println("子线程返回值 ： " + task.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}