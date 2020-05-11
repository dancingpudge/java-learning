package demo.gc;

public class fullgc {

    public static void main(String[] args) {
        ergonomicsGc();
    }

    public static void ergonomicsGc() {
        // 大对象直接放入老年代
        byte[] bigObj1 = new byte[1024 * 1024 * 160];
        // 下次创建新对象时，发现新生代内存不足；
        // 但是如果把新生代现有的对象挪到老年代之后，新生代即可容纳新对象。因此触发内存担保。
        byte[] bigObj2 = new byte[1024 * 1024 * 20];
    }
}
