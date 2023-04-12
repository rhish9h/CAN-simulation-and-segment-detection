import java.util.ArrayList;
import java.util.List;

//TODO add javadoc for class and every function
public class GPSTrace {
    private List<GPSCoordinate> trace;
    private int curIdx = 0;

    public GPSTrace(){
        trace = new ArrayList<>();
    }

    public void addCoord(GPSCoordinate g){
        trace.add(g);
    }

    public void print(){
        for(int i = 0; i < trace.size(); i ++){
            trace.get(i).print();
        }
    }

    public List<GPSCoordinate> getTrace(){
        return trace;
    }

    public GPSCoordinate getNextMessage() {
        if (curIdx < trace.size()) {
            return trace.get(curIdx++);
        }
        return null;
    }
}
