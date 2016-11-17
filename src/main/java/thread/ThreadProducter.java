package thread;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Liuh on 2016/11/17.
 */
public class ThreadProducter extends Thread {
    BlockingQueue queue;
    int num;
    ThreadProducter(BlockingQueue queue, int num){
        this.queue = queue;
        this.num = num;
    }
    public void run(){
        try {
            //System.out.println("存入：     "+ num);
            queue.put("thread".concat(String.valueOf(num)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
