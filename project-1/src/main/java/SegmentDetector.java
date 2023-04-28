
import java.util.LinkedList;
import java.util.Queue;

public class SegmentDetector {
    private Queue<Double> yawValues;
    private double totalYaw;
    private Queue<Double> accelValues;
    private double totalAccel;
    private boolean currentlyStraight;
    private final int windowSize = 10;
    private String segment;

    public SegmentDetector(){
        yawValues = new LinkedList<>();
        accelValues = new LinkedList<>();
        totalYaw = 0.0;
        totalAccel = 0.0;
        currentlyStraight = true;
    }

    public String parseValue(double yawRate, double latAccel){
        segment = null;

        if(yawValues.size() == windowSize && accelValues.size() == windowSize){

            totalYaw -= yawValues.peek();
            yawValues.remove();
            yawValues.add(yawRate);
            totalYaw += yawRate;

            totalAccel -= accelValues.peek();
            accelValues.remove();
            accelValues.add(latAccel);
            totalAccel += latAccel;

            double avgYaw = totalYaw / windowSize;
            double avgAccel = totalAccel / windowSize;

            if(currentlyStraight){
                if(avgYaw > 5.0 || avgYaw < -5.0){
                    currentlyStraight = false;
                    Simulation.paused = true;

                    if(avgYaw > 5.0){
                        segment = "Now on a left turn segment";
                        System.out.println("Now on a left turn segment");    
                    } else if(avgYaw < -5.0){
                        segment = "Now on a right turn segment";
                        System.out.println("Now on a right turn segment"); 
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
                        Simulation.paused = true;

                        segment = "Now on a straight segment";
                        System.out.println("Now on a straight segment");

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
            yawValues.add(yawRate);
            totalYaw = totalYaw + yawRate;

            accelValues.add(latAccel);
            totalAccel = totalAccel + latAccel;
            return null;
        }

        return segment;
    }
}
