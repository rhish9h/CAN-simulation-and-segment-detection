import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Detector that identifies a segment, classifies as Curve or Straight
 */
public class SegmentDetector {
    private Queue<Double> yawValues;
    private double totalYaw;
    private Queue<Double> accelValues;
    private double totalAccel;
    private boolean currentlyStraight;
    private final int windowSize = 10;
    private SegmentData segment;
    private List<SegmentData> segmentDataList;
    private SegmentAggregator aggregator;

    public SegmentDetector(){
        yawValues = new LinkedList<>();
        accelValues = new LinkedList<>();
        totalYaw = 0.0;
        totalAccel = 0.0;
        currentlyStraight = true;
        segmentDataList = new ArrayList<>();
        aggregator = new SegmentAggregator();
    }

    /**
     * Parse one record of sensor data at a time,
     * maintain a sliding window average of windowSize (10) to deal with anomalies,
     * Based on threshold values for yaw rate and lateral acceleration,
     * classify whether segment is curve or straight
     * @param sensorData one record of sensor data
     * @return String consisting of segment information that was detected
     */
    public String parse(SensorData sensorData){
        aggregator.aggregate(sensorData, null);

        if(yawValues.size() == windowSize && accelValues.size() == windowSize){

            totalYaw -= yawValues.peek();
            yawValues.remove();
            yawValues.add(sensorData.getYawRate());
            totalYaw += sensorData.getYawRate();

            totalAccel -= accelValues.peek();
            accelValues.remove();
            accelValues.add(sensorData.getLatAccel());
            totalAccel += sensorData.getLatAccel();

            double avgYaw = totalYaw / windowSize;
            double avgAccel = totalAccel / windowSize;

            if(currentlyStraight){
                if(avgYaw > 5.0 || avgYaw < -5.0){
                    segment = aggregator.buildStraightData(new GPSCoordinate(sensorData.getCurTime(),
                            sensorData.getGpsLat(), sensorData.getGpsLon()));
                    segmentDataList.add(segment);
                    aggregator.clear();

                    if(avgYaw > 5.0){
                        aggregator.aggregate(sensorData, CurveDirection.LEFT);
                    } else if(avgYaw < -5.0){
                        aggregator.aggregate(sensorData, CurveDirection.RIGHT);
                    }

                    currentlyStraight = false;
                    totalYaw = 0.0;
                    yawValues.clear();
                    totalAccel = 0.0;
                    accelValues.clear();
                }
                else{
                    return null;
                }
            }else{
                if(-5.0 < avgYaw && avgYaw < 5.0){
                    if(-0.5 < avgAccel && avgAccel < 0.5){
                        currentlyStraight = true;

                        segment = aggregator.buildCurveData(new GPSCoordinate(sensorData.getCurTime(),
                                sensorData.getGpsLat(), sensorData.getGpsLon()));
                        segmentDataList.add(segment);
                        aggregator.clear();
                        aggregator.aggregate(sensorData, null);

                        totalYaw = 0.0;
                        yawValues.clear();
                        totalAccel = 0.0;
                        accelValues.clear();
                    } else{
                        return null;
                    }
                } else{
                    return null;
                }
            }
        } else{
            yawValues.add(sensorData.getYawRate());
            totalYaw = totalYaw + sensorData.getYawRate();

            accelValues.add(sensorData.getLatAccel());
            totalAccel = totalAccel + sensorData.getLatAccel();
            return null;
        }

        return segment.toString();
    }

    public List<SegmentData> getSegmentDataList() {
        return segmentDataList;
    }
}
