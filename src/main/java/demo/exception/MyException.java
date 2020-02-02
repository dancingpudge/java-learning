package demo.exception;

/**
 * 1、异常时程序中的一些错误
 * 2、有些异常需要处理，有些不需要捕获处理
 * 3、JAVA中异常用对象来表示
 * 4、异常的来源  A:JAVA语言本身定义的基本异常 B:用户自定义
 * 5、Exception 程序可以处理的异常 Error 仅靠程序无法恢复
 * 6、运行时异常：尽量通过捕获异常来控制，而是开发调试时尽量避免
 *
 * @author liuh
 * @date 2020-02-02 15:21
 **/

public class MyException extends Throwable {

    /**
     * 异常转型
     *
     * @throws MyException
     */
    public void test() throws MyException {
        try {
            System.out.println("do something ......");
        } catch (Exception e) {
            throw new MyException();
        }
    }
}
