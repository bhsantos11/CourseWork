// Full disclosure, I don't know much about cars
public class PassengerCar {
    private double speed, acceleration, passengerVol, cargoVol;
    PassengerCar(){
        speed = 200; // km/h
        acceleration = 4.5; // In m/s^2
        passengerVol = 3000; // Cubic liters
        cargoVol = 500; // Also cubic liters
    }

    PassengerCar(double s, double a, double p, double c){
        speed = s;
        acceleration = a;
        passengerVol = p;
        cargoVol = c;
    }

    PassengerCar(int defSpeed){// One just for speed
        speed = defSpeed;
        if (speed > 200){
            acceleration = 8.0;
            passengerVol = 1500;
            cargoVol = 100;
        }
        else if (speed < 100){
            acceleration = 2.0;
            passengerVol = 5000;
            cargoVol = 3000;
        }
        else{
            acceleration = 4.5; // In m/s^2
            passengerVol = 3000; // Cubic liters
            cargoVol = 500; // Also cubic liters
        }// end of ifs
    } // end of constructor with params

    // Setters and Getters
    //
    /** Sets the speed of a Sedan.
     * @param s the value to which to set the speed
     */
    public void setSpeed(double s){ speed = s;}

    /** Sets the acceleration of a Sedan.
     * @param a the value to which to set the acceleration
     */
    public void setAcceleration(double a){acceleration = a; }

    /** Sets the passengerVol of a Sedan.
     * @param p the value to which to set the passengerVol
     */
    public void setPassengerVol(double p) {passengerVol = p; }

    /** Sets the cargoVol of a Sedan.
     * @param c the value to which to set the cargoVol
     */
    public void setCargoVol(double c) { cargoVol = c;}

    /** Gets the speed of a Sedan.
     * @return the Sedan's speed
     */
    public double getSpeed(){ return speed; }

    /** Gets the acceleration of a Sedan.
     * @return the Sedan's acceleration
     */
    public double getAcceleration(){ return  acceleration;}

    /** Gets the passengerVol of a Sedan.
     * @return the Sedan's passengerVol
     */
    public double getPassengerVol(){ return passengerVol;}

    /** Gets the cargoVol of a Sedan.
     * @return the Sedan's cargoVol
     */
    public double getCargoVol(){ return cargoVol; }


    public enum CarSizeClass {
        MINICOMPACT,
        SUBCOMPACT,
        SMALL,
        COMPACT,
        MIDSIZE,
        LARGE
    };

    public int sportiness(){
        //90 miles is around 145 km so 90 becomes 145
        // 0-60 = 12 so 12 becomes 2

        int sportiness = ((int)speed - 145) + (((int)acceleration - 6) * 10);
        return sportiness;
    }

}
