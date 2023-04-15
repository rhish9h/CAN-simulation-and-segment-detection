import java.io.IOException;
import java.util.Scanner;

//TODO update this javadoc and check if any other javadoc needs update
/**
 * Holds the main method used to perform the simulation.
 * Simulation includes -
 * 1. parsing the given file
 * 2. getting next 30 messages and printing them
 * 3. resetting next message
 * 4. getting next 5 messages and printing them
 */
public class CANSimulation {
    private CANTrace canTrace;
    private GPSTrace gpsTrace;
    private Scanner sc;
    private double startTime;
    private SensorDataReceiver sensorDataReceiver;

    public CANSimulation() {
        sc = new Scanner(System.in);
        sensorDataReceiver = new SensorDataReceiver();
    }

    public void parseCANData() throws IOException {
        System.out.println("Please enter file name of CAN Data (Press ENTER to use default file):");
        String fileName = sc.nextLine();

        if (fileName.trim().equals("")) {
            fileName = "src/main/resources/18 CANmessages.trc";
        }

        System.out.println("Parsing file: " + fileName);
        CANTraceParser canTraceParser = new CANTraceParser();
        canTrace = canTraceParser.parseCANTraceFile(fileName);
    }

    public void parseGPSData() throws IOException {
        System.out.println("Please enter file name of GPS Data (Press ENTER to use default file):");
        String fileName = sc.nextLine();

        if (fileName.trim().equals("")) {
            fileName = "src/main/resources/GPStrace.txt";
        }

        System.out.println("Parsing file: " + fileName);
        GPSParser gpsParser = new GPSParser();
        gpsTrace = gpsParser.parseGPSTraceFile(fileName);
    }

    public void startSimulation() {
        System.out.println("Start of Simulation");

        try {
            parseCANData();
            parseGPSData();

            System.out.println("Press enter to start simulation");
            sc.nextLine();

            CANFrame frame = canTrace.getNextMessage();
            GPSCoordinate coord = gpsTrace.getNextMessage();

            double frameTime;
            double coordTime;
            startTime = System.nanoTime();

            double curTime;

            while (frame != null && coord != null) {
                curTime = (System.nanoTime() - startTime) / 1000_000;
                frameTime = frame != null ? frame.getTime() : 0;
                coordTime = coord != null ? coord.getOffset() : 0;

                if (frameTime < coordTime && curTime >= frameTime) {
                    frame = canTrace.getNextMessage();
                } else if (curTime >= coordTime) {
                    sensorDataReceiver.receiveSensorValues(coord.getLatitude(), coordTime, Identifier.GPS_LAT);
                    sensorDataReceiver.receiveSensorValues(coord.getLongitude(), coordTime, Identifier.GPS_LON);
                    coord = gpsTrace.getNextMessage();
                } else {
                    sensorDataReceiver.receiveSensorValues(0, curTime, Identifier.CUR_TIME);
                }

                // This delay is added on purpose to avoid jittery ui output
                Thread.sleep(0, 200);
            }

            while (frame != null) {
                curTime = (System.nanoTime() - startTime) / 1000_000;
                frameTime = frame != null ? frame.getTime() : 0;

                if (curTime >= frameTime) {
                    frame = canTrace.getNextMessage();
                } else {
                    sensorDataReceiver.receiveSensorValues(0, curTime, Identifier.CUR_TIME);
                }

                // This delay is added on purpose to avoid jittery ui output
                Thread.sleep(0, 200);
            }

            while (coord != null) {
                curTime = (System.nanoTime() - startTime) / 1000_000;
                coordTime = coord != null ? coord.getOffset() : 0;

                if (curTime >= coordTime) {
                    sensorDataReceiver.receiveSensorValues(coord.getLatitude(), coordTime, Identifier.GPS_LAT);
                    sensorDataReceiver.receiveSensorValues(coord.getLongitude(), coordTime, Identifier.GPS_LON);
                    coord = gpsTrace.getNextMessage();
                } else {
                    sensorDataReceiver.receiveSensorValues(0, curTime, Identifier.CUR_TIME);
                }

                // This delay is added on purpose to avoid jittery ui output
                Thread.sleep(0, 200);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("End of Simulation");
    }

    public static void main(String[] args) {
        CANSimulation canSimulation = new CANSimulation();
        canSimulation.startSimulation();
    }
}
