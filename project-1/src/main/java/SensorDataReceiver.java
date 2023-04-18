/**
 * Receiver of sensor data, prints data in the right format once received
 */
public class SensorDataReceiver {
    private String curTime = "-";
    private String vehSpeed = "-";
    private String strAngle = "-";
    private String yawRate = "-";
    private String latAccel = "-";
    private String lonAccel = "-";
    private String gpsLat = "-";
    private String gpsLon = "-";

    /**
     * Public API to receive sensor values and print them in the right format
     * @param sensorValue magnitude/value of the sensor reading
     * @param offset time offset at which it was captured
     * @param identifier description to identify which sensor's data it is
     */
    public void receiveSensorValues(double sensorValue, double offset, String identifier) {
        curTime = String.valueOf(offset);

        switch (identifier) {
            case Identifier.CUR_TIME -> doNothing();
            case Identifier.VEH_SPEED -> vehSpeed = String.valueOf(sensorValue);
            case Identifier.STR_ANGLE -> strAngle = String.valueOf(sensorValue);
            case Identifier.YAW_RATE -> yawRate = String.valueOf(sensorValue);
            case Identifier.LAT_ACCEL -> latAccel = String.valueOf(sensorValue);
            case Identifier.LON_ACCEL -> lonAccel = String.valueOf(sensorValue);
            case Identifier.GPS_LAT -> gpsLat = String.valueOf(sensorValue);
            case Identifier.GPS_LON -> gpsLon = String.valueOf(sensorValue);
        }

        printData();
    }

    /**
     * Used when only time data is sent, so no sensor data other than time is updated
     */
    private void doNothing() {}

    /**
     * Print function to display all sensor values in the correct format
     */
    private void printData() {
        System.out.print(String.format("%20s ms | %10s km/h | %10s deg | %10s deg/sec | %10s m/sec^s | %10s m/sec^s | %15s %15s \r",
                curTime, vehSpeed, strAngle, yawRate, latAccel, lonAccel, gpsLat, gpsLon));
    }
}
