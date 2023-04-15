public class SensorDataReceiver {
    private String curTime = "-";
    private String vehSpeed = "-";
    private String strAngle = "-";
    private String yawRate = "-";
    private String latAccel = "-";
    private String lonAccel = "-";
    private String gpsLat = "-";
    private String gpsLon = "-";

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

    private void doNothing() {}

    private void printData() {
        System.out.format("%s ms | %s km/h | %s deg | %s deg/sec | %s m/sec^s | %s m/sec^s | %s %s \r",
                curTime, vehSpeed, strAngle, yawRate, latAccel, lonAccel, gpsLat, gpsLon);
    }
}
