package demo.thread.producer;

import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Liuh
 * @date 2016/11/17
 */
public class Producer extends Thread {
    BlockingQueue queue;
    int num;
    Producer(BlockingQueue queue, int num){
        this.queue = queue;
        this.num = num;
    }
    @Override
    public void run(){
        try {
            System.out.println("存入：     "+ num);
            queue.put("demo/thread".concat(String.valueOf(num)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
