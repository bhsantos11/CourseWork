// package ods;

import java.util.Iterator;
import java.util.Random;

/** This class implements hashing with chaining using multiplicative hashing
 * <p><b>DO NOT CHANGE THIS FILE</b>
 *
 * @author morin (with modifications)
 *
 * @param T the type parameter
 */
public class ChainedHashTable<T> implements USet<T> {
    /** The hash table  */
    DLList<T>[] t;
    boolean selector = true;  /* added by sjc */

    /** The "dimension" of the table (table.length = 2^d) */
    int d;

    /** The number of elements in the hash table */
    int n;

    /** The multiplier */
    int z;

    /** The number of bits in an int */
    protected static final int w = 32;

    /** Create a new empty hash table */
    public ChainedHashTable() {
        d        = 1;
        t        = allocTable(2);
        Random r = new Random();
        z        = r.nextInt() | 1;     // is a random odd integer
        //    z = 1132930231;
    }
    /** Return the ChainedHashTable to an empty state.  */
    public void clear() {
        d = 1;
        t = allocTable(2);
        n = 0;
    }

    /** Allocate and initialize a new empty table
     * @param s the number of buckets in the new table.
     * @return an empty table with s many buckets
     */
    @SuppressWarnings({"unchecked"})
    protected DLList<T>[] allocTable(int s) {
        DLList<T>[] tab = new DLList[s];
        for (int i = 0; i < s; i++) {
            tab[i] = new DLList<T>();
        }
        return tab;
    }

    /** Resize the table so that it has size 2^d */
    protected void resize() {
        d = 1;
        while (1<<d <= n) d++;
        n = 0;
        DLList<T>[] oldTable = t;
        t = allocTable(1<<d);
        for (DLList<T> bucket : oldTable)
            for (T x : bucket)
                add(x);
    }

    /** Return the number of elements stored in this hash table */
    public int size() {
        return n;
    }

    /** Compute the table location for object x
     * @param x the value look up
     * @return ((x.hashCode() * z) mod 2^w) div 2^(w-d) if selector == true
     * @return ((x.hashCode() mod 2^w) if selector == false
     *
     * modified from original by sjc
     */
    protected final int hash(Object x) {

        if (selector) {
            return (z * x.hashCode()) >>> (w-d);
        } else {
            return (x.hashCode() >>> (w-d));
        }
    }

    /** Add the element x to the hashtable if it is not already present.
     * @return true
     */
    public boolean add(T x) {
        if (find(x) != null) return false;
        if (n+1 > t.length) resize();
        t[hash(x)].add(x);
        n++;
        return true;
    }
    /** Remove x from the hash table if present.
     * @param x the value to be removed
     * @return y if y equaling x was found in the table
     *    and if no such y was found, return null
     */
    public T remove(T x) {
        Iterator<T> it = t[hash(x)].iterator();
        while (it.hasNext()) {
            T y = it.next();
            if (y.equals(x)) {
                it.remove();
                n--;
                return y;
            }
        }
        return null;
    }

    /** Get the copy of x stored in this table.
     * @param x - the item to get 
     * @return - the element y stored in this table such that x.equals(y)
     * is true, or null if no such element y exists
     */
    public T find(Object x) {
        for (T y : t[hash(x)])
            if (y.equals(x))
                return y;
        return null;
    }


    public Iterator<T> iterator() {
        class IT implements Iterator<T> {
            int i, j;
            int ilast, jlast;
            IT() {
                i = 0;
                j = 0;
                while (i < t.length && t[i].isEmpty())
                    i++;
            }
            protected void jumpToNext() {
                while (i < t.length && j + 1 > t[i].size()) {
                    j = 0;
                    i++;
                }
            }
            public boolean hasNext() {
                return i < t.length;
            }
            public T next() {
                ilast = i;
                jlast = j;
                T x =  t[i].get(j);
                j++;
                jumpToNext();
                return x;
            }
            public void remove() {
                ChainedHashTable.this.remove(t[ilast].get(jlast));
            }
        }
        return new IT();
    }

    /** Produces a String representation of the hash table, 
     *  provided the table has 500 or fewer elements.
     */
    public String toString() {
        if (n>500) return "**Forget it, the table is too big.**";
        String ans = "";
        for(int i=0; i<t.length; i++) {
            ans = ans + t[i].toString() + "\n";
        }
        return ans;
    }

    //////////////////////////////////////////////////////////////////
    /**
     *  set the selector flag to choose which hashing algorithm to run.
     *  @param f new boolean value for the selector flag
     *  @return the true if set; false if the table was non-empty
     *
     *  added by sjc
     */

    public boolean setSelector(boolean f) {
        // can't change selector on non-empty table
        if (n != 0) {
            System.out.println("Fail:  attempting to turn off selector on hash table with " + n + " entries.");
            return false;
        }
        selector = f;
        return true;
    }

    //////////////////////////////////////////////////////////////////
    /** Produces a string detailing statistics on the ChainedHashTable.
     * @return a string detailing statistics on the ChainedHashTable
     */
    public String stats() {
        if (n==0) return "**Empty table**";

        double mean    = (double) t.length / n;
        double sumSqrs = 0.0;
        int minSize    = n;
        int maxSize    = 0;

        for(DLList<T> lst : t) {
            int sz = lst.size();
            if (sz>maxSize) maxSize = sz;
            if (sz<minSize) minSize = sz;
            sumSqrs = sumSqrs + Math.pow(sz-mean,2);
        }
        return String.format("Buckets = %6d  Elements = %6d",t.length,n)
                + String.format("  Bucket sizes ::  min = %2d  max = %2d",minSize,maxSize)
                + String.format("  avg = %1.3f", mean)
                + String.format("  std.dev = %1.3f", Math.sqrt(sumSqrs/t.length));
    }

    //////////////////////////////////////////////////////////////////
    /** A test of the ChainedHashTable class
     * @param args not used
     */
    public static void main(String[] args) {
        int n = 31;
        ChainedHashTable<Integer> t = new ChainedHashTable<Integer>();
        for (int i = 0; i < n; i++) t.add(i*2);

        for (int i = 0; i < 2*n; i++) {
            Integer x = t.find(i);
            if (i % 2 == 0)  assert(x.intValue() == i);
            else            assert(x == null);
        }
        System.out.println(t);
        System.out.println(t.stats());
    }

}