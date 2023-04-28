import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SensorDataReceiverTest {

    @Test
    void receiveSensorValues() {
        SensorDataReceiver sensorDataReceiver = new SensorDataReceiver(new SegmentDetector());
        sensorDataReceiver.receiveSensorValues(1231.121, 322342.223, Identifier.GPS_LAT);
    }
}