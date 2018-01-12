//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.util.Date;

public class Box2 {
    private int HEY_NO_FAIR_PEEKING = 0;
    public int[] pair;
    static boolean fuss = true;

    public Box2() {
        if (fuss) {
            System.out.println("Box2 run on " + new Date());
            fuss = false;
        }

        this.pair = new int[2];
    }

    public Box2(int a, int b) {
        if (fuss) {
            System.out.println("Box2 run on " + new Date());
            fuss = false;
        }

        this.pair = new int[2];
        this.pair[0] = a;
        this.pair[1] = b;
    }

    public static Box2 copy1(Box2 source) {
        Box2 result = new Box2();
        result.pair[0] = source.pair[0];
        result.pair[1] = source.pair[1];
        return result;
    }

    public static Box2 copy2(Box2 source) {
        Box2 result = new Box2();
        result.pair = source.pair;
        return result;
    }
}
