import java.util.ListIterator;

/** A subclass of DLList that keeps a sorted (from smallest to biggest) list of items.
 * A dummy node is used to simplify the code.
 * @author Bernardo Hauer Santos
 * @param <T> the type of elements stored in the list which is assumed to implement
 *   the Comparable interface.
 */
public class Ordered<T extends Comparable<T>> extends DLList<T> {
    ////////////////////////////////////////////////////////////////////////
    /** Constructs an empty Ordered list (Just call's DLList's constructor.)
     */
    public Ordered() { super(); }

    ////////////////////////////////////////////////////////////////////////
    // OVERRIDDEN METHODS
    /** Adds a new value to the Ordered list so that the elements are
     * in <code>compareTo</code> order.  <i>Important:</i>
     * If at the start there is already an list element equal to
     * <code>val</code>, then nothing is added to the list.
     * <p><b>Examples:</b>
     * <ul>
     * <li> If <code>lst = [1,5,23,97]</code>, then after lst.add(12),
     * <code>lst = [1,5,12,23,97]</code>.
     * <li> If <code>lst = [1,5,23,97]</code>, then after lst.add(23),
     * <code>lst = [1,5,23,97]</code>.
     * </ul>
     * <p> <b>HOMEWORK PROBLEM.</b>
     * @param val the value to be added
     * @return true iff a new item was added to the list.
     */
    public boolean add(T val) {
        Node newNode = new Node();
        newNode.val = val;
        if( n == 0 ) addBefore(BEHIND, val);

        Iterator listIterator = new Iterator(this, 0);
        // Here the solution is pretty simple
        //      just iterate throught the list and when the node that newNode has to come before if found, use the addBefore method to put newNode in its position
        while(listIterator.hasNext()){
            if(val.compareTo(listIterator.p.val) == 0) break;
            else if(val.compareTo(listIterator.p.val) < 0){
                addBefore(listIterator.p, newNode);
                break;
            }
            else if(val.compareTo(listIterator.p.val) > 0){
                listIterator.next();
            }

            if(listIterator.p == BEHIND) addBefore(BEHIND, val);

        }
        System.out.println("Current list " + this);
        return true;
    }
    class Iterator implements ListIterator<T> {
        /** p is the node whose value is returned by next() */
        Node p;
        /** last is the last node whose value was returned by next() or previous() */
        Node last;
        /** i is the index of p */
        int i;
        // CONSTRUCTOR
        Iterator(Ordered<T> l, int iInit) { i = iInit; p = l.getNode(i); last = null; }
        public boolean hasNext()     { return p != BEHIND;     }
        public boolean hasPrevious() { return p.prev != AHEAD; }
        public int nextIndex()       { return i;               }
        public int previousIndex()   { return i-1;             }

        public void add(T x)            { throw new UnsupportedOperationException(); }
        public void set(T x)            { throw new UnsupportedOperationException(); }

        public T next()      { last = p;  p = p.next;  i++;  return last.val;        }
        public T previous()  { last = p = p.prev;      i--;  return last.val;        }
        public void remove() {
            if (p == last) p = p.next;
            Ordered.this.remove(last);
        }
    }
    /** <b>Unsupported</b>
     * @throws UnsupportedOperationException always */
    public void add(int i, T x)       { throw new UnsupportedOperationException(); }
    /** <b>Unsupported</b>
     * @throws UnsupportedOperationException always */
    public T set(int i, T x)          { throw new UnsupportedOperationException(); }

    ////////////////////////////////////////////////////////////////////////
    // new methods
    ////////////////////////////////////////////////////////////////////////
    /** Construct a list of neighbors of x in the list.
     *  <p> <b>HOMEWORK PROBLEM.</b>
     * @param x the value for the neighborhood
     * @return a list consisting of up to three elements:
     * <ul>
     * <li> the element of the list which has the largest value less than <code>x</code>
     * (if such an element exists)
     * <li> <code>x</code> if <code>x</code> is in the list
     * <li> the element of the list which has the smallest value greater than <code>x</code>
     * (if such an element exists)
     * </ul>
     * <b>Examples:</b> If <code>lst</code> consists of [1,3,5,7], then
     * <ul>
     * <li>  lst.neighbors(0) would return [1]
     * <li>  lst.neighbors(1) would return [1,3]
     * <li>  lst.neighbors(8) would return [7]
     * <li>  lst.neighbors(7) would return [5,7]
     * <li>  lst.neighbors(2) would return [1,3]
     * <li>  lst.neighbors(3) would return [1,3,5]
     * </ul>
     */

    public Ordered<T> neighbors(T x){
        //System.out.println("this list is: " + this);
        Ordered<T> ans = new Ordered<T>();
        Node target = new Node();
        target.val = x;
        boolean targetFound = false, largestFound = false, smallestFound = false ;
        Iterator listIterator = new Iterator(this, 0);
        while(listIterator.hasNext()){ // Finding if value is in list
            if(listIterator.p.val.equals(target.val)){// this is the easiest situation and all we do is check if the node has neighbors and add them
                ans.add(target.val);
                if(listIterator.hasNext() && !listIterator.p.next.equals(BEHIND)) ans.add(listIterator.p.next.val);
                if(listIterator.hasPrevious() && !listIterator.p.next.equals(AHEAD)) ans.add(listIterator.p.prev.val);
                targetFound = true;
            }
            listIterator.next();
        }

        if(!targetFound){ // This loop is only necessary if there is no instance of the target in the list
            listIterator = new Iterator(this, 0);
            while(listIterator.hasNext() && listIterator.p.next != BEHIND){
                // First if finds largest
                if (listIterator.p.val.compareTo(target.val) < 0 && (listIterator.p.next.val.compareTo(target.val) > 0) && !largestFound) { // largest val
                    ans.add(listIterator.p.val);
                    largestFound = true;
                }



                if (listIterator.p.val.compareTo(target.val) > 0 && (listIterator.p.next.val.compareTo(target.val) < 0) && !smallestFound) { // smallest val
                    ans.add(listIterator.p.val);
                    smallestFound = true;
                }



                listIterator.next();
            }// end of next loop
        }

        return ans;
    }




    /** Checks if this list is sorted <i>(for debugging proposes)</i>
     * @return true iff the list is sorted
     */
    public boolean checkSorted() {
        if (size() <= 1) return true;

        T prior            = get(0);
        ListIterator<T> it = listIterator(1);

        while (it.hasNext()) {
            T current = it.next();
            if (prior.compareTo(current) > 0) return false;
            prior = current;
        }
        return true;
    }
    ////////////////////////////////////////////////////////////////////////
    public static void main(String... argv) {
        Ordered<Integer> lst = new Ordered<Integer>();
        lst.add(11);
    }
}