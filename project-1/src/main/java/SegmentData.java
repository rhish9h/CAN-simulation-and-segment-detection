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

    public GPSCoordinate getGpsStart() {
        return gpsStart;
    }

    public GPSCoordinate getGpsEnd() {
        return gpsEnd;
    }

    public double getAvgVehSpeed() {
        return avgVehSpeed;
    }

    public double getMaxAccel() {
        return maxAccel;
    }

    public double getMinAccel() {
        return minAccel;
    }
}
