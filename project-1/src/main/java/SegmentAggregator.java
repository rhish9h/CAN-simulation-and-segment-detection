/**
 * Aggregator class that collects all the data required for a segment,
 * Calculates rolling averages, min and maxes
 * It can build Curve or Straight segment object depending on need
 */
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

    /**
     * Clear all the data from this class and set to default values
     */
    public void clear() {
        this.segmentStartTime = 0;
        this.gpsStart = null;
        this.gpsEnd = null;
        this.avgVehSpeed = 0;
        this.maxLatAccel = -99;
        this.minLatAccel = 99;
        this.curveDirection = null;
        this.curveAngle = 0;
        this.maxStrAngle = -99;
        this.maxLonAccel = -99;
        this.minLonAccel = 99;
        this.maxVehSpeed = -99;
        this.minVehSpeed = 99;
        this.straightLength = 0;
    }

    /**
     * Update the current segment data based on the new sensor data received
     * @param sensorData all the sensor data to be aggregated
     * @param curveDirection direction where the curve bends
     */
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
        if (sensorData.getLatAccel() < minLatAccel) {
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
            minLonAccel = sensorData.getLonAccel();
        }
        if (sensorData.getVehSpeed() > maxVehSpeed) {
            maxVehSpeed = sensorData.getVehSpeed();
        }
        if (sensorData.getVehSpeed() < minVehSpeed) {
            minVehSpeed = sensorData.getVehSpeed();
        }
    }

    /**
     * Builder for Straight data object
     * @param gpsEnd GPS coordinate where segment ends
     * @return StraightData object built by existing segment data
     */
    public StraightData buildStraightData(GPSCoordinate gpsEnd) {
        this.gpsEnd = gpsEnd;
        straightLength = avgVehSpeed * (gpsEnd.getOffset() - segmentStartTime) / 1000 / 60 / 60;
        return new StraightData(gpsStart, gpsEnd, avgVehSpeed, maxLonAccel, minLonAccel,
                maxVehSpeed, minVehSpeed, straightLength);
    }

    /**
     * Builder for Curve data object
     * @param gpsEnd GPS coordinate where segment ends
     * @return CurveData object built by existing segment data
     */
    public CurveData buildCurveData(GPSCoordinate gpsEnd) {
        this.gpsEnd = gpsEnd;
        curveAngle /= ((gpsEnd.getOffset() - segmentStartTime) / 10);
        return new CurveData(gpsStart, gpsEnd, avgVehSpeed, maxLatAccel, minLatAccel, curveDirection,
                curveAngle, maxStrAngle);
    }
}
