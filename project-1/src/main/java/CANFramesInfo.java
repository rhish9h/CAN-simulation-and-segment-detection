import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Holds all the information required to parse the CAN Frames.
 */
public class CANFramesInfo {
    private static final Map<String, List<SensorInfo>> infoMap = new HashMap<>();

    static {
        infoMap.put("0018",
                List.of(new SensorInfo(
                        new DataFieldLocation(6, 0, 7, 5),
                        14,
                        Identifier.STR_ANGLE,
                        16383,
                        new Range(-2048, 2047),
                        new FrameVal(0.5, "deg")
                )));
        infoMap.put("0F7A",
                List.of(new SensorInfo(
                        new DataFieldLocation(6, 0, 7, 3),
                        12,
                        Identifier.VEH_SPEED,
                        4095,
                        new Range(0, 409.4),
                        new FrameVal(0.1, "km/h")
                )));
        infoMap.put("0B41",
                List.of(new SensorInfo(
                        new DataFieldLocation(6, 0, 7, 7),
                        16,
                        Identifier.YAW_RATE,
                        65535,
                        new Range(-327.68, 327.66),
                        new FrameVal(0.01, "deg/sec")
                ), new SensorInfo(
                        new DataFieldLocation(3, 0, 3, 7),
                        8,
                        Identifier.LON_ACCEL,
                        255,
                        new Range(-10.24, 10.08),
                        new FrameVal(0.08, "m/sec^2")
                ), new SensorInfo(
                        new DataFieldLocation(2, 0, 2, 7),
                        8,
                        Identifier.LAT_ACCEL,
                        255,
                        new Range(-10.24, 10.08),
                        new FrameVal(0.08, "m/sec^2")
                )));
    }

    public static Map<String, List<SensorInfo>> getInfoMap() {
        return infoMap;
    }
}
