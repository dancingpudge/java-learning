package thread;

/**
 * Created by Liuh on 2016/10/11.
 */
public class SonRunnable implements Runnable{

    Output out =null;

    SonRunnable(Output out){
        this.out=out;
    }

    public void run() {
        try {
            //因为要执行50次要求的操作，每次操作子线程要执行2次，一共50*2=100次
            for(int i=1;i<=100;i++){
                out.doSonWhile(i%2==0?i/2:(i+1)/2);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
