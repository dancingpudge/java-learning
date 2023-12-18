package demo.lambda;

import cn.hutool.core.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 此测试用例用来说明
 * 1、stream的parallel可提高计算效率
 * 2、stream的parallel非线程安全
 *
 * @author: LiuH
 * @date: 2023/6/12 21:54
 */
public class StreamParallelTest {
    public static void main(String[] args) {

        List list = new ArrayList();
        long s = System.currentTimeMillis();
        IntStream.range(0, 1000).parallel().forEach(i -> {
            try {
                TimeUnit.MILLISECONDS.sleep(RandomUtil.randomInt(0, 20));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            list.add(i);
        });
        System.out.println(list.size() + " time cost " + (System.currentTimeMillis() - s));
        s = System.currentTimeMillis();

        List cplist = new CopyOnWriteArrayList();
        IntStream.range(0, 1000).parallel().forEach(i -> {
            try {
                TimeUnit.MILLISECONDS.sleep(RandomUtil.randomInt(0, 20));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            cplist.add(i);
        });
        System.out.println(cplist.size() + " time cost " + (System.currentTimeMillis() - s));
        s = System.currentTimeMillis();

        List cplist2 = new CopyOnWriteArrayList();
        IntStream.range(0, 1000).forEach(i -> {
            try {
                TimeUnit.MILLISECONDS.sleep(RandomUtil.randomInt(0, 20));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            cplist2.add(i);
        });
        System.out.println(cplist2.size() + " time cost " + (System.currentTimeMillis() - s));
    }
}
