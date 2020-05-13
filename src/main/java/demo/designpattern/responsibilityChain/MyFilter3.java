package demo.designpattern.responsibilityChain;

/**
 * @Description //TODO
 * @Author liuhu
 * @Date 2020/5/13 0:43
 **/
public class MyFilter3 implements MyFilter {

    @Override
    public boolean filter() {
        System.out.println(this.getClass().getName() + " do something");
        return true;
    }
}
