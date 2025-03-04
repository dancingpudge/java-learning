package com.shark.demo.juc.executor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

import static com.shark.demo.juc.executor.CountDownLatchDemo.SDF;


/**
 * @author Liuh
 */
public class CountDownLatchDemo {

    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * IO密集型任务  = 一般为2*CPU核心数（常出现于线程中：数据库数据交互、文件上传下载、网络数据传输等等）
     * CPU密集型任务 = 一般为CPU核心数+1（常出现于线程中：复杂算法）
     * 混合型任务  = 视机器配置和复杂度自测而定
     */
    static final int corePoolSize = Runtime.getRuntime().availableProcessors();

    static ExecutorService executor = new ThreadPoolExecutor(
            0,
            corePoolSize + 1,
            1,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(3),
            new ThreadPoolExecutor.DiscardPolicy()
    );

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //方式一(CountDownLatch)
        methodCountDownLatch(corePoolSize * 2, executor);

        //方式二(Future)
        methodFuture(executor);
    }

    /**
     * 方式一(CountDownLatch)
     *
     * @throws InterruptedException
     */
    static void methodCountDownLatch(int corePoolSize, ExecutorService executor) throws InterruptedException {
        System.out.println("----CountDownLatch 开始运行----");
        CountDownLatch latch = new CountDownLatch(corePoolSize);
        //使用execute方法
        for (int i = 0; i < corePoolSize * 3; i++) {
            executor.execute(new Stats("任务" + i, latch));
        }
        latch.await();// 等待所有人任务结束
        System.out.println("----CountDownLatch 开始运行----");
    }

    /**
     * 方式二(Future)
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    static void methodFuture(ExecutorService executor) throws ExecutionException, InterruptedException {
        System.out.println("----Future 开始运行----");
        // 创建一个线程池
        // 创建多个有返回值的任务
        List<Future> list = new ArrayList<Future>();
        for (int i = 0; i < 5; i++) {
            Callable c = new MyCallable(i + " ");
            // 执行任务并获取Future对象
            Future f = executor.submit(c);
            System.out.println("运行中 ===》" + f.get().toString());
            list.add(f);
        }

        // 获取所有并发任务的运行结果
        for (Future f : list) {
            // 从Future对象上获取任务的返回值，并输出到控制台
            System.out.println("运行后 ===》" + f.get().toString());
        }

        System.out.println("----Future 程序结束运行----");
    }

}


/**
 * Runnable与Callable
 * <p>
 * 相同点：
 * 两者都是接口；
 * 两者都可用来编写多线程程序；
 * 两者都需要调用Thread.start()启动线程；
 * <p>
 * 不同点：
 * 两者最大的不同点是：实现Callable接口的任务线程能返回执行结果；而实现Runnable接口的任务线程不能返回结果；
 * Callable接口的call()方法允许抛出异常；而Runnable接口的run()方法的异常只能在内部消化，不能继续上抛；
 * <p>
 * 注意点：
 * Callable接口支持返回执行结果，此时需要调用FutureTask.get()方法实现，此方法会阻塞主线程直到获取‘将来’结果；当不调用此方法时，主线程不会阻塞！
 */

class Stats implements Runnable {
    String statsName;
    CountDownLatch latch;

    public Stats(String statsName, CountDownLatch latch) {
        this.statsName = statsName;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            System.out.println(statsName + " do stats begin at " + SDF.format(new Date()));
            Thread.sleep(1000);
            System.out.println(statsName + " do stats complete at " + SDF.format(new Date()));
            //单次任务结束，计数器减一
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
        }
    }
}

class MyCallable implements Callable<Object> {
    private String taskNum;

    MyCallable(String taskNum) {
        this.taskNum = taskNum;
    }

    @Override
    public Object call() throws Exception {
        System.out.println(taskNum + " do stats begin at " + SDF.format(new Date()));
        Thread.sleep(1000);
        System.out.println(taskNum + " do stats complete at " + SDF.format(new Date()));
        return taskNum + "任务返回运行结果";
    }
}

