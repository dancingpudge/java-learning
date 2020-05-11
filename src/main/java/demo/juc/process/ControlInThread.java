package demo.juc.process;

/**
 * @Description 线程内使用join控制
 * @Author liuhu
 * @Date 2020/5/4 23:32
 **/
public class ControlInThread {

    static class Thread1 implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "   线程1结束啦");
        }
    }

    static class Thread2 implements Runnable {

        public Thread t;

        public Thread2(Thread t) {
            this.t = t;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + "   线程2开始啦");
                t.join();
                System.out.println(Thread.currentThread().getName() + "   线程2结束啦");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new Thread1(), "Th1");
        new Thread(new Thread2(t1), "Th2").start();
        t1.start();
    }

}
