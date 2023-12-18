package demo.juc.quasar;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.fibers.Suspendable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @description: demo.juc.quasar <br>
 * @date: 2021/7/22 2:41 下午 <br>
 * @author: liuhui <br>
 * @version: 0.0.1-SNAPSHOT <br>
 */
public class CoroutineTest {
    public static void main(String[] args) {
        Fiber fiberA = new FiberTask("hello");
        Fiber fiberB = new FiberTask("world");
        fiberA.start();
        fiberB.start();
        try {
            fiberA.join();
            fiberB.join();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

   static class FiberTask extends Fiber<Integer> {
        private String msg;


        public FiberTask(String msg) {
            this.msg = msg;
        }

        @Override
        @Suspendable
        protected Integer run() throws SuspendExecution, InterruptedException {
            for (int i = 0; i < 5; i++) {
                System.out.println(msg);
                Fiber.park(1000, TimeUnit.MILLISECONDS);
            }
            return 0;
        }
    }
}
