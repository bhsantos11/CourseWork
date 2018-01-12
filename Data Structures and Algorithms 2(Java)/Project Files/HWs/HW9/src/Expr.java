import java.util.*;

/******************************************************************************
 * This is a class to represent expression trees.
 *
 * <p><b>Class invariants:</b>
 * <ol type=I>
 * <li>  <!-- Invariant I. -->
 *       When <code>tag = NUM</code> the <code>Expr</code> represents a
 *       number, in which case <code>num</code> is this number and
 *       <code>name = ' '</code> and <code>left=right=null</code>.
 * <li>  <!-- Invariant II. -->
 *       When <code>tag = VAR</code> the <code>Expr</code> represents a variable,
 *       in which <code>name</code> = the variable name,
 *       <code>num = 0.0</code> and <code>left=right=null</code>.
 * <li>  <!-- Invariant III. -->
 *       When <code>tag = OP</code> the <code>Expr</code> represents an expression
 *       of the form: ( e1 op e2 ) where <code>op</code> is one of
 *       <code>'+', '-', '*', '/'</code> in which case:
 *       <ul>
 *         <li> <code>name = op</code>,
 *         <li> <code>num = 0.0</code>,
 *         <li> <code>left</code> is a reference to a representation of e1, and
 *         <li> <code>right</code> is a reference to a representation of e2.
 *       </ul>
 * </ol>
 *
 * @author Bernardo Bicalho Hauer Santos
 *
 * @version
 *   Oct 22, 2015
 ****************************************************************************/

public class Expr {
    /** Tags for labeling different sorts of Expressions. */
    public static enum Tag {
        /** tag for number expressions */
        NUM,
        /** tag for variable expressions */
        VAR,
        /** tag for expressions of the form (exp1 op exp2) */
        OP
    };

    // Instance variables
    /** A tag describing the kind of expression */
    private Tag    tag;
    /** For VAR and OP Exprs, this contains the variable or operator name */
    private char   name;
    /** For NUM Exprs, this contains the number */
    private double num;
    /** For OP Exprs, this references the left subexpression */
    private Expr   left;
    /** For OP Exprs, this references the right subexpression */
    private Expr   right;

    /////////////////////////////////////////////////////////////////////////////
    // Constructors
    /** Builds an Expr for number <code>n</code>
     * @param n  the number for the Expr
     */
    public Expr(double n) {
        tag = Tag.NUM;  name = ' ';  num = n;  left = right = null;
    }

    /////////////////////////////////////////////////////////////////////////////
    /** Builds an Expr for variable v
     *  @param v the variable tag (one of 'a', ... , 'z')
     */
    public Expr(char v) {
        if ( !Character.isLowerCase(v) )
            throw new IllegalArgumentException(
                    "A variable expression must a lowercase letter, not "+v);
        tag = Tag.VAR;  name = v;  num = 0.0;  left = right = null;
    }

    /////////////////////////////////////////////////////////////////////////////
    /** Builds an Expr with operator <code>op</code>, left child
     *   <code>lft</code>, and right child <code>rght</code>.
     * @param op   the operator
     * @param lft  the left child
     * @param rght the right child
     */
    public Expr(Expr lft, char op, Expr rght) {
        if ( !isOp(op) )
            throw new IllegalArgumentException(
                    "A operator must +, -, *, or /,  not "+op);
        tag = Tag.OP; name = op; num = 0.0;  left = lft;  right = rght;
    }

    /////////////////////////////////////////////////////////////////////////////
    /** Construct an expression tree from an infix expression.
     * @param exp   an infix expression string
     *  <p> <b><i>Important:</i></b> In the String <code>exp</code>, parens, variables, numbers, and operators
     *    should be surrounded with spaces.
     *    If you omit these spaces, errors will result.
     * <p>
     *    E.g., "( ( 2 + 3 ) *  4 )" is OK, but each of "((2+3)*4)", "(( 2 + 3 ) * 4 )",
     *    and "( ( 2 +3 ) * 4 )" will cause an error.
     **/
    public Expr(String exp) {
        Expr ans = readTree(new Scanner(exp));
        tag      = ans.tag;
        num      = ans.num;
        name     = ans.name;
        left     = ans.left;
        right    = ans.right;
    }

    /** Helper method for the constructor above */
    private static Expr readTree(Scanner inscan) {
        String token = inscan.next();
        char first = token.charAt(0);

        // check for a variable
        if (Character.isLowerCase(first))
            return new Expr(first);

            // check for a number
        else if (Character.isDigit(first))
            return new Expr(Double.valueOf(token));

            // Check for an expression of the form "( e1 op e2 )"
        else if (first == '(') {
            Expr e1 = readTree(inscan);
            char op = (inscan.next()).charAt(0);
            Expr e2 = readTree(inscan);
            inscan.next(); // reads past the ')'
            return new Expr(e1,op,e2);
        }

        else
            throw new IllegalArgumentException("Strange input: "+token);
    }

    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    // Instance Methods (provided)

    /////////////////////////////////////////////////////////////////////////////
    /** Count the number of leaves (variables and numbers) in an Expr.
     * @return the number of leaves (variables and numbers) in this Expr.
     */
    public int countLeaves() {
        switch ( tag ) {
            case NUM:
                return 1;
            case VAR:
                return 1;
            default: // the OP case
                return (left.countLeaves() + right.countLeaves());
        }
    }

    /////////////////////////////////////////////////////////////////////////////
    /** An equality test for Expr's.
     * @param obj the reference object with which to compare.
     * @return true iff <code>this</code> object is the same as
     *         the <code>obj</code> argument
     */
    public boolean equals(Object obj) {
        if ( (obj == null) || !(obj instanceof Expr) ) return false;
        if ( this == obj ) return true;
        Expr that = (Expr)obj;
        if ( that.tag != this.tag ) return false;
        switch ( that.tag ) {
            case VAR: return (this.name==that.name);
            case NUM: return (this.num==that.num);
            default: // the OP case
                return (this.name==that.name
                        && left.equals(that.left)
                        && right.equals(that.right));
        }
    }

    /////////////////////////////////////////////////////////////////////////////
    /** Constructs a string representation of this Expr.
     * @return a string representation of this Expr
     */
    public String toString() {
        return this.toInfix(); // This won't work until toFix does.
    }

    /////////////////////////////////////////////////////////////////////////////
    /** Construct an expression tree from a postfix expression.
     * @param exp   a postfix expression string
     *  <p> <b><i>Important:</i></b> In the String <code>exp</code>,
     *    parens, variables, numbers, and operators should be surrounded
     *    with spaces.  If you omit these spaces, errors <i>will</i> result.
     * <p>
     *    E.g., "2 3 + 4 *" is OK, but both of "2 3+ 4 +" and "2 3 + 4+"
     *    will cause an error.
     * @return the expression tree for this postfix expression
     **/
    public static Expr fromPostfix(String exp) {
        Scanner inscan  = new Scanner(exp);
        Stack<Expr> stk = new Stack<Expr>();

        while (inscan.hasNext()) {
            String token = inscan.next();
            char first   = token.charAt(0);

            if (Character.isDigit(first))
                stk.push(new Expr(Double.valueOf(token)));

            else if (Character.isLowerCase(first))
                stk.push(new Expr(first));

            else if (isOp(first)) {
                Expr e2 = stk.pop();
                Expr e1 = stk.pop();
                stk.push(new Expr(e1,first,e2));
            }

            else throw new IllegalArgumentException("Strange input: "+token+" in "+exp);
        }
        return stk.pop();
    }

    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    // Utilities (provided)

    /////////////////////////////////////////////////////////////////////////////
    /** Tests if a character <code>c</code> is one of '+', '-', '*', '/'
     * @param c  the char to be tested
     * @return true iff c is one of +,-,*,/
     */
    public static boolean isOp(char c) {
        return (("+-*/").indexOf(c) >= 0);
    }

    /////////////////////////////////////////////////////////////////////////////
    /** Checks that the Expr satisfies the class invariants.
     * <strong>For debugging.</strong>
     */
    public void checkTreeProps() {
        switch ( tag ) {
            case NUM:
                if (name!=' ') System.out.println("Number Expr with name="+name);
                if (left!=null || right!=null)
                    System.out.println("Number Expr with non-null left or right");
                break;
            case VAR:
                if (num!=0.0) System.out.println("Variable Expr with num="+num);
                if (left!=null || right!=null)
                    System.out.println("Variable Expr with non-null left or right");
                break;
            default: // tag == OP
                if (num!=0.0) System.out.println("Op Expr with num="+num);
                if (left==null)
                    System.out.println("Problem: Op Expr with null left child");
                else
                    left.checkTreeProps();

                if (right==null)
                    System.out.println("Problem: Op Expr with null right child");
                else
                    right.checkTreeProps();
                break;
        }
    }

    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    // Instance Methods (for the homework)

    /////////////////////////////////////////////////////////////////////////////
    /** Evaluates an expression tree.
     * <p> &nbsp; &nbsp; &nbsp; <strong><i>PART OF THE HOMEWORK!</i></strong></p>
     * @param env  a table of variable values
     * @return     the value of the expression
     **/
    public double eval(HashMap<Character,Double> env) {
        System.out.println("eval: Fix me!!");
        return 0.0;
    }

    /////////////////////////////////////////////////////////////////////////////
    /** Converts the expression tree to the infix form of the expression.
     * <p> &nbsp; &nbsp; &nbsp; <strong><i>PART OF THE HOMEWORK!</i></strong>
     * @return a String consisting of the infix form of the expression
     **/
    public String toInfix() {

    }

    /////////////////////////////////////////////////////////////////////////////
    /** Converts the expression tree to a postfix form of the expression.
     * <p> &nbsp; &nbsp; &nbsp; <strong><i>PART OF THE HOMEWORK!</i></strong>
     *  @return  a String consisting of the postfix form of the expression
     **/
    public String toPostfix() {
        System.out.println("toPostfix: Fix me!!");
        return "0.0";
    }

    /////////////////////////////////////////////////////////////////////////////
    /** Substitute Expr <code>e2</code> for variable <code>v</code> in <code>e1</code>.
     * <p> &nbsp; &nbsp; &nbsp; <strong><i>PART OF THE HOMEWORK!</i></strong>
     * @param e1 the expression to be substituted into
     * @param v the variable to be substituted for
     * @param e2 the expression to be substituted for v
     * @return the result of doing the substitution
     */
    public static Expr subst(Expr e1, char v, Expr e2) {
        System.out.println("subst: Fix me!!");
        return new Expr(0.0);
    }

    /////////////////////////////////////////////////////////////////////////////
    /** Constructs a simplification of the expression.
     * <p> &nbsp; &nbsp; &nbsp; <strong><i>PART OF THE HOMEWORK!</i></strong>
     * @return the simplified Expr
     **/
    public Expr simplify() {
        System.out.println("simplify: Fix me!!");
        return new Expr(0.0);
    }

}