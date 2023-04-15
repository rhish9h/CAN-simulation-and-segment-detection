public class SensorDataReceiver {
    private String curTime = "-";
    private String vehSpeed = "-";
    private String strAngle = "-";
    private String yawRate = "-";
    private String latAccel = "-";
    private String lonAccel = "-";
    private String gpsLat = "-";
    private String gpsLon = "-";

    public void receiveSensorValues(double sensorValue, double offset, Identifier identifier) {
        curTime = String.valueOf(offset);

        switch (identifier) {
            case CUR_TIME -> doNothing();
            case VEH_SPEED -> vehSpeed = String.valueOf(sensorValue);
            case STR_ANGLE -> strAngle = String.valueOf(sensorValue);
            case YAW_RATE -> yawRate = String.valueOf(sensorValue);
            case LAT_ACCEL -> latAccel = String.valueOf(sensorValue);
            case LON_ACCEL -> lonAccel = String.valueOf(sensorValue);
            case GPS_LAT -> gpsLat = String.valueOf(sensorValue);
            case GPS_LON -> gpsLon = String.valueOf(sensorValue);
        }

        printData();
    }

    private void doNothing() {}

    private void printData() {
        System.out.format("%s ms | %s km/h | %s deg | %s deg/sec | %s m/sec^s | %s m/sec^s | %s %s \r",
                curTime, vehSpeed, strAngle, yawRate, latAccel, lonAccel, gpsLat, gpsLon);
    }
}
