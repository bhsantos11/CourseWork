//package ods;
import java.util.AbstractSequentialList;
import java.util.ListIterator;

/** An implementation of the List interface as a doubly-linked circular list.
 * Two sentinel nodes, AHEAD and BEHIND, are used to simplify the code.
 * We use the convention that <ul>
 * <li><code>AHEAD.next = the first Node in the list</code> and
 * <li><code>BEHIND.prev = the last Node in the list</code>
 * <li><code>n</code> = the number of elements in the list.
 * </ul>
 * We also have: <ul>
 * <li><code>AHEAD.prev = BEHIND.next = null</code></ul>
 * When the DLList is empty we have:
 *  <ul>
 * <li><code>AHEAD.next = BEHIND</code>
 * <li> <code>BEHIND.prev = AHEAD</code>
 * <li><code>n=0</code>.
 * </ul>
 *
 * @author Bernardo Santos
 * @param <T> the type of elements stored in the list
 */
public class DLList<T> extends AbstractSequentialList<T> {
    /** Internal Node class */
    class Node {
        T val;
        Node prev, next;
    }

    /** Number of nodes in the list */
    protected int n;

    /** We use the convention that <ul>
     * <li><code>AHEAD.next = the first Node in the list</code> and
     * <li><code>BEHIND.prev = the last Node in the list</code></ul>
     * We also have: <ul>
     * <li><code>AHEAD.prev = BEHIND.next = null</code></ul>
     * Also, when the DLList is empty we have:
     *  <ul>
     * <li><code>AHEAD.next = BEHIND</code> and <code>BEHIND.prev = AHEAD</code> </ul>
     */
    /** The AHEAD senteniel node */
    protected Node AHEAD;
    /** The BEHIND senteniel node */
    protected Node BEHIND;

    /** Construct an empty DDList */
    public DLList() {
        AHEAD  = new Node();
        BEHIND = new Node();
        clear();
    }
    ///////////////////////////////////////////////////////////////////////
    // The Node utility methods
    ///////////////////////////////////////////////////////////////////////

    /** Add a new node u before the node w
     * @param w   the node to insert the new node before
     * @param u   the node to be inserted
     * @return    the inserted node
     */
    protected Node addBefore(Node w, Node u) {
        u.prev      = w.prev;
        u.next      = w;
        w.prev = u;
        u.prev.next = u;
        n++;
        return u;
    }

    ///////////////////////////////////////////////////////////////////////
    /** Add a new node containing x before the node w
     * @param w   the node to insert the new node before
     * @param x   the value to store in the new node
     * @return    the newly created and inserted node
     */
    protected Node addBefore(Node w, T x) {
        Node u = new Node();
        u.val  = x;
        return addBefore(w,u);
    }

    ///////////////////////////////////////////////////////////////////////
    /** Remove the node w from the list
     * @param w  the node to remove
     */
    protected void remove(Node w) {
        w.prev.next = w.next;
        w.next.prev = w.prev;
        n--;
    }

    ///////////////////////////////////////////////////////////////////////
    /** Get the i'th node in the list
     * @param i the index of the node to get
     * @return the node with index i
     */
    protected Node getNode(int i) {
        Node p = null;
        if (i < n / 2) {
            p = AHEAD.next;
            for (int j = 0; j < i; j++)
                p = p.next;
        } else {
            p = BEHIND;
            for (int j = n; j > i; j--)
                p = p.prev;
        }
        return p;
    }

    ///////////////////////////////////////////////////////////
    /** Constructs a listIterator starting at position <code>i</code>.
     * @param i the position to start the listIterator
     * @return a new a listIterator starting at position <code>i</code>
     * @throws IndexOutOfBoundsException when <code>i&lt;0</code> or <code>i&ge;n</code>
     */
    public ListIterator<T> listIterator(int i) {
        if (i < 0 || i > n) throw new IndexOutOfBoundsException();
        return new Iterator(this, i);
    }

    ///////////////////////////////////////////////////////////
    /** Returns the size of this list
     * @return the size of this list
     */
    public int size() {
        return n;
    }

    ///////////////////////////////////////////////////////////
    /** Adds an element to the end of the list
     * @param x the value to add
     * @return true (to be consistent with AbstractSequentialList)
     */
    public boolean add(T x) {
        addBefore(BEHIND, x);
        return true;
    }

    ///////////////////////////////////////////////////////////
    /** Removes the node at the <code>i</code>-th position in this list.
     * @param i the index of the node to be removed
     * @return the value of the node removed
     * @throws IndexOutOfBoundsException when <code>i&lt;0</code> or <code>i&ge;n</code>.
     */
    public T remove(int i) {
        if (i < 0 || i > n - 1) throw new IndexOutOfBoundsException();
        Node w = getNode(i);
        remove(w);
        return w.val;
    }

    ///////////////////////////////////////////////////////////
    /** Adds a new node with value <code>x</code> as the <code>i</code>-th
     * element of this list.
     * @param i the index of the new node
     * @param x the value of the new node
     * @throws IndexOutOfBoundsException when <code>i&lt;0</code> or <code>i&ge;n</code>.
     */
    public void add(int i, T x) {
        if (i < 0 || i > n) throw new IndexOutOfBoundsException();
        addBefore(getNode(i), x);
    }

    ///////////////////////////////////////////////////////////
    /** Gets the value of the <code>i</code>-th element of this
     * list.
     * @param i the index of the element of the list to get
     * @return the  value of the i-th element
     * @throws IndexOutOfBoundsException when <code>i&lt;0</code> or <code>i&ge;n</code>.
     */
    public T get(int i) {
        if (i < 0 || i > n - 1) throw new IndexOutOfBoundsException();
        return getNode(i).val;
    }

    ///////////////////////////////////////////////////////////
    /** Returns the first element of this list.
     * @return the first element of this list
     */
    public T firstElem() { return getNode(0).val;   }

    ///////////////////////////////////////////////////////////
    /** Returns the last element of this list.
     * @return the last element of this list
     */
    public T lastElem()  { return getNode(n-1).val; }

    ///////////////////////////////////////////////////////////
    /** Sets the value of the <code>i</code>-th element of this
     * list to <code>x</code>.
     * @param i the index of the element of the list to set
     * @param x the value to set the i-th element to
     * @return the old value of the i-th element
     * @throws IndexOutOfBoundsException when <code>i&lt;0</code> or <code>i&ge;n</code>.
     */
    public T set(int i, T x) {
        if (i < 0 || i > n - 1) throw new IndexOutOfBoundsException();
        Node u = getNode(i);
        T y    = u.val;
        u.val  = x;
        return y;
    }
    ///////////////////////////////////////////////////////////
    /** Resets this list to be empty.
     */
    public void clear() {
        AHEAD.next = BEHIND;
        BEHIND.prev = AHEAD;
        AHEAD.prev = BEHIND.next = null;
        n = 0;
    }

    ///////////////////////////////////////////////////////////

    class Iterator implements ListIterator<T> {
        /** p is the node whose value is returned by next() */
        Node p;
        /** last is the last node whose value was returned by next() or previous() */
        Node last;
        /** i is the index of p */
        int i;
        // CONSTRUCTOR
        Iterator(DLList<T> l, int iInit) { i = iInit; p = l.getNode(i); last = null; }

        public boolean hasNext()     { return p != BEHIND;     }
        public boolean hasPrevious() { return p.prev != AHEAD; }
        public int nextIndex()       { return i;               }
        public int previousIndex()   { return i-1;             }
        public void add(T x)         { addBefore(p, x);        }
        public void set(T x)         { last.val = x;           }

        public T next()      { last = p;  p = p.next;  i++;  return last.val;       }
        public T previous()  { last = p = p.prev;  i--; return last.val;            }
        public void remove() { if (p == last) p = p.next; DLList.this.remove(last); }
    }
    ///////////////////////////////////////////////////////////
    /** Returns a  copy of this list
     * @return a copy of this list
     */
    public DLList<T> copy() {
        DLList<T> ans = new DLList<T>();
        ListIterator<T> it = listIterator(0);
        while (it.hasNext())  ans.add(it.next());
        return ans;
    }

    ///////////////////////////////////////////////////////////
    /** Tests equality between <code>e1</code> and <code>e2</code>,
     * where one or both of these may be null.
     * @param e1 first argument
     * @param e2 second argument
     * @return true iff both are null or e1.equals(e2)
     */
    protected static boolean theSame(Object e1,Object e2) {
        return (e1==e2 || (e1!=null && e1.equals(e2)));
    }

    ///////////////////////////////////////////////////////////
    /** Check that the list is correctly formed and report
     * any problems found.
     */
    protected void linkCheck() {
        Node nd = AHEAD;
        while (nd != BEHIND) {
            if (nd.next.prev != nd && false) // TO_DO Fix error printing this
                System.out.println(" ERROR in "+this+", the "+nd.val+"-node has nd.next.prev != nd");
            nd = nd.next;
        }
    }

    ///////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////
    // HOMEWORK PROBLEMS START HERE ///////////////////////////
    ///////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////

    /** Remove the first <code>k</code> elements of the list and return
     * these in a new list.
     * That is, if the list starts off as [a0,a1,...,a{n-1}], then
     * after this.splitAt(k), the list becomes [ak,a{k+1},...,a{n-1}] and
     * the list [a0,...,a{k-1}] is returned.
     * If <code>k&le;0</code>, the list is unchanged and the empty list
     * is returned; and if <code>k&ge;n</code>, then the list becomes empty
     * and a copy of the original list is returned.
     * <p> <b>HOMEWORK PROBLEM.</b>
     * @param k the index at which the split occurs
     * @return the list of elements removed, in order
     */
    public DLList<T> splitAt(int k) {
        DLList<T> ans = new DLList<T>();
        Node selected = AHEAD.next;
        if(k > n){
            if(AHEAD.next == null) return ans;
            for(int i = 0; i < n; i++){
                ans.add(selected.val);
                selected = selected.next;
            }
            clear();
        }// end of first if
        else for(int i = 0; i < k; i++){
            ans.add(selected.val);
            selected = selected.next;
            remove(selected.prev);
        }
        return ans;
    }
    ////////////////////////////////////////////////////////////////////
    /** Returns a reversed copy of this list
     * <p><b>HOMEWORK PROBLEM</b>
     * @return a reversed copy of this list (the list itself is not changed)
     */
    public DLList<T> reverseCopy() {
        DLList<T> ans = new DLList<T>();
        if(n <= 0) return ans;
        ans.addAll(this);
        ans.reverse();
        return ans;
    }

    ////////////////////////////////////////////////////////////////////
    /** Reverses the list.
     * <p> <b>HOMEWORK PROBLEM.</b> See Exercise 3.12 in <i>Open Data Structures</i>.
     */
    public void reverse() {

        if (n <= 0) return;


        Node n1 = AHEAD.next, n2 = n1.next, n3, n4 = BEHIND.prev;
        BEHIND.prev = n1;
        while(n2 != BEHIND){
            n3 = n2.next;
            n2.next = n1;
            n2.prev = n3;

            if(n1 == AHEAD.next) n1.next = BEHIND;
            if(n1 == n4) n1.prev = AHEAD;
            n1 = n2;
            n2 = n3;
        }
        AHEAD.next = n1;




        // I realize there's a pointer error occurring at the final nodes but idk how to fix them


    }
    ////////////////////////////////////////////////////////////////////
    /** Takes the first node from <code>other</code> and appends it to
     * this list.
     * See Exercise 3.13.1 in <i>Open Data Structures</i>.
     * @param other the list from which to take the first node
     * @throws IllegalArgumentException when <code>other</code> is empty.
     */
    public void takeFirst(DLList<T> other) {
        if (other.n==0) throw new IllegalArgumentException("Argument list is empty");
        Node nd = other.AHEAD.next; // first node in other's list
        other.remove(nd);
        addBefore(BEHIND,nd);
    }
    ////////////////////////////////////////////////////////////////////
    /** Merges two sorted lists into single sorted list.
     * We assume that <code>lst1</code> and <code>lst2</code> have no
     * Nodes in common.
     * <p> <b>HOMEWORK PROBLEM.</b>  See Exercise 3.13.2 in <i>Open Data Structures</i>.
     * @param  lst1 the first sorted list
     * @param  lst2 the second sorted list
     * @return the sorted, merge of <code>lst1</code> and <code>lst2</code>
     */
    public static DLList<Integer> merge(DLList<Integer> lst1, DLList<Integer> lst2) {
        DLList<Integer> ans = new DLList<Integer>();
        if((lst1.size() == 0) && (lst2.size() == 0)){
            return ans;
        }
        else if((lst1.n == 0) && (lst2.n != 0)){
            ans.addAll(lst2);
        }

        else if((lst1.n != 0) && (lst2.n == 0)){
            ans.addAll(lst1);
        }

        else {
            DLList.Node lst1T = lst1.AHEAD.next;
            DLList.Node lst2T = lst2.AHEAD.next;
            while ((lst1T != lst1.BEHIND) || (lst2T != lst2.BEHIND)){
                if(lst1T == lst1.BEHIND){
                    ans.add((int)lst2T.val);
                    lst2T = lst2T.next;
                }
                else if (lst2T == lst2.BEHIND){
                    ans.add((int)lst1T.val);
                    lst1T = lst1T.next;
                }

                else if((int)lst1T.val <= (int)lst2T.val){
                    ans.add((int)lst1T.val);
                    lst1T = lst1T.next;
                }
                else if((int)lst1T.val > (int)lst2T.val){
                    ans.add((int)lst2T.val);
                    lst2T = lst2T.next;
                }


            }// end of while
        }

        lst1.clear();
        lst2.clear();

        return ans;
    }

    ////////////////////////////////////////////////////////////////////
    /** Sorts the list <code>lst1</code> via mergeSort.
     * <p> <b>HOMEWORK PROBLEM.</b>  See Exercise 3.13.3 in <i>Open Data Structures</i>.
     * @param  lst1 the list to be sorted
     * @return the sorted version of <code>lst1</code>
     */
    public static DLList<Integer> mergesort(DLList<Integer> lst1) {
        DLList<Integer> ans = new DLList<Integer>();
        if(lst1.n <= 0) return ans;

        int[] x = new int[lst1.n + 1];
        int c = 0;
        DLList.Node lstT = lst1.AHEAD.next;

        while(lstT != lst1.BEHIND){
            x[c] = (int)lstT.val;
            c++;//ha
            lstT = lstT.next;
        }
        //Starting of simple insertion sort for the array
        int i, k, j;
        for (i = 1; i < lst1.n; i++)
        {
            k = x[i];
            j = i-1;

            while (j >= 0 && x[j] > k)
            {
                x[j+1] = x[j];
                j = j-1;
            }
            x[j+1] = k;
        }
        //end sort

        for(int f = 0; f < lst1.n; f++){
            ans.add(x[f]);
        }

        return ans;

    }
}