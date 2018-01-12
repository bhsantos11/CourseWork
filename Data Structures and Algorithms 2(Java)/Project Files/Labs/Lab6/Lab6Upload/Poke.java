import java.io.*;

public class Poke {
    public static void main(String argv[]) {
        Puzzle0 p0 = new Puzzle0();
        Puzzle1 p1 = new Puzzle1();
        Puzzle2 p2 = new Puzzle2();
        Puzzle3 p3 = new Puzzle3();

        // Do your tests on p1.a, p1.b, p2.a, p2.b, p3.a, and p3.b below.

        // Example: Figuring out Puzzle0.
        System.out.println("\n**PUZZLE 0 Experiments**");
        System.out.println("p0.a is " + p0.a); // prints: -T-O-
        System.out.println("p0.b is " + p0.b); // prints: -Y-O-
        // The O might be in a shared Node
        p0.a.changeAll('O','X');
        System.out.println("p0.a is " + p0.a); // prints: -T-X-
        System.out.println("p0.b is " + p0.b); // prints: -Y-X-
        // Since p0.b's last Node's letter changed from an O to an X,
        // that Node must be shared between p0.a and p0.b.
        // So we have the digram given in Part I of the Lab writeup


        System.out.println("\n**PUZZLE 1 Experiments**");
        System.out.println("p1.a is "+ p1.a);
        System.out.println("p1.b is "+ p1.b);
        System.out.println("p1.c is "+ p1.c);

        System.out.println("\nChange all the a's in p1.b to i's");
        p1.b.changeAll('a','i');
        System.out.println("p1.a is "+ p1.a);
        System.out.println("p1.b is "+ p1.b);
        System.out.println("p1.c is "+ p1.c);

        System.out.println("\nChange all the h's in p1.b to x's");
        p1.b.changeAll('h','x');
        System.out.println("p1.a is "+ p1.a);
        System.out.println("p1.b is "+ p1.b);
        System.out.println("p1.c is "+ p1.c);

        System.out.println("\nChange all the t's in p1.b to z's");
        p1.b.changeAll('t','z');
        System.out.println("p1.a is "+ p1.a);
        System.out.println("p1.b is "+ p1.b);
        System.out.println("p1.c is "+ p1.c);

        // EXPLANATION
        // Since a is affected by all changes made to b all its nodes points to the nodes in b
        // since all of the nodes in c after the first h node are affected by changed made to b
            // all of the c nodes following its first h are in b



        // Now write down a box and arrow diagram for p1.a, p1.b, and p1.c.
        // Explain why your diagram *must* be correct.

        System.out.println("\n**PUZZLE 2 Experiments**");
        System.out.println("p2.a is "+ p2.a);
        System.out.println("p2.b is "+ p2.b);
        System.out.println("p2.c is "+ p2.c);

        System.out.println("\nChange all the o's in p2.b to x's");
        p2.b.changeAll('o','x');
        System.out.println("p2.a is "+ p2.a);
        System.out.println("p2.b is "+ p2.b);
        System.out.println("p2.c is "+ p2.c);

        System.out.println("\nChange all the k's in p2.b to y's");
        p2.b.changeAll('k','y');
        System.out.println("p2.a is "+ p2.a);
        System.out.println("p2.b is "+ p2.b);
        System.out.println("p2.c is "+ p2.c);

        System.out.println("\nChange all the o's in p2.c to a's");
        p2.c.changeAll('o','a');
        System.out.println("p2.a is "+ p2.a);
        System.out.println("p2.b is "+ p2.b);
        System.out.println("p2.c is "+ p2.c);

        // EXPLANATION
        // Since the last two nodes in a and c are affected by changed made to b that means that they have to be in b
        // Since the first o in c is not affected by the changes made to the "o"s in b that means that c has an o that is not in b
        // and since the o in a is not affected by changed made in the "o"s in c but not but, that means that that o is not in a
        // Since a has an o that is not affected by changes made in b it means that it has its own O

        if (p2.a.front==null)
            System.out.println("Hey, p2.a is empty");
        else
            System.out.println("The first letter in p2.a is: "
                    + p2.a.front.letter);

        // Etc.
        System.out.println("\n**PUZZLE 3 Experiments**");
        System.out.println("p3.a is "+ p3.a);
        System.out.println("p3.b.front is "+ p3.b.front.letter);
        System.out.println("p3.b.front.next is "+ p3.b.front.next.letter);
        System.out.println("p3.b.front.next.next is "+ p3.b.front.next.next.next.letter);
        Node t = p3.b.front;
        while(t != null){ // goes on forever
            System.out.println("t is " + t.letter);
            t = t.next;
        }
        //System.out.println("p3.c is "+ p3.c); cannot resolve this, aka c doesnt exist
        // System.out.println("p3.b is "+ p3.b); This causes bad things to happen.

        //EXPLANATION
        // a is empty as can be seen by the output of "-"
        // no matter how far we go o's next is always o, so we it has to point to itself

    }
}