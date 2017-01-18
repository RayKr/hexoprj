/**
 * Created by jingz on 2017/1/18.
 */
public class Outer {

    class Inner {
        int m = 10;
    }

    public static void main(String[] args) {
        Inner inner = new Outer().new Inner();
        System.out.println(inner.m);
    }
}
