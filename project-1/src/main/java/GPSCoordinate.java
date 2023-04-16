/**
 * POJO to store GPS coordinate data
 */
public class GPSCoordinate {
    private double offset;
    private double latitude;
    private double longitude;

    public GPSCoordinate(int o, double x, double y){
        offset = o;
        latitude = x;
        longitude = y;
    }

    void print(){
        System.out.format("%-30s %-30s %-30s%n",
                "Offset: " + offset + " ms", "Latitude: " + latitude, "Longitude: " + longitude);
    }

    public double getOffset() {
        return offset;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
