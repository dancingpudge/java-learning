package demo.thread.daemon;


/**
 * Main线程结束，其他线程一样可以正常运行
 * <p>
 * 主线程，只是个普通的非守护线程，用来启动应用程序，不能设置成守护线程；
 * 除此之外，它跟其他非守护线程没有什么不同。
 * 主线程执行结束，其他线程一样可以正常执行。代码如下：
 *
 * @author liuh
 * @date 2018-10-11 15:22
 **/
public class ParentTest {

    /**
     * 这样其实是很合理的，按照操作系统的理论，进程是资源分配的基本单位，线程是CPU调度的基本单位。
     * 对于CPU来说，其实并不存在java的主线程和子线程之分，都只是个普通的线程。
     * 进程的资源是线程共享的，只要进程还在，线程就可以正常执行，换句话说线程是强依赖于进程的。
     * 也就是说，线程其实并不存在互相依赖的关系，一个线程的死亡从理论上来说，不会对其他线程有什么影响。
     */
    public static void main(String[] args) {
        System.out.println("parent demo.thread begin ");

        ChildThread t1 = new ChildThread("thread1");
        ChildThread t2 = new ChildThread("thread2");
        t1.start();
        t2.start();

        System.out.println("parent demo.thread over ");
    }
}


class ChildThread extends Thread {
    private String name;

    public ChildThread(String name) {
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


