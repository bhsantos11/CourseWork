/*********************************************************
 * Car is a simple representation for passenger vehicles
 *
 * @author
 *   Louis Reasoner and Sally Segfault
 * @version
 *   21 Sep 2017
 *
 **********************************************************/
// IMPORTANT
// I tried to change all the units to metric but I think there might be some conversion errors

// TEST CASE
    /*
    How sporty was the 1972 Super Beetle?  56
Prius total capacity: 3117.0 which makes it a MINICOMPACT
    */
public class Sedan extends PassengerCar{


    /** Constructs a new Sedan with parameters
     * <i>speed:</i> 110 MPH, <i>acceleration:</i> 9.0 seconds,
     * <i>passengerVol:</i> 95 cubic feet, <i>cargoVol:</i> 20 cf
     */
    public Sedan() {
        super();
    }

    /** Constructs a new Sedan with the specified values of
     * <i>speed</i>, <i>acceleration</i>, <i>pssengerVol</i>, and <i>cargoVol</i>.
     * @param s the Sedan's top speed in MPH
     * @param a the Sedan's acceleration (0 to 60 MPH time)
     * @param p the Sedan's passenger volume
     * @param c the Sedan's cargo volume
     */

    public Sedan(double s, double a, double p, double c) {
        super(s, a, p, c);
    }


    public CarSizeClass sedanClass() {
        double totalVol = getCargoVol() + getPassengerVol();
        if (totalVol > 2407) return CarSizeClass.MINICOMPACT;
        else if (totalVol >= 2407 && totalVol < 2831 ) return CarSizeClass.SUBCOMPACT;
        else if (totalVol >= 2832 && totalVol < 3314) return CarSizeClass.COMPACT;
        else if (totalVol >= 3315 && totalVol < 3397) return CarSizeClass.MIDSIZE;
        else return CarSizeClass.LARGE; // Has to be large
    }


    public static void main(String[] argv){
        Sedan superBeetle = new Sedan();

        // Herbie the Love Bug
        superBeetle.setAcceleration(18.2);
        superBeetle.setSpeed(81);

        // My old 2008 Prius
        Sedan prius = new Sedan(278.417, 11.1, 2721, 396);

        // A P100D in Ludicrous+ mode
        Sedan teslaS = new Sedan(209, 10.7, 2661, 877);

        System.out.println("How sporty was the 1972 Super Beetle?  " + superBeetle.sportiness());
        System.out.println("Prius total capacity: " + (prius.getPassengerVol() + prius.getCargoVol())
                + " which makes it a " + prius.sedanClass());

        if (teslaS.sportiness() > prius.sportiness()) {
            System.out.println("Teslas are sportier than Priuses!");
        }

    }
};
