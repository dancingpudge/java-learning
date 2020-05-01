package demo.thread.deadLock;

/**
 * @Description jstack
 * <p>
 * <p>
 * Found one Java-level deadlock:
 * =============================
 * "Thread-1":
 * waiting to lock monitor 0x032fc15c (object 0x05551218, a java.lang.String),
 * which is held by "Thread-0"
 * "Thread-0":
 * waiting to lock monitor 0x032fce7c (object 0x05551240, a java.lang.String),
 * which is held by "Thread-1"
 * <p>
 * Java stack information for the threads listed above:
 * ===================================================
 * "Thread-1":
 * at demo.thread.deadLock.DeadLockDemo$Lock.run(DeadLockDemo.java:29)
 * - waiting to lock <0x05551218> (a java.lang.String)
 * - locked <0x05551240> (a java.lang.String)
 * at java.lang.Thread.run(Thread.java:748)
 * "Thread-0":
 * at demo.thread.deadLock.DeadLockDemo$Lock.run(DeadLockDemo.java:29)
 * - waiting to lock <0x05551240> (a java.lang.String)
 * - locked <0x05551218> (a java.lang.String)
 * at java.lang.Thread.run(Thread.java:748)
 * <p>
 * Found 1 deadlock.
 * <p>
 * <p>
 * D:\Java\jdk1.8.0_161\bin>
 * @Author liuhu
 * @Date 2020/5/1 22:29
 **/
public class DeadLockDemo {

    public static class Lock implements Runnable {
        private String lock1;
        private String lock2;

        public Lock(String lock1, String lock2) {
            this.lock1 = lock1;
            this.lock2 = lock2;
        }

        @Override
        public void run() {
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + "======>try to get Lock" + lock1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + "======>try to get Lock" + lock2);
                }
            }
        }
    }

    public static void main(String[] args) {
        String lock1 = "lock1";
        String lock2 = "lock2";
        new Thread(new Lock(lock1, lock2)).start();
        new Thread(new Lock(lock2, lock1)).start();
    }
}
