package demo.design.pattern.strategy;

/**
 * @Description 只要需排序的类实现了Comparable接口，就都可以排序
 * @Author liuhu
 * @Date 2020/5/17 1:22
 **/
public class SortStrategy {
    public static void main(String[] args) {
        Sort.Select(new String[]{"afa", "faf", "afaf"});
        Sort.Select(new MyComparable[]{new MyComparable(3), new MyComparable(1), new MyComparable(6),});
    }

    static class Sort {
        static void Select(Comparable a[]) {
            for (int i = 0, aLength = a.length; i < aLength - 1; i++) {
                int min = i;
                for (int j = i + 1; j < aLength; j++) {
                    if (a[j].compareTo(a[min]) < 0) {
                        min = j;
                    }
                }
                Comparable temp = a[i];
                a[i] = a[min];
                a[min] = temp;
            }
            System.out.println(a);
        }
    }

}

class MyComparable implements Comparable<MyComparable> {

    private int s;

    MyComparable(int s) {
        this.s = s;
    }

    @Override
    public int compareTo(demo.design.pattern.strategy.MyComparable o) {
        return s - o.s > 0 ? 1 : -1;
    }
}