package thread.executor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

import static thread.Constant.SDF;

/**
 * 有返回值的线程
 *
 * @author Liuh
 */
@SuppressWarnings("unchecked")
public class NewFixedThreadPool {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * 方式一(CountDownLatch)
         */
        methodCountDownLatch();
        /**
         * 方式二(Future)
         */
        methodFuture();
    }

    /**
     * 方式一(CountDownLatch)
     *
     * @throws InterruptedException
     */
    static void methodCountDownLatch() throws InterruptedException {


        /**
         * IO密集型任务  = 一般为2*CPU核心数（常出现于线程中：数据库数据交互、文件上传下载、网络数据传输等等）
         * CPU密集型任务 = 一般为CPU核心数+1（常出现于线程中：复杂算法）
         * 混合型任务  = 视机器配置和复杂度自测而定
         */
        int corePoolSize = Runtime.getRuntime().availableProcessors();
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
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, corePoolSize + 1, 10L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(1000));

        CountDownLatch latch = new CountDownLatch(5);
        //使用execute方法
        executor.execute(new Stats("任务A", 1000, latch));
        executor.execute(new Stats("任务B", 1000, latch));
        executor.execute(new Stats("任务C", 1000, latch));
        executor.execute(new Stats("任务D", 1000, latch));
        executor.execute(new Stats("任务E", 1000, latch));
        latch.await();// 等待所有人任务结束
        System.out.println("所有的统计任务执行完成:" + SDF.format(new Date()));
    }

    /**
     * 方式二(Future)
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    static void methodFuture() throws ExecutionException, InterruptedException {
        System.out.println("----程序开始运行----");
        Date date1 = new Date();

        int taskSize = 15;
        // 创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(taskSize);
        // 创建多个有返回值的任务
        List<Future> list = new ArrayList<Future>();
        for (int i = 0; i < taskSize; i++) {
            Callable c = new MyCallable(i + " ");
            // 执行任务并获取Future对象
            Future f = pool.submit(c);
            System.out.println(">>>" + f.get().toString());
            list.add(f);
        }
        // 关闭线程池
        pool.shutdown();

        // 获取所有并发任务的运行结果
        for (Future f : list) {
            // 从Future对象上获取任务的返回值，并输出到控制台
            System.out.println(">>>" + f.get().toString());
        }

        Date date2 = new Date();
        System.out.println("----程序结束运行----，程序运行时间【" + (date2.getTime() - date1.getTime()) + "毫秒】");
    }
}

class Stats implements Runnable {


    static final String startTime = SDF.format(new Date());
    String statsName;
    int runTime;
    CountDownLatch latch;

    public Stats(String statsName, int runTime, CountDownLatch latch) {
        this.statsName = statsName;
        this.runTime = runTime;
        this.latch = latch;
    }

    public void run() {
        try {
            System.out.println(statsName + " do stats begin at " + startTime);
            //模拟任务执行时间
            Thread.sleep(runTime);
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
        System.out.println(">>>" + taskNum + "任务启动");
        Date dateTmp1 = new Date();
        Thread.sleep(10);
        Date dateTmp2 = new Date();
        long time = dateTmp2.getTime() - dateTmp1.getTime();
        System.out.println(">>>" + taskNum + "任务终止");
        return taskNum + "任务返回运行结果,当前任务时间【" + time + "毫秒】";
    }
}

