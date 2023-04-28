/**
 * POJO used to hold all of the sensor data in one place
 */
public class SensorData {
    private double curTime;
    private double vehSpeed;
    private double strAngle;
    private double yawRate;
    private double latAccel;
    private double lonAccel;
    private double gpsLat;
    private double gpsLon;

    public SensorData(double curTime, double vehSpeed, double strAngle, double yawRate, double latAccel,
                      double lonAccel, double gpsLat, double gpsLon) {
        this.curTime = curTime;
        this.vehSpeed = vehSpeed;
        this.strAngle = strAngle;
        this.yawRate = yawRate;
        this.latAccel = latAccel;
        this.lonAccel = lonAccel;
        this.gpsLat = gpsLat;
        this.gpsLon = gpsLon;
    }

    public double getCurTime() {
        return curTime;
    }

    public double getVehSpeed() {
        return vehSpeed;
    }

    public double getStrAngle() {
        return strAngle;
    }

    public double getYawRate() {
        return yawRate;
    }

    public double getLatAccel() {
        return latAccel;
    }

    public double getLonAccel() {
        return lonAccel;
    }

    public double getGpsLat() {
        return gpsLat;
    }

    public double getGpsLon() {
        return gpsLon;
    }
}
