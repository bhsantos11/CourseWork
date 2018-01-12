import java.util.*;

/******************************************************************************
 * This is binary search tree class over items of type T.  It is roughly
 * along the lines of the class described in section 6.2 of Open Data Structures.
 * The one big change is that this class uses a sentinel node ANCHOR, where
 * <ul>
 *   <li> ANCHOR.left = the actual root of the tree
 *   <li> ANCHOR.parent = ANCHOR.right = ANCHOR.data = null
 * </ul>
 * We compare data fields via the method <code>compare</code> below where
 * we arrange that null acts like +&infin;, so that the ANCHOR node is the
 * rightmost/largest node in the (extended) tree.
 *
 * @author Bernardo Bicalho Hauer Santos
 *
 * @version
 *   November 23, 2017
 ****************************************************************************/


public class BSTree<T extends Comparable<T>> {
    //////////////////////////////////////////////////////////////////////
    // instance variables
    /** The sentinel node for of the tree ANCHOR.left is the actual root */
    protected BSNode<T> ANCHOR;
    /** The number of nodes (elements) currently in the tree */
    protected int n;

    //////////////////////////////////////////////////////////////////////
    // Constructor
    public BSTree() { ANCHOR = new BSNode<T>(null);  n = 0; }

    //////////////////////////////////////////////////////////////////////
    /** compares two T values where null is treated as +infinity
     * @param x first thing to compare
     * @param y second thing to compare
     * @return (i) a negative number if x &lt; y; (ii) 0, if x = y;
     *   (iii) a positive number, if x &gt; y
     */
    public int compare(T x,T y) {
        if (x==null && y==null) return 0; // null == null
        if (y==null) return -1;           // y < null
        if (x==null) return 1;            // null > y
        return x.compareTo(y);
    }

    //////////////////////////////////////////////////////////////////////
    /** Search for a value in the tree
     * @param u the BSNode to start the BST-search from
     * @param x the value to search for
     * @return the last node on the search path for x
     */
    protected BSNode<T> findLast(BSNode<T> u,T x) {
        if (u==null) return null;
        for(;;) {
            int cmp = compare(x,u.data);
            if (cmp==0 || (cmp<0 && u.left==null) || (cmp>0 && u.right==null))
                return u;
            if (cmp<0)  u = u.left; else u = u.right;
        }
    }
    //////////////////////////////////////////////////////////////////////
    /** Add a Node with value x to the BST (if it is not already there).
     * @param x the value to add
     * @return true iff a node was added.
     */
    protected boolean add(T x) {
        BSNode<T> u = findLast(ANCHOR,x);
        int cmp = compare(x,u.data);
        if (cmp==0)    return false;
        if (cmp<0)     u.left  = new BSNode<T>(x,null,null,u);
        else /*cmp>0*/ u.right = new BSNode<T>(x,null,null,u);
        n++;
        return true;
    }

    //////////////////////////////////////////////////////////////////////
    /** Finds the leftmost node below node <code>u</code>
     * @param u the node to start the search
     * @return the leftmost node below node <code>u</code>
     */
    protected BSNode<T> leftmostBelow(BSNode<T> u) {
        if (u==null) return null;
        while (u.left != null) u = u.left;
        return u;
    }
    //////////////////////////////////////////////////////////////////////
    /** Returns the smallest value in the tree
     * @return the smallest value in the tree
     */
    public T smallest() { return (leftmostBelow(ANCHOR.left)).data; }
    //////////////////////////////////////////////////////////////////////
    /** Finds the rightmost node below node <code>u</code>
     * @param u the node at which to start the search
     * @return the rightmost node below node <code>u</code>
     */
    protected BSNode<T> rightmostBelow(BSNode<T> u) {
        if (u==null) return null;
        while (u.right != null) u = u.right;
        return u;
    }
    //////////////////////////////////////////////////////////////////////
    /** Returns the largest value in the tree
     * @return the largest value in the tree
     */
    public T largest() { return (rightmostBelow(ANCHOR.left)).data; }
    //////////////////////////////////////////////////////////////////////
    /** Delete the node with key value <code>x</code>, if it has such a node.
     * @param x the value to be deleted
     * @return true iff a node was actually deleted.
     */
    public boolean delete(T x) {
        if (x==null) return false;
        BSNode<T> u = findLast(ANCHOR,x);
        if (compare(x,u.data)!=0) return false;
        remove(u);
        return true;
    }
    /** Remove the node u --- ASSUMING u has at most one child
     * @param u the node to be removed
     */
    protected void splice(BSNode<T> u) {
        if (u.left==null && u.right==null) { // u is a leaf
            if (u.parent.left==u) u.parent.left  = null;
            else                 u.parent.right = null;
        }
        else if (u.left!=null) { // so u.right == null
            u.left.parent = u.parent;
            if (u.parent.left==u) u.parent.left  = u.left;
            else                 u.parent.right = u.left;
        }
        else { // u.right!=null  // so u.left == null
            u.right.parent = u.parent;
            if (u.parent.left==u) u.parent.left  = u.right;
            else                 u.parent.right = u.right;
        }
        n--;
    }
    /** Remove the node u from the binary search tree
     * @param u the node to be removed
     */
    protected void remove(BSNode<T> u) {
        if (u.left == null || u.right == null)
            splice(u);
        else {
            BSNode<T> w = leftmostBelow(u.right);
            u.data = w.data;
            splice(w);
        }
    }

    //////////////////////////////////////////////////////////////////////
    /** Return a copy of this BSTree
     * @return a copy of this BSTree
     */
    public BSTree<T> copy() {
        BSTree<T> cp  = new BSTree<T>();
        cp.ANCHOR.left = copy(ANCHOR.left,cp.ANCHOR);
        cp.n          = n;
        return cp;
    }
    private BSNode<T> copy(BSNode<T> u,BSNode<T> p) {
        if (u==null) return null;
        BSNode<T> newu = new BSNode<T>(u.data,null,null,p);
        newu.left      = copy(u.left,newu);
        newu.right     = copy(u.right,newu);
        return newu;
    }

    //////////////////////////////////////////////////////////////////////
    /** Return the in-order successor of u in the tree, if any
     * @param u the node to find the successor of
     * @return the in-order successor of u in the tree which
     * is null if u is the rightmost node in the tree.
     */
    protected BSNode<T> successor(BSNode<T> u) {
        if (u==null || u==ANCHOR) return null;
        if (u.right!=null) return leftmostBelow(u.right);
        BSNode<T> child = u;
        BSNode<T> p = child.parent;
        while (p!=null && child == p.right) {
            child = p;
            p     = child.parent;
        }
        return p;
    }
    //////////////////////////////////////////////////////////////////////
    /** <h2><strong>HOMEWORK PROBLEM</strong></h2>
     *
     * @param u the node to find the predecessor of
     * @return the in-order predecessor of u in the tree, which
     * is null if u is the leftmost node in the tree.
     */
    protected BSNode<T> predecessor(BSNode<T> u) { //literally the same method as successor but with right and left switched
        if (u==null || u==ANCHOR) return null;
        if (u.left!=null) return rightmostBelow(u.left);
        BSNode<T> child = u;
        BSNode<T> p = child.parent;
        while (p!=null && child == p.left) {
            child = p;
            p     = child.parent;
        }
        return p;
    }

    //////////////////////////////////////////////////////////////////////
    /** <h2><strong>HOMEWORK PROBLEM</strong></h2>
     * Tests whether two BSTrees are equal
     * @param obj the BSTree to compare to this tree
     * @return true if the trees are the same, false if they're not
     */
    public boolean equals(Object obj) {
        if (obj == this) return true; // If comparing to itself
        if (obj == null || obj.getClass() != this.getClass())  return false; // If the objects are of different classes

        @SuppressWarnings({"unchecked"})
        BSTree<T> other = (BSTree<T>) obj;
        return eq(this.ANCHOR,other.ANCHOR);
    }

    /** Tests whether the subtrees rooted at u and v are equal.
     * @param u the first BSNode
     * @param v the second BSNode
     * @return true iff the subtrees rooted at u and v are equal.
     */
    private boolean eq(BSNode<T> u,BSNode<T> v) {
        //Logically what these statements is first check if they're both null(ie empty lists) if they are they are therefore equal so it returns true
        //  the second if statement is only tested if the first one is false(ie one of the lists is not empty) in which case if either list is false then they are not equal because the other is logically not empty
        if(u == null && v == null) return true;
        else if(u == null || v == null) return false;

        //Return
            // Compares if the current nodes are equal, if they are not equal the tree is false
            // Applies recursion to check the left of both trees
            // Applies recursion to check the right of both trees
        return((compare(u.data, v.data) == 0) && eq(u.left,v.left) && eq(u.right, v.right));

    }
    //////////////////////////////////////////////////////////////////////
    /** Constructs an in-order list of the value in this tree that are
     * between small and large - inclusive.
     * <h2><strong>HOMEWORK PROBLEM</strong></h2>
     * @param small the left-end of the interval of values
     * @param large the right-end of the interval of values
     * @return an in-order list of all the values in the tree that are between
     * small and large - inclusive.
     */
    // Logical Assumptions if (p.data.compareTo(small) > 0) is true then p.data is bigger than small
    protected ArrayList<T> between(T small, T large) {
        /* Begging of possible iterative approach
        ArrayList<T> ans = new ArrayList<T>();
        if(this.ANCHOR.left == null) return ans; // If the tree is empty it will return an empty list  !might not be needed
        BSNode<T> p = this.ANCHOR.left;
        if((p.data.compareTo(small) > 0) && (p.data.compareTo(large) < 0)) ans.add(p.data);
        */
        // Attempting recursive approach
        //p has to be a possible node to be returned
        return between(ANCHOR.left, small, large);
    }

    private ArrayList<T> between(BSNode<T> u,T small, T large) {
        ArrayList<T> ans = new ArrayList<T>();
        if(u == null) return ans;

        // Recursive
            // Recursively adds left branch of u
            // Checks and adds current U if its in range
            // Recursively adds right brach of u
        // We go left, u, right: as to be in order the smallest must be added before the u value, which must be added before the right or bigger values
        if(u.left != null) {ans.addAll(between(u.left, small, large)); }
        if((u.data.compareTo(small) > 0) && (u.data.compareTo(large) < 0)) { ans.add(u.data); }
        if(u.right != null) {ans.addAll(between(u.right, small, large));}




        return ans;
    }

    ///////////////////////////////////////////////////////////
    /** <h2><strong>HOMEWORK PROBLEM</strong></h2> Constructs a listIterator starting at the left or right end of the BSTree.
     * @param leftToRight if <code>true</code>, start the iterator at the leftmost
     * node of the tree, if <code>false</code>, start the iterator at the
     * rightmost node of the tree (i.e., <code>ANCHOR</code>).
     * @return a new a listIterator with indicated starting position
     */
    public ListIterator<T> iterator(boolean leftToRight) {
        return new Iterator(leftToRight);
    }
    ///////////////////////////////////////////////////////////
    class Iterator implements ListIterator<T> {
        /** p is the node whose value is returned by next() */
        BSNode<T> p;
        /** last is the last node whose value was returned by next() or previous() */
        BSNode<T> last;
        /** leftmost is the left-most element of the tree */
        BSNode<T> leftmost;

        // CONSTRUCTORS
        Iterator(boolean leftToRight) {
            last     = null;
            leftmost = leftmostBelow(ANCHOR);
            p        = (leftToRight)?leftmost:ANCHOR; //If left to right is true p is bottom left otherwise its ANCHOR
        }
        // Ass. both hasNext and hasPrevious are dependent on next and previous
        public boolean hasNext() {
            if ((last == null) && (p.parent != null)) return true; // First node and has parent
            else if((last == null)) return false; // First node and has no parent
            if((last.data.compareTo(p.data)) < 0) { //last is smaller than p so we are going from left to right
                if(p!=ANCHOR) return true;
            }


            System.out.println("\n False");
            System.out.println("p.data = " + p.data);
            System.out.println("last.data = " + last.data);
            return false;
        }

        public boolean hasPrevious()  { return false; } // FIX

        public T next() {
            BSNode<T> lastP = last;
            last = p;
            if(lastP == null){
                p = p.parent;// For first node
                return last.data;
            }
            if(lastP.data.compareTo(p.data) < 0) {//going in order
                if (p.right!=null) p = leftmostBelow(p.right);
                BSNode<T> child = p;
                BSNode<T> p = child.parent;
                while (p!=null && child == p.right) {
                    child = p;
                    p     = child.parent;
                }
            }

            return last.data;
        } // FIX
        public T previous()           {
            return null;
        } // FIX

        public int nextIndex()        { throw new UnsupportedOperationException(); }
        public int previousIndex()    { throw new UnsupportedOperationException(); }
        public void add(T x)          { throw new UnsupportedOperationException(); }
        public void set(T x)          { throw new UnsupportedOperationException(); }
        public void remove()          { throw new UnsupportedOperationException(); }
    }

    //////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    // Testing utilities
    //////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    /** Print the tree */
    private void show() {
        if (ANCHOR.left==null) System.out.println("--");
        else ANCHOR.left.show(0);
    }

    //////////////////////////////////////////////////////////////////////
    /** Construct an ArrayList from a string
     * @param str the string to build the list from
     * @return the constructed list
     */
    private static ArrayList<Character> make(String str) {
        ArrayList<Character> ans = new ArrayList<Character>();
        for(int i=0; i<str.length();i++) ans.add(str.charAt(i));
        return ans;
    }
    //////////////////////////////////////////////////////////////////////
    // Test for the between method
    private static void betweenTest(BSTree<Character> t,
                                    Character small,
                                    Character large,
                                    String target) {
        ArrayList<Character> result = t.between(small,large);
        ArrayList<Character> targ = make(target);
        System.out.print("bst1.between('"+small+"','"+large+"') = " + result);
        if (result.equals(targ))
            System.out.println("\tCheck");
        else
            System.out.println("\tWRONG, should be = " + targ);

    }

    //////////////////////////////////////////////////////////////////////
    // Test for the equals method
    private static void eqTest(BSTree<Character> t1,
                               BSTree<Character> t2,
                               boolean           target) {
        boolean result = t1.equals(t2);
        System.out.print("t1.equals(t2) = " + result);
        if (result==target)
            System.out.println("\tCheck");
        else
            System.out.println("\tWrong");
    }

    //////////////////////////////////////////////////////////////////////
    // Test program for the class
    public static void main(String[] argv) {
        char[] cs = {'f','l','u','f','f','y',
                'k','i','t','t','e','n',
                't','e','a',
                'p','a','r','t','y'};

        BSNode<Character> u;
        ArrayList<Character> lst  = make("");
        ArrayList<Character> lst1 = make("aefiklnprtuy");
        ArrayList<Character> lst2 = make("yutrpnlkifea");

        BSTree<Character> bst1 = new BSTree<Character>();
        for (char c : cs) bst1.add(c);
        bst1.show();

        //
        // Testing for between
        //

        System.out.println("-----------------------------------------");
        betweenTest(bst1,'b','d',"");
        betweenTest(bst1,'z','a',"");
        betweenTest(bst1,'c','q',"efiklnp");
        betweenTest(bst1,'m','z',"nprtuy");
        System.out.println("-----------------------------------------");

        //
        // Testing for equals
        //

        BSTree<Character> bst2 = new BSTree<Character>();
        BSTree<Character> bst3 = new BSTree<Character>();
        // bst2.show();    bst3.show();
        System.out.print("equals test 1:\t"); eqTest(bst2,bst3,true);
        bst2.add('m');
        System.out.print("equals test 2:\t"); eqTest(bst2,bst3,false);
        System.out.print("equals test 3:\t"); eqTest(bst3,bst2,false);
        bst3.add('x');
        System.out.print("equals test 4:\t"); eqTest(bst2,bst3,false);
        bst3.delete('x'); bst3.add('m');
        System.out.print("equals test 5:\t"); eqTest(bst3,bst2,true);
        bst2.add('f'); bst2.add('c'); bst2.add('k'); bst2.add('p');
        bst3.add('f'); bst3.add('c'); bst3.add('k'); bst3.add('p');
        System.out.print("equals test 6:\t"); eqTest(bst3,bst2,true);
        bst2.delete('k');
        System.out.print("equals test 7:\t"); eqTest(bst3,bst2,false);
        bst2.add('j');
        System.out.print("equals test 8:\t"); eqTest(bst3,bst2,false);


        System.out.println("-----------------------------------------");

        //
        // Testing for predecessor
        //

        System.out.println("Inorder traversals using successor and predecessor");

        System.out.print("\tIn order (left to right): ");
        u = bst1.leftmostBelow(bst1.ANCHOR);

        while (u!=bst1.ANCHOR) {
            lst.add(u.data);
            u = bst1.successor(u);
        }
        System.out.println(lst);
        if (lst.equals(lst1)) System.out.println("\t\tCheck");
        else System.out.println("\t\tWrong, it should be "+lst1);

        lst.clear();
        System.out.print("\tIn order (right to left): ");
        u = bst1.rightmostBelow(bst1.ANCHOR.left);
        while (u!=null) {
            lst.add(u.data);
            u = bst1.predecessor(u);
        }
        System.out.println(lst);
        if (lst.equals(lst2)) System.out.println("\tCheck");
        else System.out.println("\t\tWrong, it should be "+lst2);

        System.out.println("-----------------------------------------");

        //
        // Testing for iterator
        //

        System.out.println("Inorder traversals using the ListIterator");

        ListIterator<Character> it;

        lst.clear();
        System.out.print("\tIn order (left to right): ");
        it = bst1.iterator(true);
        while (it.hasNext()) lst.add(it.next());
        System.out.println(lst);
        if (lst.equals(lst1)) System.out.println("\t\tCheck");
        else System.out.println("\t\tWrong, it should be "+lst1);

        lst.clear();
        System.out.print("\tIn order (right to left): ");
        it = bst1.iterator(false);
        while (it.hasPrevious()) lst.add(it.previous());
        System.out.println(lst);
        if (lst.equals(lst2)) System.out.println("\tCheck");
        else System.out.println("\t\tWrong, it should be "+lst2);

    }
    //////////////////////////////////////////////////////////////////////
}
/*
if(last == null){// First node
                System.out.println("First node, p: " + p.data);
                last = p;
                p = p.parent;
                return last.data;
            }

            last = p;


            if((lastP.data.compareTo(p.data)) < 0) { //last is smaller than p so we are going from left to right

                if(lastP == p.left){// when last is left child
                    if(p.right != null) p = p.right; //last one was left child and it has a right so we go right
                    else p = p.parent; //last one was left child but it does not have a right so we go up
                }
                else if(lastP == p.parent){ // When last is parent
                    if(p.left != null) p = p.left;//last was parent and it has a left node that should be unvisited
                    else if(p.right !=null) p = p.right; // last was parent and has no left node
                    else p = p.parent;
                }
                else if(lastP == p.right){
                    p = p.parent;
                }
//
            }
            else if((last.data.compareTo(p.data)) > 0) { //last is bigger than p so we are going from right to left

            }

////////////////////////////////////////////SECOND VERSION/////////////////////////////////////////////////////////////////////
BSNode<T> lastP = last;
            if (last == null) {// First node
                System.out.println("First node, p: " + p.data);
                last = p;
                p = p.parent;
                return last.data;
            } else if (p == ANCHOR.left){//here we're "crossing" the root
                last = p;

            }

            last = p;


            if((lastP.data.compareTo(p.data)) < 0) { //last is smaller than p so we are going from left to right

                if(lastP == p.left){// when last is left child
                    if(p.right != null) p = p.right; //last one was left child and it has a right so we go right
                    else p = p.parent; //last one was left child but it does not have a right so we go up
                }
                else if(lastP == p.parent){ // When last is parent
                    if(p.left != null) p = p.left;//last was parent and it has a left node that should be unvisited
                    else if(p.right !=null) p = p.right; // last was parent and has no left node
                    else p = p.parent;
                }
                else if(lastP == p.right){
                    p = p.parent;
                }
//
            }
            else if((last.data.compareTo(p.data)) > 0) { //last is bigger than p so we are going from right to left

            }

 */