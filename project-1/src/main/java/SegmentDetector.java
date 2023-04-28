import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SegmentDetector {
    private Queue<Double> yawValues;
    private double totalYaw;
    private Queue<Double> accelValues;
    private double totalAccel;
    private boolean currentlyStraight;
    private final int windowSize = 10;
    private SegmentData segment;
    private List<SegmentData> segmentDataList;

    public SegmentDetector(){
        yawValues = new LinkedList<>();
        accelValues = new LinkedList<>();
        totalYaw = 0.0;
        totalAccel = 0.0;
        currentlyStraight = true;
        segmentDataList = new ArrayList<>();
    }

    public String parse(SensorData sensorData){
        if (segment == null) {
            segment = new StraightData(new GPSCoordinate(sensorData.getCurTime(), sensorData.getGpsLat(), sensorData.getGpsLon()),
                    null, 0, 0, 0, 0, 0, 0);
        }

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
                    currentlyStraight = false;
//                    Simulation.paused = true;

                    if(avgYaw > 5.0){
                        //TODO set data in previous segment
                        segmentDataList.add(segment);
                        segment = new CurveData(null, null, 0, 0, 0, CurveDirection.LEFT, 0, 0);
                    } else if(avgYaw < -5.0){
                        segmentDataList.add(segment);
                        segment = new CurveData(null, null, 0, 0, 0, CurveDirection.RIGHT, 0, 0);
                    }

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
//                        Simulation.paused = true;

                        segmentDataList.add(segment);
                        segment = new StraightData(null, null, 0, 0, 0, 0, 0, 0);

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
