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
    public static void main(String[] args) throws IOException {
        System.out.println("Please enter file name of CAN Data (Press ENTER to use default file):");
        Scanner sc = new Scanner(System.in);
        String fileName = sc.nextLine();

        if (fileName.trim().equals("")) {
            fileName = "src/main/resources/18 CANmessages.trc";
        }

        System.out.println("Parsing file: " + fileName);
        CANTraceParser canTraceParser = new CANTraceParser();
        CANTrace canTrace = canTraceParser.parseCANTraceFile(fileName);
        System.out.println("Getting next 30 messages and printing them");
        System.out.println("-----------------------------------------------------------------");
        System.out.format("%6s | %8s | %7s | %8s %7s | %s%n",
                "Msg No", "Frame ID", "Time", "Value", "Unit", "Description");
        System.out.println("-----------------------------------------------------------------");
        for (int i = 0; i < 30; i++) {
            System.out.println(canTrace.getNextMessage());
        }

        System.out.println("Resetting next message");
        canTrace.resetNextMessage();

        System.out.println("Getting next 5 messages and printing them");
        for (int i = 0; i < 5; i++) {
            System.out.println(canTrace.getNextMessage());
        }

        //Start of New GPS section

        fileName = "";

        System.out.println("Please enter file name of GPS Data (Press ENTER to use default file):");
        fileName = sc.nextLine();

        if (fileName.trim().equals("")) {
            fileName = "src/main/resources/GPStrace.txt";
        }

        System.out.println("Parsing file: " + fileName);
        GPSParser gpsParser = new GPSParser();
        GPSTrace gpsTrace = gpsParser.parseGPSTraceFile(fileName);
        gpsTrace.print();

        sc.close();
        System.out.println("End of Simulation");
    }
}
