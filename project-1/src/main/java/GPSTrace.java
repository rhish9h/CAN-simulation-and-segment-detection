import java.util.ArrayList;
import java.util.List;

/**
 * Class consists of a collection of GPSCoordinates, also has APIs to interact with them.
 */
public class GPSTrace {
    private List<GPSCoordinate> trace;
    private int curIdx = 0;

    public GPSTrace(){
        trace = new ArrayList<>();
    }

    /**
     * Add coordinate to the collection of GPS coordinates
     * @param g coordinate to be added
     */
    public void addCoord(GPSCoordinate g){
        trace.add(g);
    }

    /**
     * Print all the GPS coordinates from the collection
     */
    public void print(){
        for(int i = 0; i < trace.size(); i ++){
            trace.get(i).print();
        }
    }

    public List<GPSCoordinate> getTrace(){
        return trace;
    }

    /**
     * Get the next GPScoordinate in the collection if present, else fetch null
     * @return GPSCoordinate object
     */
    public GPSCoordinate getNextMessage() {
        if (curIdx < trace.size()) {
            return trace.get(curIdx++);
        }
        return null;
    }
}
