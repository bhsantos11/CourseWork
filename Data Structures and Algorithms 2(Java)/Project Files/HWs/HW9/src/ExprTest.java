import java.util.*;

/**
 *  Tests for the Expr class
 **/
public class ExprTest {


    ////////////////////////////////////////////////////////////////////
    // Utility tests

    /** Tests if two Expr's are equal and fusses if they are not.
     * @param e1 the first Expr
     * @param e2 the second Expr
     */
    public static void checkEq(Expr e1,Expr e2) {
        if (e1.equals(e2))
            System.out.println("Passed. Both expressions = "+e1);
        else
            System.out.println("FAILED!! equality test for "+e1+" and "+e2);
    }

    /** Tests if an Expr evaluates to an expected value.
     * @param e the Expr for the test
     * @param actual the value of e.eval(varVals) from main
     * @param expected the expected value of the evaluation.
     */
    public static void evalTest(Expr e,double actual,double expected) {
        if (actual==expected)
            System.out.println("Passed. " + e + " evaluates to " + expected);
        else
            System.out.println("FAILED!! " + e + " evaluates to " + actual
                    + " but " + expected + " was expected");
    }

    ////////////////////////////////////////////////////////////////////
    /** Test of the Expr class
     * @param argv not used
     */
    public static void main(String argv[]){
        double[] vals = {0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, -1.0, -5.0, 0.125,
                10.0, 3.5, 2.1, 5.4, 14.0, 15.0, 16.0, 17.0, 18.0,
                19.0, 20.0, -0.3, 20.0, 2.0, 4.0, 100.0};


        Expr.Tag t1 = Expr.Tag.NUM;
        Expr.Tag t2 = Expr.Tag.VAR;
        System.out.println(t1.equals(t2));

        HashMap<Character,Double> varVals = new HashMap<Character,Double>(40);
        for(char c='a', i=0; c<='z'; c++, i++) varVals.put(c,vals[i]);

        for(char c='a', i=0; c<='z'; c++, i++) {
            System.out.print("varVals.get('"+c+"') = " + varVals.get(c)+"\t");
            if (i%3==2) System.out.println();
        }
        System.out.println("\n");

        Expr[] ex = new Expr[10];
        ex[0] = new Expr("( 2 + 3 )");
        ex[1] = new Expr("( ( 2 + 3 ) - 4 )");
        ex[2] = new Expr("( 2 + ( 3 * 4 ) )");
        ex[3] = new Expr("( ( 2 + 3 ) * 4 )");
        ex[4] = new Expr('x');
        ex[5] = new Expr('y');
        ex[6] = new Expr("( x + ( 3 * ( y / 5 ) ) )");
        ex[7] = new Expr("( ( ( w + ( x * ( ( 4 + 5 ) / 6 ) ) ) + ( 7 * 8 ) ) - y )");
        ex[8] = new Expr("( x + ( ( 12.0 - y ) * ( y + ( 3 * 2 ) ) ) )");
        ex[9] = new Expr("( ( z + 1.0 ) + ( ( 12.0 - y ) * ( ( z + 1.0 ) + y ) ) )");

        // // // // // // // // // // // // // // // // // // // //
        System.out.println("\n*** Tests for eval ***");
        double[] evalTarget = {5.0, 1.0, 14.0, 20.0, 2.0, 4.0, 4.4, 75.0, 82.0, 941.0};
        for(int i=0; i<ex.length; i++)
            evalTest(ex[i],ex[i].eval(varVals),evalTarget[i]);
        // for(Expr e : ex)  System.out.print(e.eval(varVals)+", ");


        System.out.println("\n*** Tests for fromPostfix ***");
        String[] fromPostfixTarget =
                {"2.0 3.0 +", "2.0 3.0 + 4.0 -", "2.0 3.0 4.0 * +", "2.0 3.0 + 4.0 *",
                        "x", "y", "x 3.0 y 5.0 / * +", "w x 4.0 5.0 + 6.0 / * + 7.0 8.0 * + y -",
                        "x 12.0 y - y 3.0 2.0 * + * +", "z 1.0 + 12.0 y - z 1.0 + y + * +"};
        for(int i=0; i<ex.length; i++)
            checkEq(ex[i],Expr.fromPostfix(fromPostfixTarget[i]));


        // // // // // // // // // // // // // // // // // // // //
        System.out.println("\n*** Tests for toInfix ***");
        for(Expr e : ex)
            checkEq(e,new Expr(e.toInfix()));


        // // // // // // // // // // // // // // // // // // // //
        System.out.println("\n*** Tests for toPostfix ***");
        for(Expr e : ex)
            checkEq(e,Expr.fromPostfix(e.toPostfix()));


        // // // // // // // // // // // // // // // // // // // //
        System.out.println("\n*** Tests for simplify ***");
        String[] simplTarget =
                {"5.0", "1.0", "14.0", "20.0", "x", "y",
                        "( x + ( 3.0 * ( y / 5.0 ) ) )", "( ( ( w + ( x * 1.5 ) ) + 56.0 ) - y )",
                        "( x + ( ( 12.0 - y ) * ( y + 6.0 ) ) )",
                        "( ( z + 1.0 ) + ( ( 12.0 - y ) * ( ( z + 1.0 ) + y ) ) )"};
        for(int i=0; i<ex.length; i++)
            checkEq(ex[i].simplify(),new Expr(simplTarget[i]));


        // // // // // // // // // // // // // // // // // // // //
        System.out.println("\n*** Tests for substitute ***");
        String[] substTarget =
                {"( 2.0 + 3.0 )", "( ( 2.0 + 3.0 ) - 4.0 )", "( 2.0 + ( 3.0 * 4.0 ) )",
                        "( ( 2.0 + 3.0 ) * 4.0 )", "x", "( z + 1.0 )",
                        "( x + ( 3.0 * ( ( z + 1.0 ) / 5.0 ) ) )",
                        "( ( ( w + ( x * ( ( 4.0 + 5.0 ) / 6.0 ) ) ) + ( 7.0 * 8.0 ) ) - ( z + 1.0 ) )",
                        "( x + ( ( 12.0 - ( z + 1.0 ) ) * ( ( z + 1.0 ) + ( 3.0 * 2.0 ) ) ) )",
                        "( ( z + 1.0 ) + ( ( 12.0 - ( z + 1.0 ) ) * ( ( z + 1.0 ) + ( z + 1.0 ) ) ) )"};
        for(int i=0; i<ex.length; i++)
            checkEq(Expr.subst(ex[i],'y',new Expr("( z + 1 )")),new Expr(substTarget[i]));

    }
}