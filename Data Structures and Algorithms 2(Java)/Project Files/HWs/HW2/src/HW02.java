import java.util.Arrays;

public class HW02 {
    //////////////////////////////////////////////////////////
    // multFibIter(n) = multFibIter(n-1) * multFibIter(n-2), computed iteratively

    public static int multFibIter(int n) {
        int ans = 0;
        if(n == 0) return 1;
        if(n == 1) return 2;
        while(n > 0){
            ans = (n -1) * (n -2);
            n--;
        }
        return ans;
    }

    //////////////////////////////////////////////////////////
    // multFibIter(n) = multFibIter(n-1) * multFibIter(n-2), computed recursively
    public static int multFibRec(int n) {
        if(n ==  0){
            return 1;
        }
        if(n == 1){
            return 2;
        }
        return (multFibRec(n - 1) * multFibRec(n - 2));
    }

    //////////////////////////////////////////////////////////////////////
    // compute day of week, Key Value method
    public static int weekdayKeyValue(int day, int month, int year) {
        // Do Sept 14 1752 Test
        if(year <= 1752 && month <= 9){
            if (year < 1752) return -1;
            if (month <= 9){
                if(month < 9) return -1;
                if(day <= 14) return -1;
            }
        }
        // Implement key value test
        int keyValues[] = {1, 4, 4, 0, 2, 5, 0, 3, 6, 1, 4, 6}; // Key Value Table
        int yearDigits = year % 100; // Should return last 2 digits



        return 0;
    }

    //////////////////////////////////////////////////////////////////////
    // compute area of triangle usins SAS
    public static double sasArea(double sideA, double degrees, double sideB) {
        // Remember to convert degrees to radians!
        return 150.0; // FIX ME!!
    }

    //////////////////////////////////////////////////////////
    // days(y,m) = the number of days in month n of year y
    public static int days(int year, int month) {
        // Thirty days have September, April, June and November.
        // All the rest have thirty-one,
        // Except February the only one
        // Which Leap Years change each fourth time
        // From twenty-eight to twenty-nine.
        // Century 100s don't always leap,
        // each 400 years that leap we keep.
        return Integer.MIN_VALUE; // FIX ME!!!
    }

    //////////////////////////////////////////////////////////
    //  Array of month abbreviations

    private static String monthNames[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    private static String dayNames[] = {"Saturday",
            "Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday"};

    //////////////////////////////////////////////////////////
    // A little method for printing out tests for problem 3.
    public static void testDateToDay(int day, int year, int month) {
        // FIX ME!!  Your code must correctly handle tense
        //           (past, present, future); this code does not.
        System.out.println(day + " " + monthNames[month + 1] + " " + year +
                " is/was/will be a " +
                dayNames[weekdayKeyValue(day, month, year)]);
    }


    //////////////////////////////////////////////////////////
    // A little method for printing out tests for problem 5.
    public static void testDays(int year, int month) {
        System.out.println(month + "/" + year + " has " +
                days(year, month) + " days");
    }

    //////////////////////////////////////////////////////////

    /**
     * The main method for testing the assignment's methods.
     */
    public static void main(String argv[]) {

        /////////////////////////////////////////////////////////
        System.out.println("\n**Tests for Problem 1**");
        for (int i = 0; i <= 9; i++)
            System.out.println("multFibIter(" + i + ") =\t" + multFibIter(i));

        /////////////////////////////////////////////////////////
        System.out.println("\n**Tests for Problem 2**");
        for (int i = 0; i <= 9; i++)
            System.out.println("multFibRec(" + i + ") =\t" + multFibRec(i));


        /////////////////////////////////////////////////////////
        System.out.println("\n**Tests for Problem 3**");

        testDateToDay(29, 2064, 1);
        testDateToDay(4, 2017, 1);
        testDateToDay(10, 2017, 9);

        /*
        /////////////////////////////////////////////////////////
        System.out.println("\n**Tests for Problem 4**");

        // {side A, angle in degrees, side B}
        double sasInputs[][] = {{20, 30, 30}, {10, 90, 10}};

        for (int i = 0; i < sasInputs.length; i++) {
            System.out.print("For a triangle with side A = " + sasInputs[i][0]);
            System.out.print(", side B = " + sasInputs[i][2]);
            System.out.println(", and an included angle of " + sasInputs[i][1] + ",");
            System.out.println("\tthe area is "
                    + sasArea(sasInputs[i][0],
                    sasInputs[i][1],
                    sasInputs[i][2]) + " square units.");
        }

        /////////////////////////////////////////////////////////
        System.out.println("\n**Tests for Problem 5**");
        testDays(1900,2);  // 28
        testDays(2000,2);  // 29
        testDays(2004,2);  // 29
        testDays(2016,1);  // 31
        testDays(2016,2);  // 29
        testDays(2017,9);  // 30
        testDays(2017,12); // 3
        */
    }
}
