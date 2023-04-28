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

    public double getMaxVehSpeed() {
        return maxVehSpeed;
    }

    public double getMinVehSpeed() {
        return minVehSpeed;
    }

    public double getStraightLength() {
        return straightLength;
    }

    @Override
    public String toString() {
        return "StraightData{" +
                "maxVehSpeed=" + maxVehSpeed +
                ", minVehSpeed=" + minVehSpeed +
                ", straightLength=" + straightLength +
                '}';
    }
}
