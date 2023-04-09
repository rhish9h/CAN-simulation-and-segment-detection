import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CANTraceParserTest {

    @Test
    void parseCANTraceFile() throws IOException {
        CANTraceParser parser = new CANTraceParser();
        CANTrace canTrace = parser.parseCANTraceFile("src/test/resources/CANMessagesTest");
        CANTrace targetTrace = new CANTrace();
        targetTrace.addCANFrame(new CANFrameSingleVal(4,
                "0018",
                4.0,
                new FrameVal(2.0, "deg"),
                "Steering wheel angle"));
        targetTrace.addCANFrame(new CANFrameTriVal(16,
                "0B41",
                6.8,
                new FrameVal(-0.88, "deg/sec"),
                new FrameVal(0.72, "m/sec^2"),
                new FrameVal(-0.24, "m/sec^2"),
                "Vehicle yaw rate",
                "Vehicle longitudinal acceleration",
                "Vehicle lateral acceleration"));
        targetTrace.addCANFrame(new CANFrameSingleVal(131,
                "0F7A",
                66.9,
                new FrameVal(72.7, "km/h"),
                "Displayed vehicle speed"));
        assertEquals(targetTrace, canTrace);
    }
}