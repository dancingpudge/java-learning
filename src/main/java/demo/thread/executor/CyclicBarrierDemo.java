package demo.thread.executor;

import java.util.concurrent.*;

/**
 * CyclicBarrier的两个构造函数：
 * CyclicBarrier(int parties)和CyclicBarrier(int parties, Runnable barrierAction)
 * 前者只需要声明需要拦截的线程数即可，而后者还需要定义一个等待所有线程到达屏障优先执行的Runnable对象。
 *
 * @author liuh
 * @date 2018-10-17 11:43
 **/
public class CyclicBarrierDemo {
    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(4, 10, 6, TimeUnit.SECONDS, new LinkedBlockingQueue<>(2));
    /**
     * 当拦截线程数达到4时，便优先执行barrierAction，然后再执行被拦截的线程。
     */
    private static final CyclicBarrier cb = new CyclicBarrier(4, () -> System.out.println("寝室四兄弟一起出发去球场"));

    public static void main(String[] args) {
        String[] str = {"李明", "王强", "刘凯", "赵杰"};
        for (int i = 0, length = str.length; i < length; i++) {
            threadPool.execute(new GoThread(str[i], cb));
        }
        try {
            Thread.sleep(4000);
            System.out.println("四个人一起到达球场，现在开始打球");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

class GoThread extends Thread {
    private final String name;
    private final CyclicBarrier cb;

    public GoThread(String name, CyclicBarrier cb) {
        this.name = name;
        this.cb = cb;
    }

    @Override
    public void run() {
        System.out.println(name + "开始从宿舍出发");
        try {
            Thread.sleep(1000);
            cb.await();//拦截线程
            System.out.println(name + "从楼底下出发");
            Thread.sleep(1000);
            System.out.println(name + "到达操场");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}