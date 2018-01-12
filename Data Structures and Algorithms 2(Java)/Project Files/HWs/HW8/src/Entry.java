import java.util.ListIterator;
/** An Entry consists of a String (<code>key</code>) together
 * with a list of Integers (<code>occurrs</code>).
 * <p><b>DO NOT CHANGE THIS FILE</b>
 */

public class Entry implements Comparable<String> {
    /** the key */
    String key;
    /** the list of occurrences */
    DLList<Integer> occurs;

    Entry(String wrd) { key    = wrd;  occurs = new DLList<Integer>();  }
    Entry(String wrd, int... nums) {
        key = wrd;
        occurs = new DLList<Integer>();
        for(int x : nums) occurs.add(x);
    }
    /** Get the key
     * @return the key value
     */
    public String getKey()                    { return key;                   }
    /** Get the list of Integers
     * @return the list of Integers
     */
    public DLList<Integer> getOccurrences()   { return occurs;                }
    /** Update the list of Integers by adding i to the end
     * @param i the number to add to the list
     */
    public void update(int i)                 { occurs.add(i);                }

    /** Compare the key value to a String value
     * @param other the String value to compare to the key
     * @return key.compareTo(other)
     */
    public int compareTo(String other)        { return key.compareTo(other);  }
    /** A hashcode based on the key value
     * @return key.hashCode()
     */
    public int hashCode()                     { return key.hashCode();        }
    /** Tests if <code>obj</code> equals the key
     * @param obj the thing to compare to the key
     * @return key.equals(obj)
     */
    public boolean equals(Object obj)         { return key.equals(obj);       }
    /** Constructs a String version of the Entry
     * @return a String version of the Entry
     */
    public String toString() {
        String ans = String.format("%15s:",key);
        ListIterator<Integer> it = occurs.listIterator(0);
        String sep               = "  ";

        while (it.hasNext()) {
            ans = ans + sep + it.next();
            sep = ", ";
        }
        return ans;
    }

}    
  