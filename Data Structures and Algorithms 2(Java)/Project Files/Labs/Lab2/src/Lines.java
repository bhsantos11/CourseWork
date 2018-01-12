import java.util.Scanner;
import java.lang.Math;

public class Lines {
    /** Computes the slope between (x1,y1) and (x2,y2) **/
    public static double calcSlope(double x1, double y1,
                                   double x2, double y2)
    throws Exception
    {
        if ((x2 - x1)== 0 && (y2 - y1)==0) throw new Exception("Both numerator and denominator equals 0");
        if (x1 == x2) throw new Exception("x1 == x2");

        double slope = ((y2 - y1) / (x2 - x1));


        return slope;
    }

    /** Prompts the user for four numbers, x1, y1, x2, and y2, and then
     * prints the slope of the line between the two points
     */
    public static void main(String argv[]) throws Exception
    {
        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter four numbers: ");

        double x1 = stdin.nextDouble();
        double y1 = stdin.nextDouble();
        double x2 = stdin.nextDouble();
        double y2 = stdin.nextDouble();

        try {
            double slope = calcSlope(x1, y1, x2, y2);
            System.out.print("The slope of the line passing through (");
            System.out.print(x1 + ", " + y1 + ") and (" + x2 + ", " + y2);
            System.out.println(") is " + slope + ".");
        }
        catch (Exception e){
            System.out.print("Sorry can't do it.");

        }



    }
}
    
    
    
    