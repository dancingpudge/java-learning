package demo.juc.quasar;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.strands.SuspendableCallable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @description: demo.juc.quasar <br>
 * @date: 2021/7/21 7:14 下午 <br>
 * @author: liuhui <br>
 * @version: 0.0.1-SNAPSHOT <br>
 */
public class QuasarTest {
    public static void main(String[] args) throws Exception {
        //使用阻塞队列来获取结果。
        LinkedBlockingQueue<Fiber<Integer>> fiberQueue = new LinkedBlockingQueue<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        TimeUnit.SECONDS.sleep(20);
        for (int i = 0; i < 1000000; i++) {
            int finalI = i;
            //这里的Fiber有点像Callable,可以返回数据
            Fiber<Integer> fiber = new Fiber<>((SuspendableCallable<Integer>) () -> {
                //这里用于测试内存占用量
                Fiber.sleep(30000);
                System.out.println("in-" + finalI + "-" + LocalDateTime.now().format(formatter));
                return finalI;
            });
            //开始执行
            fiber.start();
            //加入队列
            fiberQueue.add(fiber);
        }

        while (!fiberQueue.isEmpty()) {
            //阻塞
            Fiber<Integer> fiber = fiberQueue.take();
            System.out.println("out-" + fiber.get() + "-" + LocalDateTime.now().format(formatter));
        }

        TimeUnit.SECONDS.sleep(50);
    }
}
