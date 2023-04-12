import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GPSParser {
    private int index = 0;

    public GPSTrace parseGPSTraceFile(String gpsFile) throws IOException{
        GPSTrace gpsTrace = new GPSTrace();

        try (BufferedReader read = new BufferedReader(new FileReader(gpsFile))) {
            String line;

            while ((line = read.readLine()) != null) {
                line = line.substring(0, line.length() - 1);

                String[] split = line.split(", ");
                double latitude = Double.parseDouble(split[0]);
                double longitude = Double.parseDouble(split[1]);

                GPSCoordinate newCoord = new GPSCoordinate(index * 1000, latitude, longitude);

                gpsTrace.addCoord(newCoord);

                index ++;
            }
        }
        catch(FileNotFoundException e){
            System.out.println("The gps file was not found!");
        }

        return gpsTrace;
    }
}
