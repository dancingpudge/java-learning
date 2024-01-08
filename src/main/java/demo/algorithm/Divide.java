package demo.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @packageName: demo
 * @author: LiuH
 * @description: TODO
 * @date: 2022/7/18 10:03
 */
public class Divide {
    public static void main(String[] args) {
       /* try {
            System.out.println(1 / 0);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("==============");
            log.info(StackTraceLogUtil.getStackTraceAsString(e));
        }*/
        System.out.println(divide(26, 3));
    }

    public static int divide(int dividend, int divisor) {
        boolean b = (dividend > 0 && divisor < 0) || (dividend < 0 && divisor > 0);
        dividend = dividend == Integer.MIN_VALUE ? Integer.MAX_VALUE : dividend;
        divisor = divisor == Integer.MIN_VALUE ? Integer.MAX_VALUE : divisor;
        dividend = dividend < 0 ? 0 - dividend : dividend;
        divisor = divisor < 0 ? 0 - divisor : divisor;
        if (dividend < divisor) {
            return 0;
        }
        List<Integer> n = new ArrayList<>();
        int t = 0;
        while (t >= 0) {
            t = dev(dividend, divisor, t);
            n.add(t);
            dividend = dividend - (divisor << t);
            if (dividend < divisor) {
                break;
            }
        }
        int r = 0;
        for (int i : n) {
            r += 1 << i;
        }
        return b ? 0 - r : r;
    }

    public static int dev(int a, int b, int s) {
        if (s > 0) {
            while (true) {
                if (b << s > a) {
                    return s - 1;
                }
                s--;
            }
        } else {
            while (true) {
                if (b << s > a) {
                    return s - 1;
                }
                s++;
            }
        }
    }
}
