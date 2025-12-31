package com.shark.demo.algorithm;

import cn.hutool.core.util.RandomUtil;

import java.util.BitSet;

/**
 * @author: LiuH
 * @date: 2025/12/31 16:48
 */
public class BitMapTest {
    public static void main(String[] args) {
        BitSet bitMap = new BitSet();
        for (int i = 0; i < 1000; i++) {
            bitMap.set(RandomUtil.randomInt(0, 1000));
        }
        System.out.println(bitMap.cardinality());
        bitMap.stream().forEach(b-> System.out.println(b));
    }
}
