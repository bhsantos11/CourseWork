/********************************************************************
 * The SList class.
 * @author
 *   Bernardo Hauer Santos
 * @version
 *   20-Oct-2017
 *******************************************************************/

import java.util.NoSuchElementException;
import java.util.Iterator;

/** A simple singlely linked SList class 
 * based on Pat Morin's <a href=
 * "http://www.cis.syr.edu/courses/cis351/java/ods/SLList.java">
 * <tt>SLList.java</tt></a> from <a href="http://opendatastructures.org">
 * <em>Open Data Structures</em></a>.
 */
public class SList<T>  {
    ////////////////////////////////////////////////////////////////////
    /** The internal Node class */
    class Node {
        T val;
        Node next;
        // constructors
        public Node(T v, Node nxt) {
            val = v; next = nxt;
        }
        public Node() {
            this(null, null);
        }
    }
    // INVARIANTS: /////////////////////////////////////////////////////
    // 1. n = the number of elements in the list
    // 2. head = ref to the first element of the list
    //           (null, if the list is empty)
    // 3. tail = ref to the last element of the list
    //           (null, if the list is empty)
    // 4. For each Node, nd, in the list other than the tail
    //           nd.next = the next element in the list.

    ////////////////////////////////////////////////////////////////////
    /** Head of the SList */
    Node head;
    /** Tail of the SList */
    Node tail;
    /** The number of elements in the SList */
    int n;

    ////////////////////////////////////////////////////////////////////
    /** Construct an empty SList */
    public SList() {
        head = tail = null;
        n = 0;
    }

    /** Construct a SList from the arguments
     * @param args elements to makeup the SList
     */
    @SuppressWarnings("unchecked")
    public SList(T... args) {
        this(); // call of the default constructor
        for(T e : args) add(e);
    }

    ////////////////////////////////////////////////////////////////////
    /** Construct an iterator for this SList
     * @return a new iterator for this SList
     */
    public Iterator<T> iterator() {
        // MLIterator class
        class MLIterator implements Iterator<T> {
            protected Node p;  // the next Node

            public MLIterator()      { p = head; }
            public boolean hasNext() { return p != null; }
            public T next()          { T x = p.val;  p = p.next;  return x; }
            public void remove()     { throw new UnsupportedOperationException(); }
        } // end of MLIterator definition
        return new MLIterator();
    }

    ////////////////////////////////////////////////////////////////////
    // Internal utilities
    ////////////////////////////////////////////////////////////////////

    /** Tests if <tt>e1</tt> and <tt>e2</tt> are equal, where either
     * <tt>e1</tt> or <tt>e2</tt> can be null.
     * Note: If one is null, they are equal iff the other is also null.
     * @param e1 the first object
     * @param e2 the second object
     * @return true iff  (e1==e2 or (e1!=null and e1.equals(e2) is true)
     */
    public static boolean theSame(Object e1,Object e2) {
        if (e1==null) return (e2==null); else return (e1.equals(e2));
        // Alt: return (e1==null)?(e2==null):(e1.equals(e2));
    }

    /** Delete the Node following <code>u</code> in this SList.
     * We assume <code>u</code> is not null <i>and</i> not the tail.
     * @param u the predecessor of the node to be deleted.
     */
    protected void deleteNext(Node u) {
        if (u.next == tail)  tail = u;
        u.next = u.next.next;
        n--;
    }

    /** Insert the Node <code>v</code> following <code>u</code>
     * in this SList.  We assume <code>u</code> is not null.
     * @param u the Node in the SList to insert after.
     * @param v the Node to be inserted
     */
    protected void addAfter(Node u, Node v) {
        if (u == tail)  tail = v;
        else            v = u.next;
        u.next = v;
        n++;
    }

    /** Return the <code>i</code>th Node in this SList (counting
     * from 0).  We assume <code>0 &le; i &lt; n</code>.
     * @param i the SList-index of the Node to return
     * @return the <code>i</code>th Node in this SList.
     */
    protected Node getNode(int i) {
        Node u = head;
        for (int j = 0; j < i; j++)  u = u.next;
        return u;
    }

    ////////////////////////////////////////////////////////////////////
    // Instance methods
    ////////////////////////////////////////////////////////////////////

    /** Returns the size of the SList
     * @return the size of this SList
     */
    public int size() {
        return n;
    }

    ////////////////////////////////////////////////////////////////////
    /** Empties this SList
     */
    public void clear() {
        head = tail = null;
        n = 0;
    }

    ////////////////////////////////////////////////////////////////////
    /** Tests if the SList is empty.
     * @return true iff the SList is empty
     */
    public boolean isEmpty() {
        return (n==0);
    }

    ////////////////////////////////////////////////////////////////////
    /** Add a Node with value v to the <b>tail</b> of the SList
     * @param x the value of the new node.
     * @return true (to be consistent with
     *   <a href="http://docs.oracle.com/javase/8/docs/api/java/util/AbstractList.html">
     *      AbstractList</a>)
     */
    public boolean add(T x) {
        Node u = new Node(x,null);
        if (n == 0) { head = u; }
        else        { tail.next = u; }
        tail = u;
        n++;
        return true;
    }

    ////////////////////////////////////////////////////////////////////
    /** Add a Node with value x to the <b>head</b> of the SList
     * @param x the value of the new node.
     * @return x (to be consistent with
     *  <a href="http://docs.oracle.com/javase/8/docs/api/java/util/Stack.html">Stack</a>)
     */
    public T push(T x) {
        Node u = new Node(x,head);
        head = u;
        if (n == 0)
            tail = u;
        n++;
        return x;
    }

    ////////////////////////////////////////////////////////////////////
    /** Looks at the value at the head of this SList without removing
     * it from the SList.
     * @return the value at the head of this SList
     * @throws NoSuchElementException when the SList is empty
     */
    public T peek() {
        if (n == 0) throw new NoSuchElementException();
        return head.val;
    }

    ////////////////////////////////////////////////////////////////////
    /** Remove the element at the <b>head</b> of the SList and return
     * this element.
     * @return the element removed
     * @throws NoSuchElementException when the SList is empty
     */
    public T remove() {
        if (n == 0) throw new NoSuchElementException();
        T x = head.val;
        head = head.next;
        n--;
        if (n == 0) tail = null;
        return x;
    }

    ////////////////////////////////////////////////////////////////////
    /** Gets the i-th element of the SList.
     * @param i index of the element to get
     * @return the i-th value in the SList
     * @throws NoSuchElementException when i &lt; 0 or i &ge; n
     */
    public T get(int i) {
        if (i<0 || i>=n) throw new NoSuchElementException();
        return (getNode(i)).val;
    }

    ////////////////////////////////////////////////////////////////////
    /** Returns a string representation of the SList.
     * @return a string representation of the SList
     */
    public String toString() {
        if(head == null)  return "[]";
        if (n==0) return "[]";
        String ans = "[" + head.val;
        for (Node nd = head.next; nd != null; nd = nd.next)
            ans = ans + "," + nd.val;
        return ans + "]";
    }

    ////////////////////////////////////////////////////////////////////
    /** Makes a copy of this SList with no Nodes in common with this SList
     * @return a copy of this SList
     */
    public SList<T> copy() {
        SList<T> cp = new SList<T>();
//    Node nd = head;
//    while (nd != null) {
//      cp.add(nd.val);
//      nd = nd.next;
//    }
        Iterator<T> it = this.iterator();
        while (it.hasNext()) cp.add(it.next());
        return cp;
    }

    ////////////////////////////////////////////////////////////////////
    /** Test whether Object <tt>obj</tt> is "equal to" this SList.
     * @param obj the object to test for equality
     * @return true iff the obj is an SList with the equal elements
     *    in the identical order as this SList
     */
    public boolean equals(Object obj) {
        // NOTE: this!=null (why?)
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        @SuppressWarnings("unchecked")
        SList<T> other = (SList<T>) obj;
        // if (other.size() != this.size()) return false;
        Node cursor1 = this.head;
        Node cursor2 = other.head;
        while (cursor1 != null && cursor2 != null) {
            if ( !theSame(cursor1.val,cursor2.val) )
                return false;
            cursor1 = cursor1.next;
            cursor2 = cursor2.next;
        }
        return (cursor1 == null && cursor2 == null);
    }

    ////////////////////////////////////////////////////////////////////
    /** Alters this SList so that the first occurrence of e is removed
     * @param e the object to be deleted from this SList
     * @return true iff an instance of e was removed from this SList
     */
    public boolean delete(T e) {
        // Case: the SList is empty
        if (n==0) return false;
        // Case: e occurs in the head of the SList
        if (theSame(head.val,e)) {
            remove();
            return true;
        }
        // Search the rest of the SList for e
        Node trailer = head, leader = head.next;
        while (leader != null && !theSame(e,leader.val)) {
            trailer = leader; leader = leader.next;
        }
        // Case: e doesn't occur in the SList
        if (leader == null) return false;
        // Case: e occurs in the SList
        // Check: if e occurs at the tail, update the tail ref
        if (leader == tail) tail = trailer;
        trailer.next = leader.next;
        n--;
        return true;
    }


    ////////////////////////////////////////////////////////////////////
    // Homework problems
    ////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////
    /** Adds the elements in the SList other to the end of this SList
     * @param other the SList of elements to add to this SList
     * @return true iff this SList was modified
     * <p><b>HOMEWORK PROBLEM</b>
     */
    public boolean addAll(SList<T> other) {
        Node selected = other.head;
        for(int i = 0; i < other.n; i++){
            add(selected.val);
            selected = selected.next;
        }
        return true;
    }

    ////////////////////////////////////////////////////////////////////
    /** Test if this SList contains e.  Note e many be null.
     * @param e the object to search for
     * @return true iff the object e was found.
     * <p><b>HOMEWORK PROBLEM</b>
     */
    public boolean contains(T e) {
        Node selected = head;
        for(int i = 0; i < n; i++){
            if(selected.val == e) return true;
            selected = selected.next;
        }
        return false;
    }

    ////////////////////////////////////////////////////////////////////
    /** Returns the list of all locations of the object e in this SList.
     * @param e the object to search for
     * @return the list of all locations (indices) of the object e in this SList.
     * <p><b>HOMEWORK PROBLEM</b>
     */
    public SList<Integer> locationsOf(T e) {
        SList<Integer> ans = new SList<Integer>();
        Node selected = head;
        for(int i = 0; i < n; i++){
            if(selected.val == e) ans.add(i);
            selected = selected.next;
        }
        return ans;
    }

    ////////////////////////////////////////////////////////////////////
    /** Alters this SList so that the first <code>k</code> elements of
     * the SList are removed.  If <code>k &ge; n</code>, then this
     * SList becomes empty.
     * @param k the elements to remove from the front of this SList.
     * <p><b>HOMEWORK PROBLEM</b>
     */
    public void drop(int k) {
        if (k >= n) {
            clear();
            return;
        }
        for(int i = 0; i < k; i++){
            head = head.next;
        }
        n-=k;
    }

    ////////////////////////////////////////////////////////////////////
    /** Alters this SList so that the first <code>k</code> elements of
     * the SList are removed and placed into a new SList.
     * If <code>k &ge; n</code>, then the new SList is a copy of this
     * SList and then this SList becomes empty.
     * @param k the elements to remove from the front of this SList.
     * @return the first k elements of the list
     * <p><b>HOMEWORK PROBLEM</b>
     */
    public SList<T> splitAt(int k) {
        SList<T> taken = new SList<T>();
        if(k > n)for(int i = 0; i < n; i++){
            if(head == null) return taken;
            taken.add(head.val);
            head = head.next;
        }
        else for(int i = 0; i < k; i++){
            taken.add(head.val);
            head = head.next;
        }
        return taken;
    }

    ////////////////////////////////////////////////////////////////////
    /** Returns a reversed copy of this list
     * @return a reversed copy of this list (the list itself is not changed)
     * <p><b>HOMEWORK PROBLEM</b>
     */
    public SList<T> reverseCopy() {
        SList<T> ans = new SList<T>();
        if (n <= 0) return ans;
        ans.addAll(this);
        ans.reverse(); // Why write code twice am i right
        return ans;
    }


    ////////////////////////////////////////////////////////////////////
    /** Reverses this list.
     * <p><b>HOMEWORK PROBLEM</b>
     */
    public void reverse() {
        if (n <= 0) return;
        Node n1 = head, n2 = n1.next, n3;
        while(n2 != null) {
            n3 = n2.next; //n3 can be nullptr, n3 is being used to track what n2 will have to work on next
            n2.next = n1; // Actual pointer reversing
            if(n1 == head) n1.next = null;
            n1 = n2; // n1 moving on node forward to the node that n2's 'next' will be redefined to point to
            n2 = n3;
        }
        head = n1;
    }

    /** Tests for the above */
    public static void main(String[] argv) {
        SList<String> lst1 = new SList<String>("dog","cat","cow","pig","doe");
        System.out.println(lst1);
        SList<Integer> lst2 = new SList<Integer>(6,12,943,7,-1);
        System.out.println(lst2);
        SList<Integer> lst3 = lst2.copy();
        System.out.println(lst3);
    }
}