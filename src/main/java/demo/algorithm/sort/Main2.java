package demo.algorithm.sort;

import java.util.Scanner;

/**
 * @Description //TODO
 * @Author liuhu
 * @Date 2020/4/18 19:11
 **/
public class Main2 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long n = 0;
        try {
            n = in.nextLong();
        } catch (Exception e) {
            System.out.println("ERROR");
            return;
        }
        if (n <= 11) {
            System.out.println(11);
            return;
        }
        if (isNot(n)) {
            System.out.println(n);
            return;
        }
        long resP = n;
        long resD = n;
        boolean b = true;
        while (b) {
            if (resP < Integer.MAX_VALUE) {
                resP++;
                if (isNot(resP)) {
                    System.out.println(resP);
                    b = false;
                }
            }

            resD--;
            if (isNot(resD)) {
                System.out.println(resD);
                b = false;
            }
        }
    }

    private static boolean isNot(long n) {
        char[] s = (n + "").toCharArray();
        int l = 0;
        while (l < s.length / 2) {
            if (s[l] != s[s.length - l - 1]) {
                return false;
            }
            l++;
        }
        return true;
    }

}
