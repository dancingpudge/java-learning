package thread;

/**
 * Created by Liuh on 2016/10/11.
 */
public class MainRunnable implements Runnable{

        Output out =null;

        MainRunnable(Output out){//将Output对象引入进来
        this.out=out;
        }

        public void run() {
                try {
                        //因为要执行50次要求的操作，每次操作主线程要执行2次，一共50*2=100次
                        for(int i=1;i<=100;i++){
                        out.doMainWhile(i%2==0?i/2:(i+1)/2);
                        }
                } catch (InterruptedException e) {
                e.printStackTrace();
                }
        }

}
