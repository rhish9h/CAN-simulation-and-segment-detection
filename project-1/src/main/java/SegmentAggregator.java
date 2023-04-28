public class SegmentAggregator {
    private double segmentStartTime;

    private GPSCoordinate gpsStart;
    private GPSCoordinate gpsEnd;
    private double avgVehSpeed;

    private double maxLatAccel;
    private double minLatAccel;
    private CurveDirection curveDirection;
    private double curveAngle;
    private double maxStrAngle;

    private double maxLonAccel;
    private double minLonAccel;
    private double maxVehSpeed;
    private double minVehSpeed;
    private double straightLength;

    public void clear() {
        this.segmentStartTime = 0;
        this.gpsStart = null;
        this.gpsEnd = null;
        this.avgVehSpeed = 0;
        this.maxLatAccel = 0;
        this.minLatAccel = 0;
        this.curveDirection = null;
        this.curveAngle = 0;
        this.maxStrAngle = 0;
        this.maxLonAccel = 0;
        this.minLonAccel = 0;
        this.maxVehSpeed = 0;
        this.minVehSpeed = 0;
        this.straightLength = 0;
    }

    public void aggregate(SensorData sensorData, CurveDirection curveDirection) {
        if (segmentStartTime == 0) {
            segmentStartTime = sensorData.getCurTime();
        }
        if (gpsStart == null) {
            gpsStart = new GPSCoordinate(sensorData.getCurTime(), sensorData.getGpsLat(), sensorData.getGpsLon());
        }
        avgVehSpeed = (avgVehSpeed + sensorData.getVehSpeed()) / 2;
        if (sensorData.getLatAccel() > maxLatAccel) {
            maxLatAccel = sensorData.getLatAccel();
        }
        if (sensorData.getLonAccel() < minLatAccel) {
            minLatAccel = sensorData.getLatAccel();
        }
        if (curveDirection != null) {
            this.curveDirection = curveDirection;
        }
        curveAngle += sensorData.getYawRate();
        if (Math.abs(sensorData.getStrAngle()) > Math.abs(maxStrAngle)) {
            maxStrAngle = sensorData.getStrAngle();
        }
        if (sensorData.getLonAccel() > maxLonAccel) {
            maxLonAccel = sensorData.getLonAccel();
        }
        if (sensorData.getLonAccel() < minLonAccel) {
            maxLonAccel = sensorData.getLonAccel();
        }
        if (sensorData.getVehSpeed() > maxVehSpeed) {
            maxVehSpeed = sensorData.getVehSpeed();
        }
        if (sensorData.getVehSpeed() < minVehSpeed) {
            minVehSpeed = sensorData.getVehSpeed();
        }
    }

    public StraightData buildStraightData(GPSCoordinate gpsEnd) {
        this.gpsEnd = gpsEnd;
        straightLength = avgVehSpeed * (gpsEnd.getOffset() - segmentStartTime) / 1000_000 / 60 / 60;
        return new StraightData(gpsStart, gpsEnd, avgVehSpeed, maxLonAccel, minLonAccel,
                maxVehSpeed, minVehSpeed, straightLength);
    }

    public CurveData buildCurveData(GPSCoordinate gpsEnd) {
        this.gpsEnd = gpsEnd;
        curveAngle /= ((gpsEnd.getOffset() - segmentStartTime) / 10);
        return new CurveData(gpsStart, gpsEnd, avgVehSpeed, maxLatAccel, minLatAccel, curveDirection,
                curveAngle, maxStrAngle);
    }
}
