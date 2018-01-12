//package ods;
import java.util.AbstractSequentialList;
import java.util.ListIterator;

/** An implementation of the List interface as a doubly-linked circular list. 
 * <p><b>DO NOT CHANGE THIS FILE</b>
 * <p>
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
 * @author Dear Grader, I forgot to put my name here, so take off some points. Thanks!
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
        w.prev      = u;
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
        Node p;
        int j;
        if (i < n/2) for (p = AHEAD.next, j = 0; j < i; j++, p = p.next);
        else       for (p = BEHIND,     j = n; j > i; j--, p = p.prev);
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
    /** Gets the value of the <code>i</code>-th element of this list.
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
    /** Sets the value of the <code>i</code>-th element of this list to <code>x</code>.
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
        AHEAD.next  = BEHIND;
        BEHIND.prev = AHEAD;
        AHEAD.prev  = BEHIND.next = null;
        n = 0;
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
    /** Check that the list is built properly */
    protected void linkCheck() {
        Node nd = AHEAD;
        while (nd != BEHIND) {
            if (nd.next.prev != nd)
                System.out.println(" ERROR in "+this+", the "+nd.val
                        +"-node has nd.next.prev != nd");
            nd = nd.next;
        }
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
}