package demo.thread.producer;

import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Liuh
 * @date 2016/11/17
 */
public class Customer extends Thread {
    BlockingQueue queue;
    Customer(BlockingQueue queue){
        this.queue = queue;
    }
    @Override
    public void run(){
        try {
            queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
