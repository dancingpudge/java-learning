package thread;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Liuh on 2016/11/17.
 */
public class ThreadCustomer extends Thread {
    BlockingQueue queue;
    ThreadCustomer(BlockingQueue queue){
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
