public class Puzzle0 {

    public static LetList a, b;

    public Puzzle0() {

        // puzzle 0
        //
        //
        //   a--+
        //      |
        //      V
        //      T -> O
        //           ^
        //           |
        //      Y ---+
        //      ^
        //      |
        //   b--+
        //

        a = new LetList();
        a.push('O');
        a.push('T');
        b = a.rest();
        b.push('Y');

    }
}
