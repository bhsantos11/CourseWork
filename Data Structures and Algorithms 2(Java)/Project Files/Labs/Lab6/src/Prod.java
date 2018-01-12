import java.io.*;

public class Prod {
    // A debugging test of the LetList class.

    public static void main(String argv[]) {
        LetList a = new LetList();
        LetList b = new LetList();
        LetList c = new LetList();

        /////////////////////////////////////////////////////////////////
        a.push('o');
        a.push('o');
        a.push('f');

        System.out.println("Point 1");
        System.out.println("a: "+ a);

        /////////////////////////////////////////////////////////////////
        b = a.find('o');

        System.out.println("\nPoint 2");
        System.out.println("b: "+b);

        /////////////////////////////////////////////////////////////////
        c = a.rest();
        c = c.rest();

        System.out.println("\nPoint 3");
        System.out.println("c: "+c);

        /////////////////////////////////////////////////////////////////
        b.push('b');

        System.out.println("\nPoint 4");
        System.out.println("b: "+b);

        /////////////////////////////////////////////////////////////////
        b.changeAll('o','e');

        System.out.println("\nPoint 5");
        System.out.println("b: "+b);
        System.out.println("a: "+a);

        /////////////////////////////////////////////////////////////////
        c.changeAll('e','w');
        c.push('e');
        c.push('m');

        System.out.println("\nPoint 6");
        System.out.println("c: "+c);
        System.out.println("b: "+b);
        System.out.println("a: "+a);
        /////////////////////////////////////////////////////////////////
    }
}