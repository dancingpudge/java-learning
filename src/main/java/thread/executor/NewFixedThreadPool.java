package thread.executor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

import static thread.Constant.SDF;

/**
 * Java通过Executors提供四种线程池，分别为：
 * <p>
 * newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
 * newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
 * newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
 * newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 * <p>
 * 优点
 * 重用存在的线程，减少对象创建、消亡的开销，性能佳。
 * 可有效控制最大并发线程数，提高系统资源的使用率，同时避免过多资源竞争，避免堵塞。
 * 提供定时执行、定期执行、单线程、并发数控制等功能。
 *
 * @author Liuh
 */
public class NewFixedThreadPool {

    /**
     * public ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,
     * TimeUnit unit,BlockingQueue<Runnable> workQueue)
     * corePoolSize用于指定核心线程数量
     * maximumPoolSize指定最大线程数
     * keepAliveTime和TimeUnit指定线程空闲后的最大存活时间
     * workQueue则是线程池的缓冲队列,还未执行的线程会在队列中等待
     * 监控队列长度，确保队列有界
     * 不当的线程池大小会使得处理速度变慢，稳定性下降，并且导致内存泄露。如果配置的线程过少，则队列会持续变大，消耗过多内存。
     * 而过多的线程又会 由于频繁的上下文切换导致整个系统的速度变缓——殊途而同归。队列的长度至关重要，它必须得是有界的，这样如果线程池不堪重负了它可以暂时拒绝掉新的请求。
     * ExecutorService 默认的实现是一个无界的 LinkedBlockingQueue。
     */
    //ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, corePoolSize + 1, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(1000));
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        /**
         * IO密集型任务  = 一般为2*CPU核心数（常出现于线程中：数据库数据交互、文件上传下载、网络数据传输等等）
         * CPU密集型任务 = 一般为CPU核心数+1（常出现于线程中：复杂算法）
         * 混合型任务  = 视机器配置和复杂度自测而定
         */
        int corePoolSize = Runtime.getRuntime().availableProcessors();

        ExecutorService executor = Executors.newFixedThreadPool(corePoolSize * 2);
        /**
         * 方式一(CountDownLatch)
         */
        methodCountDownLatch(corePoolSize * 2, executor);
        /**
         * 方式二(Future)
         */
        methodFuture(executor);

        executor.shutdown();
        System.out.println("----结束运行----");
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
        for (int i = 0; i < corePoolSize; i++) {
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
        Date date1 = new Date();
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

        Date date2 = new Date();
        System.out.println("----Future 程序结束运行----，程序运行时间【" + (date2.getTime() - date1.getTime()) + "毫秒】");
    }
}

class Stats implements Runnable {
    String statsName;
    CountDownLatch latch;

    public Stats(String statsName, CountDownLatch latch) {
        this.statsName = statsName;
        this.latch = latch;
    }

    public void run() {
        try {
            System.out.println(statsName + " do stats begin at " + SDF.format(new Date()));
            //模拟任务执行时间
            Thread.sleep(1000);
            System.out.println(statsName + " do stats complete at " + SDF.format(new Date()));
            latch.countDown();//单次任务结束，计数器减一
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyCallable implements Callable<Object> {
    private String taskNum;

    MyCallable(String taskNum) {
        this.taskNum = taskNum;
    }

    public Object call() throws Exception {
        Date dateTmp1 = new Date();
        Thread.sleep(1000);
        Date dateTmp2 = new Date();
        long time = dateTmp2.getTime() - dateTmp1.getTime();
        return taskNum + "任务返回运行结果,当前任务时间【" + time + "毫秒】";
    }
}

