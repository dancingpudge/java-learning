package com.shark.demo.lambda;

import cn.hutool.core.util.RandomUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: TODO
 * @author: LiuH
 * @date: 2024/3/18 10:25
 */
public class SortTest {


    public static void main(String[] args) {
        List<CarPark> carParkList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            carParkList.add(new CarPark(RandomUtil.randomInt(30, 270) % 2 == 0));

        }

        carParkList = carParkList.stream()
                .sorted(Comparator.comparing(CarPark::getIsEnable).reversed())
                .collect(Collectors.toList());

        carParkList.forEach(c -> System.out.println(c.getIsEnable()));
    }

    static class CarPark {

        boolean isEnable;

        int id;

        private CarPark(boolean isEnable) {
            this.isEnable = isEnable;
            this.id = RandomUtil.randomInt();
        }

        public boolean getIsEnable() {
            return isEnable;
        }

        public void setIsEnable(boolean enable) {
            isEnable = enable;
        }
    }

}
