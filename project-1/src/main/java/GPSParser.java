import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Parser that reads GPS data from given file and parses into GPS Trace
 */
public class GPSParser {
    private int index = 0;

    /**
     * Public api used to parse given file and fetch the generated GPS Trace
     * @param gpsFile file that needs to be read
     * @return GPSTrace object
     * @throws IOException when file cannot be read
     */
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

            System.out.println("Parsed GPS data.\n");
        }
        catch(FileNotFoundException e){
            throw new IOException("The gps file was not found!", e);
        }

        return gpsTrace;
    }
}
