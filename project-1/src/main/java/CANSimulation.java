import java.io.IOException;
import java.util.Scanner;

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

    public CANSimulation() {
        sc = new Scanner(System.in);
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
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("End of Simulation");
    }

    public static void main(String[] args) {
        CANSimulation canSimulation = new CANSimulation();
        canSimulation.startSimulation();
    }
}
