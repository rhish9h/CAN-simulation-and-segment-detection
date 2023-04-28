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
        return "SegmentData{" +
                "gpsStart=" + gpsStart +
                ", gpsEnd=" + gpsEnd +
                ", avgVehSpeed=" + avgVehSpeed +
                ", maxAccel=" + maxAccel +
                ", minAccel=" + minAccel +
                '}';
    }
}
