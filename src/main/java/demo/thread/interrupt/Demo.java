package demo.thread.interrupt;

/**
 * @author liuh
 */
public class Demo extends Thread {

    public Demo(String name) {
        super.setName(name);
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            System.out.println(this.getName() + "运行中。。。");
        }
    }

    public static void main(String[] args) {
        Demo d1 = new Demo("first ");
        Demo d2 = new Demo("second ");

        d2.setDaemon(true);
        d1.start();
        d2.start();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         System.out.println("=========>" + Thread.activeCount());
        d2.notifyAll();
    }
}
