//TODO add javadoc
public class GPSCoordinate {
    private int offset;
    private double latitude;
    private double longitude;

    GPSCoordinate(int o, double x, double y){
        offset = o;
        latitude = x;
        longitude = y;
    }

    void print(){
        System.out.format("%-30s %-30s %-30s%n",
                "Offset: " + offset + " ms", "Latitude: " + latitude, "Longitude: " + longitude);
    }
}
