package thread.daemon;

/**
 * Main线程结束，其他线程也可以立刻结束，当且仅当这些子线程都是守护线程。
 * <p>
 * java虚拟机(相当于进程)退出的时机是：虚拟机中所有存活的线程都是守护线程。
 * 只要还有存活的非守护线程虚拟机就不会退出，而是等待非守护线程执行完毕；
 * 反之，如果虚拟机中的线程都是守护线程，那么不管这些线程的死活java虚拟机都会退出。
 * 测试代码如下：
 *
 * @author liuh 【liuh@mail.joyowo.com】
 * @date 2018-10-11 15:25
 **/
public class ParentTest2 {
    public static void main(String[] args) {
        System.out.println("parent thread begin ");

        ChildThread2 t1 = new ChildThread2("thread1");
        ChildThread2 t2 = new ChildThread2("thread2");
        t1.setDaemon(true);
        t2.setDaemon(true);

        t1.start();
        t2.start();

        System.out.println("parent thread over ");
    }
}

/**
 * 在这种情况下，的确主线程退出，子线程就立刻结束了，但是这是属于JVM的底层实现机制，并不是说主线程和子线程之间存在依赖关系
 */
class ChildThread2 extends Thread {
    private String name;

    public ChildThread2(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(this.name + "--child thead begin");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println(this.name + "--child thead over");
    }
}
