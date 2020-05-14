package demo.design.pattern.reflect;

import java.lang.reflect.Method;

/**
 * 根据运行结果得出Class.forName加载类时将类进了初始化
 * 而ClassLoader的loadClass并没有对类进行初始化，只是把类加载到了虚拟机中。
 */
public class ReflectClass {
    public static void main(String[] args) {
        reflectNewInstance();
    }

    // 创建对象
    public static void reflectNewInstance() {

        String cl = "demo.designpattern.reflect.Book";
        String methodName = "getName";
        try {
            //Class.forName会执行静态代码块和静态方法
            Class<?> classBook = Class.forName(cl);
            Book bk1 = (Book) classBook.newInstance();
            System.out.println("reflectNewInstance book = " + bk1 + " " + bk1.getName());

            Thread.sleep(1000);
            //ClassLoader().loadClass 不会执行静态代码块和静态方法
            Class<?> classBook2 = ReflectClass.class.getClassLoader().loadClass(cl);
            Book bk2 = (Book) classBook2.newInstance();
            System.out.println("reflectNewInstance book = " + bk2 + " " + bk2.getSize());


            //纯配置就可以调用被反射的类的任意方法
            Method method = classBook.getDeclaredMethod(methodName, null);
            System.out.println(method.invoke(bk1, null));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
