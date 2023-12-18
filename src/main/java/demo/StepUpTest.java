package demo;

import java.util.HashMap;

/**
 * @author: LiuH
 * @date: 2023/6/9 18:46
 */
public class StepUpTest {
    public static void main(String[] args) {
        System.out.println(f(20));
        System.out.println(ff(20));
    }

    static HashMap<Integer, Integer> map = new HashMap();

    public static int f(int n) {
        if (n <= 2) return n;
        if (map.containsKey(n)) {
            return map.get(n);
        } else {
            map.put(n, f(n - 1) + f(n - 2));
            System.out.println("n=" + n + ":" + map.get(n));
        }
        return map.get(n);
    }

    public static int ff(int n) {
        if (n <= 2) return n;
        int n1 = 1;
        int n2 = 2;
        int res = 0;
        for (int i = 3; i <= n; i++) {
            res = n1 + n2;
            n1 = n2;
            n2 = res;
            System.out.println(res);
        }
        return res;
    }


}
