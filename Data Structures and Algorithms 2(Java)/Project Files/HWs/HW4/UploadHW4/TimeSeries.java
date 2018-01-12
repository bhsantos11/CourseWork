import java.util.Arrays;

/********************************************************************
 * This is simple class to represent time series.  
 * A TimeSeries has a length <code>n&ge;0</code> and a sequence
 * of values <code>v<sub>0</sub>,&hellip; v<sub>n-1</sub></code> where 
 * we think of <code>0</code> through <code>n-1</code> as times and 
 * <code>v<sub>t</sub></code> as the value of something at time 
 * <code>t</code>.
 * @author
 *   Bernardo Santos.
 * @version
 *   20-Sept-2016
 *******************************************************************/

public class TimeSeries {
    // Instance variables /////////////////////////////////////////////
    ArrayStack ts;

    // Constructors ///////////////////////////////////////////////////

    /** Construct an empty TimeSeries */
    public TimeSeries() {
        ts = new ArrayStack();
    }

    /** Construct a TimeSeries from an array
     * @param a the array to build the TimeSeries from
     * <b>Note</b> See <a href="http://math.hws.edu/javanotes/c7/s1.html#arrays.1.2">
     * http://math.hws.edu/javanotes/c7/s1.html#arrays.1.2</a> for an explanation
     * of what the <code>int...</code> is about.
     */
    public TimeSeries(int... a) {
        ts = new ArrayStack();
        ts.addAll(0,a);
    }

    // Instance Methods ///////////////////////////////////////////////

    /** Constructs a String representation of the TimeSeries.
     * @return a String representation of the TimeSeries
     */
    public String toString() {
        return Arrays.toString(ts.toArray());
    }

    /** Gets the length of the TimeSeries
     * @return the length of the TimeSeries
     */
    public int length() {
        return ts.size();
    }

    /** Gets the value at time <code>t</code> in the TimeSeries
     * @param t a time
     * @return the value at time <code>t</code>
     * @throws IndexOutOfBoundsException when the time is out of bounds
     */
    public int get(int t) {
        if (t < 0 || t >= ts.size()) throw new IndexOutOfBoundsException();
        return ts.get(t);
    }

    /** Returns the TimeSeries as an array.
     * @return the TimeSeries as an array
     */
    public int[] toArray() {
        return ts.toArray();
    }


    /** Returns the number of times that the series value is bigger
     * than <code>v</code> between time <code>start</code> to time
     * <code>end</code> (inclusive).
     * <br><br><b>Example:</b> Suppose <code>TimeSeries ts1</code>
     * consists of the values 2, 3, 5, 7, 2, 9, 1 (at times 0 through 6).
     * <br><br><b>Example:</b> Suppose <code>TimeSeries ts</code>
     * Then <code>ts.timesAbove(3,0,4)</code>
     * would return 2 since in this time interval only the 5 and 7 are above 3.
     * @param v the value to compare to
     * @param start the start time
     * @param end the end time
     * @return the number of times the value is above <code>v</code> in the time interval
     *      from <code>start</code> to <code>end</code> (inclusive).
     * @throws IllegalArgumentException when the time interval goes out of bounds
     * <br><br><strong>&diams;&diams;&diams; You write this one. &diams;&diams;&diams;</strong>
     */
    public int timesAbove(int v, int start, int end) {
        int count = 0;
        for(int x = start; x <= end; x++){
            if(ts.get(x) > v ) count++;
        }
        return count;
    }

    /** Returns the oscillation of the series between times <code>start</code>
     * and <code>end</code> (inclusive), i.e., the difference between the
     * largest value in this time interval and smallest value in this time
     * interval.
     * <br><br><b>Example:</b> Suppose <code>TimeSeries ts1</code>
     * consists of the values 2, 3, 5, 7, 2, 9, 1 (at times 0 through 6).
     * Then <code>ts.oscillation(0,5)</code> would return 7 (= 9-2).
     * @param start the start time
     * @param end the end time
     * @return oscillation between times <code>start</code> and
     *         <code>end</code> (inclusive)
     * @throws IllegalArgumentException when the time interval goes out of bounds
     * <br><br><strong>&diams;&diams;&diams; You write this one. &diams;&diams;&diams;</strong>
     */
    public int oscillation(int start, int end) {
        int max = ts.get(start);
        int min = ts.get(start);
        for(int x = start; x <= end; x++){
            if(ts.get(x)< min) min = ts.get(x);
            if(ts.get(x) > max) max = ts.get(x);
        }// end of For Loop
        int ans = max - min;
        return ans;
    }

    /** Builds a running average for the TimeSeries over periods of length <code>k</code>
     * and returns this as a TimeSeries. <br>
     * <b>Note:</b> To keep things simple, we compute averages via
     * <code>int</code> operations.  So the average of 4 and 5 is (4+5)/2=9/2=4.
     * <br><br><b>Example:</b> Suppose <code>TimeSeries ts1</code>
     * consists of the values 2, 3, 5, 7, 2, 9, 1 (at times 0 through 6).
     * Then <code>ts1.runningAvg(3)</code> would return the TimeSeries
     * consisting of values 3, 5, 4, 6 since
     * <ul>
     * <li> 3 = (2+3+5)/3,
     * <li> 5 = (3+5+7)/3,
     * <li> 4 = (5+7+2)/3,
     * <li> 6 = (7+2+9)/3, and
     * <li> 4 = (2+9+1)/3.
     * </ul>
     * @param k the period length for the running average
     * @return a TimeSeries of length <code>(this.length()+1-k)</code>  containing
     * the runningAverages, so the <code>t</code>-th element of this TimeSeries is the
     * average of the values in this time series from time <code>t</code> to time <code>t+k-1</code>.
     * @throws IllegalArgumentException when <code>k&lt;0</code> or <code>k&ge;this.length()</code>
     * <br><br><strong>&diams;&diams;&diams; You write this one. &diams;&diams;&diams;</strong>
     */
    public TimeSeries runningAvg(int k) {
        TimeSeries ans = new TimeSeries();
        int n = 0;
        while(n < (ts.size() + k)){
            int sum = 0;
            int count = 0;
            for(int x = n; x < (n + k); x++){
                sum += ts.get(x);
                count++;
            }
            int avg = sum/ count;
            ans.toArray()[0] = avg;
            n++;
        }// end of while
        return ans;
    }
}