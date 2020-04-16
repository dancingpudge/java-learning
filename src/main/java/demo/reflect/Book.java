package demo.reflect;

public class Book {
    private static String name;

    private static int size;

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    static {
        System.out.println("执行了静态代码块");
    }
    //静态变量
    private static String staticFiled = staticMethod();

    //赋值静态变量的静态方法
    public static String staticMethod(){
        System.out.println("执行了静态方法");
        return "给静态字段赋值了";
    }
}
