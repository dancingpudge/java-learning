package demo.juc.daemon;


/**
 * Main线程是个非守护线程，不能设置成守护线程
 *
 * 这是因为，main线程是由java虚拟机在启动的时候创建的。
 * main方法开始执行的时候，主线程已经创建好并在运行了。
 * 对于运行中的线程，调用Thread.setDaemon()会抛出异常Exception in demo.thread "main"     java.lang.IllegalThreadStateException。
 * 测试代码如下：
 *
 *
 * @author liuh
 * @date 2018-10-11 15:21
 **/
public class MainThread {
    public static void main(String[] args)
    {
        System.out.println(" parent demo.thread begin ");
        Thread.currentThread().setDaemon(true);
    }

}
