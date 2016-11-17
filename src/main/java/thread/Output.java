package thread;

/**
 * Created by Liuh on 2016/10/11.
 */
public class Output {

    //两把“钥匙”
    boolean mbegin=false;
    boolean sbegin=true;//第一次子线程开始执行，所以这里默认为true

    //主线程循环打印的方法（不可被打断）
    public synchronized void doMainWhile(int num) throws InterruptedException {
        int i=0;//每次i都要初始化为0，供循环使用
        while(mbegin==false){//如果子线程在执行，主线程要等待，直到子线程恢复主线程的钥匙
            this.wait();
        }
        for (i = 1; i <=100; i++) {//开始循环(每次在方法里循环100次)
            System.out.println("主线程执行了第"+i+"次循环,总循环为第"+num+"次");
            if(i==100){
                break;
            }
        }
        if(i==100){//主线程循环打印完毕之后，要让位给子线程执行
            sbegin=true;//子线程开始工作
            mbegin=false;//主线程停止工作
            this.notify();//通知其他线程开始工作
        }
    }

    //子线程循环打印的方法（不可被打断）
    public synchronized void doSonWhile(int num) throws InterruptedException {
        int j=0;//每次i都要初始化为0，供循环使用
        while(sbegin==false){//如果主线程在执行，子线程要等待，直到主线程恢复子线程的钥匙
            this.wait();
        }
        for (j = 1; j <=10; j++) {//开始循环(每次在方法里循环10次)
            System.out.println("子线程执行了第"+j+"次循环,总循环为第"+num+"次");
            if(j==10){
                break;
            }
        }
        if(j==10){//子线程循环打印完毕之后，要让位给主线程执行
            sbegin=false;//子线程停止工作
            mbegin=true;//主线程开始
            this.notify();//通知其他线程开始工作
        }
    }

}
