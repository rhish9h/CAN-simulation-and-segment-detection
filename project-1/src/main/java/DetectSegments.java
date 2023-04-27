import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Queue;

public class DetectSegments {
    Queue<Double> yawValues;
    double currentTotal;
    boolean currentlyStraight;

    public DetectSegments(){
        yawValues = new PriorityQueue<>(10);
        currentTotal = 0;
        currentlyStraight = true;
    }

    public void newValue(double value){
        if(yawValues.size() == 10){
            currentTotal = currentTotal - yawValues.peek();
            yawValues.remove();
            yawValues.add(value);
            currentTotal = currentTotal + value;

            if(currentlyStraight){
                if((currentTotal / 10.0) > 3.0 || (currentTotal / 10.0) < -3.0){
                    currentlyStraight = false;
                    Simulation.paused = true;
                    currentTotal = 0;
                    yawValues.clear();
                }
            }
            else{
                if(-3.0 < (currentTotal / 10.0) && (currentTotal / 10.0) < 3.0){
                    currentlyStraight = true;
                    Simulation.paused = true;
                    currentTotal = 0;
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
