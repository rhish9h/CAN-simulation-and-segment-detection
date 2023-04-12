import java.util.ArrayList;

//TODO add javadoc
public class GPSTrace {
    ArrayList<GPSCoordinate> trace;

    GPSTrace(){
        trace = new ArrayList<GPSCoordinate>();
    }

    public void addCoord(GPSCoordinate g){
        trace.add(g);
    }

    public void print(){
        for(int i = 0; i < trace.size(); i ++){
            trace.get(i).print();
        }
    }

    public ArrayList<GPSCoordinate> getTrace(){
        return trace;
    }
}
