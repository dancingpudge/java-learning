package demo.thread;

import java.util.Arrays;
import java.util.List;

/**
 * @Description //TODO
 * @Author liuhu
 * @Date 2020/4/20 22:15
 **/
public class test {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new th());
        Thread t2 = new Thread(new th());
        t.start();
        t2.join();
        System.out.println("---------");
    }

}

class th implements Runnable {
    List a = Arrays.asList("fafasgfag".split(""));

    @Override
    public void run() {
        a.stream().forEach(t -> new Thread(() -> System.out.println(t)).start());
    }
}
