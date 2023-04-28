import java.util.Observable;

/**
 * Receiver of sensor data, prints data in the right format once received
 */
public class SensorDataReceiver extends Observable {
    private double curTime = 0.0;
    private double vehSpeed = 0.0;
    private double strAngle = 0.0;
    private double yawRate = 0.0;
    private double latAccel = 0.0;
    private double lonAccel = 0.0;
    private double gpsLat = 0.0;
    private double gpsLon = 0.0;
    private String segment;
    private SegmentDetector detector;

    public SensorDataReceiver(SegmentDetector detector) {
        this.detector = detector;
    }
    
    /**
     * Public API to receive sensor values and print them in the right format
     * @param sensorValue magnitude/value of the sensor reading
     * @param offset time offset at which it was captured
     * @param identifier description to identify which sensor's data it is
     */
    public void receiveSensorValues(double sensorValue, double offset, String identifier) {
        curTime = offset;

        switch (identifier) {
            case Identifier.CUR_TIME -> doNothing();
            case Identifier.VEH_SPEED -> vehSpeed = sensorValue;
            case Identifier.STR_ANGLE -> strAngle = sensorValue;
            case Identifier.YAW_RATE -> yawRate = sensorValue;
            case Identifier.LAT_ACCEL -> latAccel = sensorValue;
            case Identifier.LON_ACCEL -> lonAccel = sensorValue;
            case Identifier.GPS_LAT -> gpsLat = sensorValue;
            case Identifier.GPS_LON -> gpsLon = sensorValue;
        }

        if (yawRate != 0 && latAccel != 0 && gpsLat != 0 && gpsLon != 0) {
            segment = detector.parse(new SensorData(curTime, vehSpeed, strAngle, yawRate,
                latAccel, lonAccel, gpsLat, gpsLon));
        }
        setChanged();
        notifyObservers(new SimulationDTO(getFormattedSensorData(), segment));
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
        System.out.print(getFormattedSensorData());
    }

    private String getFormattedSensorData() {
        return String.format("%28.2f ms | %10.2f km/h | %10.2f deg | %10.2f deg/sec | %10.2f m/sec^s | %10.2f m/sec^s | " +
                        "%15.6f %15.6f \r",
                curTime, vehSpeed, strAngle, yawRate, latAccel, lonAccel, gpsLat, gpsLon);
    }
}
