public class CurveData extends SegmentData {
    private CurveDirection curveDirection;
    private double curveAngle;
    private double maxStrAngle;

    public CurveData(GPSCoordinate gpsStart, GPSCoordinate gpsEnd, double avgVehSpeed, double maxAccel,
                     double minAccel, CurveDirection curveDirection, double curveAngle, double maxStrAngle) {
        super(gpsStart, gpsEnd, avgVehSpeed, maxAccel, minAccel);
        this.curveDirection = curveDirection;
        this.curveAngle = curveAngle;
        this.maxStrAngle = maxStrAngle;
    }

    public CurveDirection getCurveDirection() {
        return curveDirection;
    }

    public double getCurveAngle() {
        return curveAngle;
    }

    public double getMaxStrAngle() {
        return maxStrAngle;
    }
}
