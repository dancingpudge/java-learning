package thread;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Liuh on 2016/11/17.
 */
public class TestThreadCustomer extends Thread {
    BlockingQueue queue;
    TestThreadCustomer(BlockingQueue queue){
        this.queue = queue;
    }
    public void run(){
        try {
            queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
