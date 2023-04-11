public class GPSCoordinate {
    private int offset;
    private double xCoord;
    private double yCoord;

    GPSCoordinate(int o, double x, double y){
        offset = o;
        xCoord = x;
        yCoord = y;
    }

    void print(){
        System.out.println("Offset: " + offset + " ms" + "      Longitude: " + xCoord + "       Latitude: " + yCoord);
    }
}
