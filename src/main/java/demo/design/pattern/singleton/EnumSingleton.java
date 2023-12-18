package demo.design.pattern.singleton;

/**
 * @description: 通过反编译代码可以看到，枚举底层其实还是class，<p/>
 * 枚举元素是被声明成public static final的成员变量（可以通过类名直接调用），并且在static静态代码块中一起初始化了，<p/>
 * 这就解释了为什么第一次调用枚举类的时候，构造代码块和构造方法执行次数会和枚举元素相等，因为第一次加载类的时候就全部初始化了。<p/>
 * <p/>
 * 由于java类的加载和初始化过程都是线程安全的，所以创建一个enum类型是线程安全的
 * ————————————————
 * @author: LiuH
 * @date: 2023/12/18 08:38
 */
public class EnumSingleton {
}
