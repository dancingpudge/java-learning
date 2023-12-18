package demo.algorithm;

/**
 * @description: demo.algorithm <br>
 * @date: 2021/4/7 6:52 下午 <br>
 * @author: liuhui <br>
 * @version: 0.0.1-SNAPSHOT <br>
 */
public class DynamicProgramming {

    public static void main(String[] args) {
        System.out.println(sum(20));
    }

    private static int sum(int n) {
        System.out.println(n);
        return n == 1 || n == 2 ? n : sum(n - 1) + sum(n - 2);
    }
}
