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

    @Override
    public String toString() {
        return String.format(" %8s | %s | %6s      | %6s      | %10s | %10s | %8.2f deg | %5.2f deg",
                "Curve", super.toString(), "", "", "", curveDirection.name(), curveAngle, maxStrAngle);
    }
}
