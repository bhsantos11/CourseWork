//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.util.Date;

public class Box1 {
    private int HEY_NO_FAIR_PEEKING = 0;
    static boolean fuss = true;
    private static int a;
    private int b;

    public Box1() {
        if (fuss) {
            System.out.println("Box1 run on " + new Date());
            fuss = false;
        }

        a = this.b = 0;
    }

    public void setA(int v) {
        a = v;
    }

    public void setB(int v) {
        this.b = v;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return this.b;
    }
}
