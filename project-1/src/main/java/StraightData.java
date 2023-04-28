public class StraightData extends SegmentData {
    private double maxVehSpeed;
    private double minVehSpeed;
    private double straightLength;

    public StraightData(GPSCoordinate gpsStart, GPSCoordinate gpsEnd, double avgVehSpeed, double maxAccel,
                        double minAccel, double maxVehSpeed, double minVehSpeed, double straightLength) {
        super(gpsStart, gpsEnd, avgVehSpeed, maxAccel, minAccel);
        this.maxVehSpeed = maxVehSpeed;
        this.minVehSpeed = minVehSpeed;
        this.straightLength = straightLength;
    }

    @Override
    public String toString() {
        return String.format(" %8s | %s | %6.2f km/h | %6.2f km/h | %6.2f km",
                "Straight", super.toString(), maxVehSpeed, minVehSpeed, straightLength);
    }
}
