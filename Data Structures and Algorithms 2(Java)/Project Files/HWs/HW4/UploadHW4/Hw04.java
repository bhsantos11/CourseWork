import java.util.Arrays;

// File: Hw04.java
/********************************************************************
 * Tests for the ArrayStack and TimeSeries classes.
 * @author
 *   Bernardo Santos
 * @version
 *   2-Oct-2017
 *******************************************************************/

public class Hw04 {
    /** Tests for the ArrayStack and the TimeSeries classes
     * @param argv command line arguments (which we don't use)
     */
    public static void main(String [] argv) {
        // Part I: Tests for the ArrayStack class

        // INDEX          0   1   2   3   4   5   6   7   8   9  10  11  12
        int[] init   = {  0,  6, 10,  6,  2,  6,  6, 10,  8,  8,  2,  6,  4};
        int[] census = {  1,  0,  2,  0,  1,  0,  5,  0,  2,  0,  2};

        ArrayStack a = new ArrayStack(init);


        // Part I: Tests for the ArrayStack class
        System.out.println("\nTESTS OF THE ArrayStack CLASS");
        /////////////////////////////////////////////////////////////////////
        // Tests for contains
        System.out.println("\n**1** Running tests for contains");
        for (int i=0; i<=10; i++) {
            boolean result = a.contains(i);
            if (result)
                System.out.print("  a contains "+i);
            else
                System.out.print("  a doesn't contain "+i);
            if (result==(i%2!=0))
                System.out.print("\tPROBLEM: This is wrong!!");
            System.out.println();
        }

        /////////////////////////////////////////////////////////////////////
        // Tests for count
        System.out.println("\n**2** Running tests for count");

        for (int i=0; i<=10; i++) {
            int cnt = a.count(i);
            System.out.print("  a.count("+i+") = "+cnt);
            if (cnt!=census[i])
                System.out.print("\tPROBLEM: This should be " + census[i]+"!!");
            System.out.println();
        }

        /////////////////////////////////////////////////////////////////////
        // Tests for delete
        System.out.println("\n**3** Running tests for delete");
        for (int i=0; i<=10; i++) a.delete(i);
        for (int i=0; i<=10; i++) {
            int cnt = a.count(i);
            int correct = Math.max(0,census[i]-1);
            System.out.print("  a.count("+i+") = "+cnt);
            if (cnt!=correct)
                System.out.print("\tPROBLEM: This should be " + correct+"!!");
            System.out.println();
        }

        /////////////////////////////////////////////////////////////////////
        // Tests for drop
        System.out.println("\n**4** Running tests for drop");
        int[] tst1 = {0,1,2,3,4,5,6,7,8,9,10,11,12};
        int[] tst2 = {4,5,6,7,8,9,10,11,12};
        int[] tst3 = {6,7,8,9,10,11,12};
        int[] tst4 = {11,12};
        ArrayStack a0 = new ArrayStack(tst1);
        ArrayStack a1 = new ArrayStack(tst1);
        ArrayStack a2 = new ArrayStack(tst2);
        ArrayStack a3 = new ArrayStack(tst3);
        ArrayStack a4 = new ArrayStack(tst4);
        ArrayStack a5 = new ArrayStack();
        System.out.print("  Checking a0.drop(0)");
        a0.drop(0);
        if (a0.equals(a1))  System.out.println("\t Passed");
        else                System.out.println("\t Failed! is: "+a0
                + "  should be: "+ a1);
        System.out.print("  Checking a0.drop(4)");
        a0.drop(4);
        if (a0.equals(a2))  System.out.println("\t Passed");
        else                System.out.println("\t Failed! is: "+a0
                + "  should be: "+ a2);
        System.out.print("  Checking a0.drop(2)");
        a0.drop(2);
        if (a0.equals(a3))  System.out.println("\t Passed");
        else                System.out.println("\t Failed! is: "+a0
                + "  should be: "+ a3);
        System.out.print("  Checking a0.drop(5)");
        a0.drop(5);
        if (a0.equals(a4))  System.out.println("\t Passed");
        else                System.out.println("\t Failed! is: "+a0
                + "  should be:  "+ a4);
        System.out.print("  Checking a0.drop(5)");
        a0.drop(2);
        if (a0.equals(a5))  System.out.println("\t Passed");
        else                System.out.println("\t Failed! is: "+a0
                + "  should be:  "+ a5);



        /////////////////////////////////////////////////////////////////////
        // Tests for locationOf
        System.out.println("\n**5** Running tests for locationOf");
        int[] tst = {9,7,9,6,3, 4,8,4,9,2, 10,9,2,7,1, 7,7,2,3,5};
        int[][] loc = {{14},{9,12,17},{4,18},{5,7},{19},{3},
                {1,13,15,16},{6},{0,2,8,11},{10}};
        ArrayStack as = new ArrayStack(tst);
        ArrayStack ans;
        for(int e=1;e<10;e++) {
            System.out.print("  Checking as.locationsOf("+e+")\t");
            ans = as.locationsOf(e);
            if (Arrays.equals(ans.toArray(),loc[e-1]))
                System.out.println(" Passed");
            else
                System.out.println(" Failed! is "+ans + "  should be: "
                        +Arrays.toString(loc[e-1]));
        }



//    // Part II: Tests for the TimeSeries class
//
        System.out.println("\n\nTESTS OF THE TimeSeries CLASS");

        TimeSeries ts1 = new TimeSeries(2,3,5,7,2,9,1);
        TimeSeries ts2 = new TimeSeries(6,68,11,12,58,41,75,73,70,
                78,91,94,77,54,53,85,32,42,66);

        /////////////////////////////////////////////////////////////////////
        // Tests for timesAbove
        System.out.println("\n**6** Running tests for timesAbove");
        int[] target1 = {7,6,4,3,3,2,2,1,1,0};
        int[] target2 = {4,4,3,2,2,1,1,0,0,0};
        System.out.println("  Checking ts1.timesAbove(i,0,6) for i=0,...,9");
        for(int i=0;i<10;i++) {
            int result = ts1.timesAbove(i,0,6);
            if (result!=target1[i])
                System.out.println("\tPROBLEM: ts1.timesAbove("+i+",0,6) = "
                        + result + " but should be " + target1[i]);
        }

        System.out.println("  Checking ts1.timesAbove(i,1,4) for i=0,...,9");
        for(int i=0;i<10;i++) {
            int result = ts1.timesAbove(i,1,4);
            if (result!=target2[i])
                System.out.println("\tPROBLEM: ts1.timesAbove("+i+",1,4) = "
                        + result + " but should be " + target2[i]);
        }
        System.out.println("  Testing timesAbove's execeptions");
        // Add two tests that timesAbove throws its exceptions correctly
        // (Use try/catch blocks.)

        /////////////////////////////////////////////////////////////////////
        // Tests for oscillation

        System.out.println("\n**7** Running tests for oscillation");
        int[] target3 = {88,83,83,82,62,62,62,62,62,62,62,62,53,53,53,53,34,24, 0};
        int[] target4 = { 0,62,62,62,62,62,69,69,69,72,85,88,88,88,88,88,88,88,88};
        System.out.println("  Checking ts2.oscillation(i,tst2.length()-1) for i=0,...,tst2.length()-1 ");
        for(int i=0;i<ts2.length();i++) {
            int result = ts2.oscillation(i,ts2.length()-1);
            if (result!=target3[i])
                System.out.println("\tPROBLEM: ts2.oscillation("+i+",tst2.length()-1) = "
                        + result + " but should be " + target3[i]);
        }
        System.out.println("  Checking ts2.oscillation(0,i) for i=0,...,tst2.length()-1 ");
        for(int i=0;i<ts2.length();i++) {
            int result = ts2.oscillation(0,i);
            if (result!=target4[i])
                System.out.println("\tPROBLEM: ts2.oscillation(0,"+i+") = " + result
                        + " but should be " + target4[i]);
        }
        System.out.println("  Testing oscillation's execeptions");
        // Add two tests that oscillation throws its exceptions correctly
        // (Use try/catch blocks.)

        /////////////////////////////////////////////////////////////////////
        // Tests for runningAvg

        System.out.println("\n**8** Running tests for runningAvg");
        int[][] tsa =
                { {6, 68, 11, 12, 58, 41, 75, 73, 70, 78, 91, 94, 77, 54, 53, 85, 32, 42},
                        {37, 39, 11, 35, 49, 58, 74, 71, 74, 84, 92, 85, 65, 53, 69, 58, 37},
                        {28, 30, 27, 37, 58, 63, 72, 73, 79, 87, 87, 75, 61, 64, 56, 53},
                        {24, 37, 30, 46, 61, 64, 74, 78, 83, 85, 79, 69, 67, 56, 53},
                        {31, 38, 39, 51, 63, 67, 77, 81, 82, 78, 73, 72, 60, 53},
                        {32, 44, 45, 54, 65, 71, 80, 80, 77, 74, 75, 65, 57},
                        {38, 48, 48, 58, 69, 74, 79, 76, 73, 76, 69, 62},
                        {43, 51, 52, 62, 72, 74, 76, 73, 75, 70, 66},
                        {46, 54, 56, 65, 73, 72, 73, 75, 70, 67},
                        {49, 57, 60, 66, 71, 70, 75, 70, 67},
                        {53, 61, 61, 65, 69, 71, 71, 68},
                        {56, 62, 61, 64, 70, 68, 68},
                        {58, 61, 60, 66, 67, 66},
                        {57, 61, 62, 63, 65},
                        {57, 62, 60, 62},
                        {59, 60, 59},
                        {57, 59},
                        {56}
                };
        for(int i=0;i<ts2.length()-1;i++) {
            System.out.println("  Checking ts2.runningAvg("+(i+1)+")");
            TimeSeries result = ts2.runningAvg(i+1);
            if (!Arrays.equals(result.toArray(),tsa[i]))
                System.out.println("\t PROBLEM: ts2.runningAvg("+(i+1)+") = "
                        + result + "\n\t\t but should be "
                        + Arrays.toString(tsa[i]));
        }
        System.out.println("  Testing runningAvg's execeptions");
        // Add two tests that oscillation throws its exceptions correctly
        // (Use try/catch blocks.)


    }
}