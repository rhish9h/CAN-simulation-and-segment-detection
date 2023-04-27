
import java.util.LinkedList;
import java.util.Queue;

public class DetectSegments {
    Queue<Double> yawValues;
    double currentTotal;
    boolean currentlyStraight;

    public DetectSegments(){
        yawValues = new LinkedList<>();
        currentTotal = 0.0;
        currentlyStraight = true;
    }

    public boolean newValue(double value){
        if(yawValues.size() == 10){

            currentTotal = currentTotal - yawValues.peek();
            yawValues.remove();
            yawValues.add(value);
            currentTotal = currentTotal + value;

            if(currentlyStraight){
                if((currentTotal / 10.0) > 5.0 || (currentTotal / 10.0) < -5.0){
                    currentlyStraight = false;

                    /* 
                    Code for testing segments, should be removed before submission

                    Simulation.paused = true;
                    if(currentTotal > 5.0){
                        System.out.println("Now on a left turn segment");    
                    }

                    if(currentTotal < -5.0){
                        System.out.println("Now on a right turn segment"); 
                    }
                    */

                    currentTotal = 0.0;
                    yawValues.clear();
                    return true;
                }
                else{
                    return false;
                }
            }else{
                if(-5.0 < (currentTotal / 10.0) && (currentTotal / 10.0) < 5.0){
                    currentlyStraight = true;

                    /* 
                    Code for testing segments, should be removed upon submission

                    Simulation.paused = true;
                    System.out.println("Now on a straight segment");
                    */

                    currentTotal = 0.0;
                    yawValues.clear();

                    return true;
                }
                else{
                    return false;
                }
            }
        }
        else{
            yawValues.add(value);
            currentTotal = currentTotal + value;
            return false;
        }
    }
}
