package demo.lambda;

import cn.hutool.core.util.RandomUtil;

/**
 * @description: TODO
 * @author: LiuH
 * @date: 2024/3/18 10:25
 */
public class SortTest {


    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(RandomUtil.randomInt(30,270));
        }
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
