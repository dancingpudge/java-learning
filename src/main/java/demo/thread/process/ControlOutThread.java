package demo.thread.process;

/**
 * @Description 父线程中使用join控制
 * @Author liuhu
 * @Date 2020/5/4 23:32
 **/
public class ControlOutThread {

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
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "   线程2结束啦");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                Thread t1 = new Thread(new Thread1(), "Th1:" + finalI);
                t1.start();
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new Thread(new Thread2(), "Th2:" + finalI).start();
            }).start();

            new Thread(() -> {
                Thread t1 = new Thread(new Thread1(), "Th11:" + finalI);
                t1.start();
                new Thread(new Thread2(), "Th22:" + finalI).start();
            }).start();

            Thread.sleep(3000);
            System.out.println("===========================");
        }


    }

}
