package thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Liuh on 2016/11/17.
 */
public class ThreadMain {
    public static void main(String [] args){
        for(int times = 0; times < 10; times++){
            final BlockingQueue queue = new ArrayBlockingQueue(10);
            long time1 = System.currentTimeMillis();
            for(int i = 0; i <10000 ;i ++){
                Thread t1 = new ThreadProducter(queue,i);
                Thread t2 = new ThreadCustomer(queue);
                t1.start();
                t2.start();
            }
            long time2 = System.currentTimeMillis();
            System.out.println("循环耗时：    "+(time2-time1)+"ms");
        }

    }

}
