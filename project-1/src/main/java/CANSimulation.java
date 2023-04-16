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
    private final int milliDelay = 0;
    private final int nanoDelay = 100;
    private CANFrame frame;
    private GPSCoordinate coord;

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
            System.out.format("   %20s |   %10s |    %10s |         %10s |         %10s |         %10s |  %30s \n",
                    "Current Time", "Vehicle Speed", "Steer Angle", "Yaw Rate", "Lat Accel", "Long Accel", "GPS Lat/Long");

            frame = canTrace.getNextMessage();
            coord = gpsTrace.getNextMessage();
            startTime = System.nanoTime();

            bothFrameAndCoordPresent();
            onlyFramePresent();
            onlyCoordPresent();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("End of Simulation");
    }

    private void bothFrameAndCoordPresent() throws InterruptedException {
        double curTime;
        double frameTime;
        double coordTime;

        while (frame != null && coord != null) {
            curTime = (System.nanoTime() - startTime) / 1000_000;
            frameTime = frame != null ? frame.getTime() : 0;
            coordTime = coord != null ? coord.getOffset() : 0;

            if (frameTime < coordTime && curTime >= frameTime) {
                if (frame instanceof CANFrameSingleVal) {
                    sendSingleFrameValue();
                } else {
                    sendTriFrameValues();
                }
                frame = canTrace.getNextMessage();
            } else if (curTime >= coordTime) {
                sendGPSValues(coordTime);
                coord = gpsTrace.getNextMessage();
            } else {
                sensorDataReceiver.receiveSensorValues(0, curTime, Identifier.CUR_TIME);
            }

            // This delay is added on purpose to avoid jittery ui output
            Thread.sleep(milliDelay, nanoDelay);
        }
    }

    private void sendSingleFrameValue() {
        CANFrameSingleVal singleFrame = (CANFrameSingleVal) frame;
        sensorDataReceiver.receiveSensorValues(singleFrame.getValue().getValue(),
                singleFrame.getTime(),
                singleFrame.getDescription());
    }

    private void sendTriFrameValues() {
        CANFrameTriVal triFrame = (CANFrameTriVal) frame;
        sensorDataReceiver.receiveSensorValues(triFrame.getValue1().getValue(),
                triFrame.getTime(),
                triFrame.getDescription1());
        sensorDataReceiver.receiveSensorValues(triFrame.getValue2().getValue(),
                triFrame.getTime(),
                triFrame.getDescription2());
        sensorDataReceiver.receiveSensorValues(triFrame.getValue3().getValue(),
                triFrame.getTime(),
                triFrame.getDescription3());
    }

    private void sendGPSValues(double coordTime) {
        sensorDataReceiver.receiveSensorValues(coord.getLatitude(), coordTime, Identifier.GPS_LAT);
        sensorDataReceiver.receiveSensorValues(coord.getLongitude(), coordTime, Identifier.GPS_LON);
    }

    private void onlyFramePresent() throws InterruptedException {
        double curTime;
        double frameTime;

        while (frame != null) {
            curTime = (System.nanoTime() - startTime) / 1000_000;
            frameTime = frame != null ? frame.getTime() : 0;

            if (curTime >= frameTime) {
                if (frame instanceof CANFrameSingleVal) {
                    sendSingleFrameValue();
                } else {
                    sendTriFrameValues();
                }
                frame = canTrace.getNextMessage();
            } else {
                sensorDataReceiver.receiveSensorValues(0, curTime, Identifier.CUR_TIME);
            }

            // This delay is added on purpose to avoid jittery ui output
            Thread.sleep(milliDelay, nanoDelay);
        }
    }

    private void onlyCoordPresent() throws InterruptedException {
        double curTime;
        double coordTime;

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
            Thread.sleep(milliDelay, nanoDelay);
        }
    }

    public static void main(String[] args) {
        CANSimulation canSimulation = new CANSimulation();
        canSimulation.startSimulation();
    }
}
