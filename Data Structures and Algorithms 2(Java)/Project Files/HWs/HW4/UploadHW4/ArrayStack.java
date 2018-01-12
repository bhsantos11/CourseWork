import java.util.Arrays;

/********************************************************************
 * This is based on a simplification of Morin's ArrayStack, which 
 * in turn, is a version of the JCF class ArrayList.  
 * It implements the List interface as a single array a.  
 * Elements are stored at positions a[0],...,a[size()-1].  
 * Doubling/halving is used to resize the array a when necessary.
 * @author
 *   Bernardo Santos
 * @version
 *   19-Sept-2016
 *******************************************************************/

public class ArrayStack {
    /////////////////////////////////////////////////////////////
    // Instance Variables
    /////////////////////////////////////////////////////////////

    /** The array used to store elements (backing array) */
    private int[] a;

    /** The number of elements stored */
    private int n;

    /////////////////////////////////////////////////////////////
    // Constructors
    /////////////////////////////////////////////////////////////

    /************************************************************
     * Constructs an empty ArrayStack with initial capacity 1.
     */
    public ArrayStack() {
        a = new int[1];
        n = 0;
    }

    /************************************************************
     * Constructs an empty ArrayStack with specified initial capacity.
     * @param k the initial capacity of the new ArrayStack
     */
    public ArrayStack(int k) {
        a = new int[k];
        n = 0;
    }

    /************************************************************
     * Constructs an empty ArrayStack from a given array.
     * @param source the array to build the ArrayStack from
     */
    public ArrayStack(int[] source) {
        a = source.clone();
        n = source.length;
    }

    /////////////////////////////////////////////////////////////
    // Utilities
    /////////////////////////////////////////////////////////////

    /************************************************************
     * Returns a copy of this ArrayStack instance.
     * @return a clone of this ArrayStack instance
     */
    public Object clone() {
        ArrayStack other = new ArrayStack(a.length);
        for (int i = 0; i < n; i++) // or better yet, use System.arraycopy
            other.a[i] = a[i];
        other.n = n;
        return (Object) other;
    }

    /************************************************************
     * Equality test on ArrayStacks
     * @param o the Object to be compared to this ArrayStack
     * @return true iff o is an ArrayStack of the size and
     *    contents as this ArrayStack
     */
    public boolean equals(Object o) {
        if (!(o instanceof ArrayStack) || o==null) return false;
        ArrayStack other = (ArrayStack) o;
        if (n != other.n) return false;
        for (int i=0; i<n; i++) {
            if (a[i] != other.a[i]) return false;
        }
        return true;
    }

    /************************************************************
     * Hash code of an ArrayStack
     * @return a hash code based on the contents of this ArrayStack
     */
    public int hashCode() {
        return Arrays.hashCode(Arrays.copyOf(a,n)); // not very effecient
    }

    /************************************************************
     * Resize the internal array
     */
    protected void resize() {
        int[] b = new int[Math.max(n*2,1)];
        for (int i = 0; i < n; i++) {
            b[i] = a[i];
        }
        a = b;
    }

    /************************************************************
     * Resize the internal array
     * @param nn the new size of the array
     * @throws IllegalArgumentException when nn &lt; n
     */
    protected void resize(int nn) {
        if (nn < n)
            throw new IllegalArgumentException(
                    "new size " + nn + "smaller than current contents size " + n);

        int[] b = new int[nn];
        for (int i = 0; i < n; i++) {
            b[i] = a[i];
        }
        a = b;
    }

    /************************************************************
     * Constructs a String representation of this ArrayStack.
     */
    public String toString() {
        return Arrays.toString(Arrays.copyOf(a,n));
    }
    public String show() {
        return ("contents = " +
                Arrays.toString(Arrays.copyOf(a,n))
                + "\n array length = " + a.length);
    }

    /************************************************************
     * Returns an array representation of the ArrayStack.
     * @return an array representation of the ArrayStack
     */
    public int[] toArray() {
        return Arrays.copyOf(a,n);
    }
    /************************************************************
     * Trims the capacity of this ArrayStack instance to be its
     *  current size.
     */
    public void trimToSize() {
        int b[] = Arrays.copyOf(a,n);
        a = b;
    }


    /////////////////////////////////////////////////////////////
    // Methods in Morin's version
    /////////////////////////////////////////////////////////////

    /************************************************************
     * Appends the specified element to the end of this ArrayStack.
     * @param e the element to be appended to this ArrayStack
     * @return true (to be consistent with java.util.ArrayList)
     */
    public boolean add(int e) {
        if (n + 1 > a.length) resize();
        a[n] = e;
        n++;
        return true;
    }

    /************************************************************
     * Inserts element e at the specified position, i, in this list.
     * Shifts the element currently at that position (if any) and
     * any subsequent elements to the right (adds one to their indices).
     * @param i  index at which the specified element is to be inserted
     * @param e  element to be inserted
     */
    public void add(int i, int e) {
        if (i < 0 || i > n) throw new IndexOutOfBoundsException();
        if (n + 1 > a.length) resize();
        for (int j = n; j > i; j--)
            a[j] = a[j-1];
        a[i] = e;
        n++;
    }

    /************************************************************
     * Inserts all of the elements in the specified array, c, into
     * this ArrayStack, starting at position i.
     * Shifts the element currently at that position (if any) and
     * any subsequent elements to the right (increases their indices).
     * Appends all of the elements in array c to the end of this ArrayStack.
     *
     * @param i index at which to insert the first element from the
     *    specified array
     * @param c an array containing elements to be added to this
     *    ArrayStack
     * @return true iff this ArrayStack changed as a result of the call.
     * @throws NullPointerException when the specified array is null
     */
    public boolean addAll(int i, int[] c) {
        if (i < 0 || i > n) throw new IndexOutOfBoundsException();
        int k = c.length;
        if (n + k > a.length) resize(2*(n+k));
        for (int j = n+k-1; j >= i+k; j--)
            a[j] = a[j-k];
        for (int x : c)
            a[i++] = x;
        n += k;
        return true;
    }

    /************************************************************
     * Removes all of the elements from this ArrayStack, which will be
     * empty after this call returns.
     */
    public void clear() {
        n = 0;
        resize();
    }

    /************************************************************
     * Returns the element at index i in this ArrayStack.
     * @param i index of the element to return
     * @return the value at index i
     */
    public int get(int i) {
        if (i < 0 || i > n - 1) throw new IndexOutOfBoundsException();
        return a[i];
    }

    /************************************************************
     * Removes the element at the specified position in this ArrayStack.
     * Shifts any subsequent elements to the left (subtracts one
     * from their indices)
     * @param i the index of the element to be removed
     * @return the element removed
     */
    public int remove(int i) {
        if (i < 0 || i > n - 1) throw new IndexOutOfBoundsException();
        int x = a[i];
        for (int j = i; j < n-1; j++)
            a[j] = a[j+1];
        n--;
        if (a.length >= 3*n) resize();
        return x;
    }

    /************************************************************
     * Replaces the element at the specified position in this ArrayStack
     * with the specified element.
     * @param i index of the element to replace
     * @param e element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException
     *    if the index is out of range (i &lt; 0 || i &ge; size())
     */
    public int set(int i, int e) {
        if (i < 0 || i > n - 1) throw new IndexOutOfBoundsException();
        int y = a[i];
        a[i] = e;
        return y;
    }

    /************************************************************
     * Returns the number of elements in this ArrayStack.
     * @return the number of elements in this ArrayStack.
     */
    public int size() {
        return n;
    }

    /////////////////////////////////////////////////////////////
    // Additional Methods (provided with the homework)
    /////////////////////////////////////////////////////////////

    /************************************************************
     * Returns true if this ArrayStack contains no elements.
     * @return true iff the ArrayStack contains no elements.
     */
    public boolean isEmpty() {
        return (n==0);
    }

    /////////////////////////////////////////////////////////////
    // Additional Methods (ones *you* have to write)
    /////////////////////////////////////////////////////////////

    /************************************************************
     * Tests if the specified element is in the ArrayStack.
     * @param e the element to be checked for
     * @return true iff this ArrayStack contains the specified element.
     * <br><br><strong>&diams;&diams;&diams; You write this one. &diams;&diams;&diams;</strong>
     */
    public boolean contains(int e) {
        for(int x = 0; x < n;x++){
            if(a[x]==e) return true;
        }
        return false;
    }

    /************************************************************
     * Counts the number of times the specified element occurs in
     * the ArrayStack.
     * @param e the element to be counted
     * @return the number of times e occurs in the ArrayStack.
     * <br><br><strong>&diams;&diams;&diams; You write this one. &diams;&diams;&diams;</strong>
     */
    public int count(int e) {
        int count = 0;
        for(int x = 0; x < n; x++){
            if(a[x] == e) count++;
        }
        return count;
    }

    /************************************************************
     * Deletes the first occurrence of the specified element from
     * this ArrayStack, if it is present. If the ArrayStack does not contain
     * the element, it is unchanged.
     * @param e to be deleted from this ArrayStack, if present
     * @return true iff the specified element was deleted.
     * <br><br><strong>&diams;&diams;&diams; You write this one. &diams;&diams;&diams;</strong>
     */
    public boolean delete(int e) {
        for(int x = 0; x < n; x++){
            if(a[x] == e){
                int temp = a[x];
                a[x] = a[(n-1)];
                a[(n-1)] = temp;
                n--;
                resize();
                return true;
            }
        }// end of for loop
        return false;
    }

    /************************************************************
     * When <code>m&le;n</code>, this method deletes the elements
     * at indices <code>0</code> through <code>m-1</code> from the
     * ArrayStack. When <code>m&gt;n</code>,  all of the elements
     * of this ArrayStack are deleted.
     * @param m the number of elements to delete.
     * <br><br><strong>&diams;&diams;&diams; You write this one. &diams;&diams;&diams;</strong>
     */
    public void drop(int m) {
        // To-Do
    }

    /************************************************************
     * Returns a new ArrayStack consisting of all (and only) the indices
     * of <code>e</code> in this ArrayStack.  E.g., if this ArrayStack
     * has 109 occurring at indices 4, 8, and 34, then the returned
     * ArrayStack consists of [4, 8, 34].  If <code>e</code> does not
     * occur in this ArrayStack, an empty ArrayStack is returned.
     * @param e the element to be searched for
     * @return the list of all indices of <code>e</code> in this ArrayStack.
     * <br><br><strong>&diams;&diams;&diams; You write this one. &diams;&diams;&diams;</strong>
     */
    public ArrayStack locationsOf(int e) {
        ArrayStack ans = new ArrayStack();
        for(int x=0; x< n; x++){
            if(a[x] == e) ans.add(x);
        }
        return ans;
    }
}