/**
 * Abstract class for the Segment Data family, branches out into different types
 * like Curve Data and Straight Data
 * Holds all the data that is common to all children in the Segment Data family
 */
public abstract class SegmentData {
    private GPSCoordinate gpsStart;
    private GPSCoordinate gpsEnd;
    private double avgVehSpeed;
    private double maxAccel;
    private double minAccel;

    public SegmentData(GPSCoordinate gpsStart, GPSCoordinate gpsEnd, double avgVehSpeed, double maxAccel, double minAccel) {
        this.gpsStart = gpsStart;
        this.gpsEnd = gpsEnd;
        this.avgVehSpeed = avgVehSpeed;
        this.maxAccel = maxAccel;
        this.minAccel = minAccel;
    }

    @Override
    public String toString() {
        return String.format("%3.6f %3.6f | %3.6f %3.6f |  %4.2f km/h | %5.2f m/sec^s | %5.2f m/sec^2",
                gpsStart.getLatitude(), gpsStart.getLongitude(), gpsEnd.getLatitude(), gpsEnd.getLongitude(),
                avgVehSpeed, maxAccel, minAccel);
    }
}
