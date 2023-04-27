
import java.util.LinkedList;
import java.util.Queue;

public class DetectSegments {
    Queue<Double> yawValues;
    double currentTotalYAW;
    Queue<Double> accelValues;
    double currentTotalACCEL;
    boolean currentlyStraight;

    public DetectSegments(){
        yawValues = new LinkedList<>();
        accelValues = new LinkedList<>();
        currentTotalYAW = 0.0;
        currentTotalACCEL = 0.0;
        currentlyStraight = true;
    }

    public boolean newValue(double valueYAW, double valueACCEL){
        if(yawValues.size() == 10 && accelValues.size() == 10){

            currentTotalYAW = currentTotalYAW - yawValues.peek();
            yawValues.remove();
            yawValues.add(valueYAW);
            currentTotalYAW = currentTotalYAW + valueYAW;

            currentTotalACCEL = currentTotalACCEL - accelValues.peek();
            accelValues.remove();
            accelValues.add(valueACCEL);
            currentTotalACCEL = currentTotalACCEL + valueACCEL;

            if(currentlyStraight){
                if((currentTotalYAW / 10.0) > 5.0 || (currentTotalYAW / 10.0) < -5.0){
                    currentlyStraight = false;

                    /*
                    Code for testing segments, should be removed before submission
                    */

                    Simulation.paused = true;
                    if(currentTotalYAW > 5.0){
                        System.out.println("Now on a left turn segment");    
                    }

                    if(currentTotalYAW < -5.0){
                        System.out.println("Now on a right turn segment"); 
                    }
                    

                    currentTotalYAW = 0.0;
                    yawValues.clear();
                    currentTotalACCEL = 0.0;
                    accelValues.clear();
                    return true;
                }
                else{
                    return false;
                }
            }else{
                if(-5.0 < (currentTotalYAW / 10.0) && (currentTotalYAW / 10.0) < 5.0){
                    if(-0.5 < (currentTotalACCEL / 10.0) && (currentTotalACCEL / 10.0) < 0.5){
                       currentlyStraight = true; 
                       /* 
                        Code for testing segments, should be removed before submission
                        */

                        Simulation.paused = true;
                        System.out.println("Now on a straight segment");
                    

                        currentTotalYAW = 0.0;
                        yawValues.clear();
                        currentTotalACCEL = 0.0;
                        accelValues.clear();

                        return true;
                    }
                    else{
                        return false;
                    }
                }
                else{
                    return false;
                }
            }
        }
        else{

            yawValues.add(valueYAW);
            currentTotalYAW = currentTotalYAW + valueYAW;

            accelValues.add(valueACCEL);
            currentTotalACCEL = currentTotalACCEL + valueACCEL;
            return false;
        }
    }
}
