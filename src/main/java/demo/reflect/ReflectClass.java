package demo.reflect;

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

        String cl = "demo.reflect.Book";
        try {
            Class<?> classBook = Class.forName(cl, true, ClassLoader.getSystemClassLoader());
            Book bk1 = (Book) classBook.newInstance();
            System.out.println("reflectNewInstance book = " + bk1 + " " + bk1.getName());

            Thread.sleep(1000);
            Class<?> classBook2 = ClassLoader.getSystemClassLoader().loadClass(cl);
            Book bk2 = (Book) classBook2.newInstance();
            System.out.println("reflectNewInstance book = " + bk2 + " " + bk2.getSize());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
