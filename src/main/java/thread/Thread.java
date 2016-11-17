package thread;

/**
 * Created by Liuh on 2016/10/11.
 */
public class Thread {
    public static void main(String[] args) {
        Output out = new Output();//循环输出类
        //注意:这里要保证子线程和主线程类引入的是同一个Output对象
        //不然无法共用两把“钥匙”
        MainRunnable main= new MainRunnable(out);//主线程
        SonRunnable son= new SonRunnable(out);//子线程
        //new Thread(son).start();//主线程启动
        //new Thread(main).start();//子线程启动
    }
}
