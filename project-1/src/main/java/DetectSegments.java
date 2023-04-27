
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

    public void newValue(double value){
        if(yawValues.size() == 10){

            currentTotal = currentTotal - yawValues.peek();
            yawValues.remove();
            yawValues.add(value);
            currentTotal = currentTotal + value;

            if(currentlyStraight){
                if((currentTotal / 10.0) > 5.0 || (currentTotal / 10.0) < -5.0){
                    currentlyStraight = false;
                    Simulation.paused = true;
                    currentTotal = 0.0;
                    yawValues.clear();
                }
            }else{
                if(-5.0 < (currentTotal / 10.0) && (currentTotal / 10.0) < 5.0){
                    currentlyStraight = true;
                    Simulation.paused = true;
                    currentTotal = 0.0;
                    yawValues.clear();
                }
            }
        }
        else{ 
            yawValues.add(value);
            currentTotal = currentTotal + value;
        }
    }
}
