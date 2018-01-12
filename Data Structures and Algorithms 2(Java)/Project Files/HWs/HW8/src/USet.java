//package ods;

/** An interface for unordered sets 
 * <p><b>DO NOT CHANGE THIS FILE</b>
 */
public interface USet<T> extends Iterable<T> {

    /** Returns the size of the USet
     * @return the size of the USet */
    public int size();

    /** Adds an element to the USet if that element isn't
     * already in the USet.
     * @param x the value to be added
     * @return true iff x was successfully added to the USet
     */
    public boolean add(T x);

    /** Removes an element from the USet
     * @param x the element to be removed
     * @return x if this value was removed, null otherwise.
     */
    public T remove(T x);
    /** Tests if an element is in the USet
     * @param x the element to be sought
     * @return x if this value was found, null otherwise.
     */
    public T find(T x);
    /** Remove all elements from the USet */
    public void clear();
}