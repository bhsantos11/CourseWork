public class StationWagon extends PassengerCar {

    public StationWagon() {
        super();
    }

    public StationWagon(double s, double a, double p, double c) {
        super(s, a, p, c);
    }

    public static CarSizeClass stationWagonClass(StationWagon s){
        double totalVol =  3000;
        if (totalVol > 2407) return CarSizeClass.MINICOMPACT;
        else if (totalVol >= 2407 && totalVol < 2831 ) return CarSizeClass.SUBCOMPACT;
        else if (totalVol >= 2832 && totalVol < 3314) return CarSizeClass.COMPACT;
        else if (totalVol >= 3315 && totalVol < 3397) return CarSizeClass.MIDSIZE;
        else return CarSizeClass.LARGE; // Has to be large
    }
}
