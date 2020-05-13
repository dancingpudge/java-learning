package demo.designpattern.responsibilityChain;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 责任链模式代码
 * @Author liuhu
 * @Date 2020/5/13 0:49
 **/
public class FilterChainMain {

    public static void main(String[] args) {

        FilterChain FilterChain2 = new FilterChain()
                .add(new MyFilter2())
                .add(new MyFilter3());

        new FilterChain()
                .add(new MyFilter1())
                .add(FilterChain2)
                .filter();

    }

    static class FilterChain implements MyFilter {
        List<MyFilter> filters = new ArrayList();

        //次数是责任链模式的精髓，返回的是MyFilter，add的也是MyFilter
        //所以就可以让责任链与责任链相连接
        public FilterChain add(MyFilter filter) {
            filters.add(filter);
            return this;
        }

        @Override
        public boolean filter() {
            for (MyFilter f : filters) {
                if (!f.filter()) {
                    return false;
                }
            }
            return true;
        }
    }
}
