/**
 * POJO to store GPS coordinate data
 */
public class GPSCoordinate {
    private double offset;
    private double latitude;
    private double longitude;

    public GPSCoordinate(double offset, double latitude, double longitude){
        this.offset = offset;
        this.latitude = latitude;
        this.longitude = longitude;
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

    @Override
    public String toString() {
        return "GPSCoordinate{" +
                "offset=" + offset +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
