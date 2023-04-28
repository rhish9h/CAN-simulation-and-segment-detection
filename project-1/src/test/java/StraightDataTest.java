import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StraightDataTest {
    private final String heading = String.format(" %8s | %19s | %19s | %11s | %13s | %13s | %10s | %10s | %10s | %10s |" +
                    " %10s | %10s",
            "Seg Type", "GPS Start Lat/Lon", "GPS End Lat/Lon", "Avg Veh Spd", "Max Accel", "Min Accel",
            "Max Veh Spd", "Min Veh Spd", "Len of str", "Curve dir", "Deg of curve", "Max str angle");

    @Test
    void testToString() {
        System.out.println(heading);
        StraightData straightData = new StraightData(new GPSCoordinate(0, 52.721103, 13.223500),
                new GPSCoordinate(0, 52.722007, 13.227160), 77.80, -0.08,
                0.00, 78.80, 0.00, 0.00);
        System.out.println(straightData);
        System.out.println();
    }

    @Test
    void testToStringPositive() {
        System.out.println(heading);
        StraightData straightData = new StraightData(new GPSCoordinate(0, 52.721103, 13.223500),
                new GPSCoordinate(0, 52.722007, 13.227160), 77.80, 0.08,
                0.00, 78.80, 0.00, 0.00);
        System.out.println(straightData);
        System.out.println();
    }
}