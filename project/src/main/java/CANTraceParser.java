import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Parser that reads CAN bus data from given file and parses into CAN Trace
 */
public class CANTraceParser {
    private int parseFail = 0;
    private int parseSuccess = 0;

    /**
     * Public api used to parse given file and fetch the generated CAN Trace
     * @param canTraceFile file that needs to be read
     * @return CANTrace object
     * @throws IOException when file cannot be read
     */
    public CANTrace parseCANTraceFile(String canTraceFile) throws IOException {
        CANTrace canTrace = new CANTrace();

        // Code to read file referred from Baeldung and modified according to my need
        // https://www.baeldung.com/reading-file-in-java
        try (BufferedReader br = new BufferedReader(new FileReader(canTraceFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith(";")) {
                    continue;
                }

                CANFrame frame = parseLineIntoCANFrame(line);

                if (frame != null) {
                    canTrace.addCANFrame(frame);
                    parseSuccess++;
                } else {
                    parseFail++;
                }
            }
        }

        System.out.println("Parsed " + parseSuccess + " lines successfully. (IDs - 0018, 0F7A, 0B41)");
        if (parseFail > 0) {
            System.out.println("Failed to parse / Skipped " + parseFail + " lines.");
        }

        canTrace.sortByTime();

        return canTrace;
    }

    /**
     * Parse individual line from the file into a single CANFrame
     * @param line to be parsed
     * @return CANFrame that is generated
     */
    private CANFrame parseLineIntoCANFrame(String line) {
        try {
            String[] split = line.split("\\s+");
            long msgNumber = Long.parseLong(split[1].replace(")", ""));
            double time = Double.parseDouble(split[2]);
            String frameID = split[4];

            if (!List.of("0018", "0F7A", "0B41").contains(frameID)) {
                return null;
            }

            if (frameID.equalsIgnoreCase("0B41")) {
                return parseToCANFrameTriVal(frameID, split, msgNumber, time);
            } else {
                return parseToCANFrameSingleVal(frameID, split, msgNumber, time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Create a Tri Val Can Frame from the line data
     * @param frameID identifier of frame type
     * @param split tokenized array of strings from the line
     * @param msgNumber as it was generated in the dump
     * @param time at which it was generated
     * @return CANFrameTriVal object
     */
    private CANFrameTriVal parseToCANFrameTriVal(String frameID, String[] split, long msgNumber, double time) {
        Map<String, List<SensorInfo>> infoMap = CANFramesInfo.getInfoMap();
        CANFrameTriVal canFrame = new CANFrameTriVal();
        List<SensorInfo> sensorInfoList = infoMap.get(frameID);

        if (sensorInfoList.size() != 3) {
            return null;
        }

        double value1 = getFrameValue(split, sensorInfoList.get(0));
        canFrame.setValue1(new FrameVal(value1, sensorInfoList.get(0).getStepSizeWithUnit().getUnit()));

        double value2 = getFrameValue(split, sensorInfoList.get(1));
        canFrame.setValue2(new FrameVal(value2, sensorInfoList.get(1).getStepSizeWithUnit().getUnit()));

        double value3 = getFrameValue(split, sensorInfoList.get(2));
        canFrame.setValue3(new FrameVal(value3, sensorInfoList.get(2).getStepSizeWithUnit().getUnit()));

        canFrame.setDescription1(sensorInfoList.get(0).getDescription());
        canFrame.setDescription2(sensorInfoList.get(1).getDescription());
        canFrame.setDescription3(sensorInfoList.get(2).getDescription());

        canFrame.setMsgNumber(msgNumber);
        canFrame.setFrameID(frameID);
        canFrame.setTime(time);

        return canFrame;
    }

    /**
     * Create a Single Val Can Frame from the line data
     * @param frameID identifier of frame type
     * @param split tokenized array of strings from the line
     * @param msgNumber as it was generated in the dump
     * @param time at which it was generated
     * @return CANFrameSingleVal object
     */
    private CANFrameSingleVal parseToCANFrameSingleVal(String frameID, String[] split, long msgNumber, double time) {
        Map<String, List<SensorInfo>> infoMap = CANFramesInfo.getInfoMap();
        CANFrameSingleVal canFrame = new CANFrameSingleVal();
        SensorInfo sensorInfo = infoMap.get(frameID).get(0);
        String description = sensorInfo.getDescription();
        double value = getFrameValue(split, sensorInfo);

        canFrame.setMsgNumber(msgNumber);
        canFrame.setFrameID(frameID);
        canFrame.setTime(time);
        canFrame.setDescription(description);
        canFrame.setValue(new FrameVal(value, sensorInfo.getStepSizeWithUnit().getUnit()));
        return canFrame;
    }

    /**
     * Specifically get the frame value from the line
     *
     * Math rounding trick found on Stack Overflow
     * https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
     *
     * @param split tokenized array of strings from the line
     * @param sensorInfo information used to parse the data for that particular sensor
     * @return double value
     */
    private double getFrameValue(String[] split, SensorInfo sensorInfo) {
        long rawValue = getRawValue(split, sensorInfo.getLocation());
        double value = rawValue * sensorInfo.getStepSizeWithUnit().getValue()
                + sensorInfo.getRange().getFrom();
        value *= 100;
        value = Math.round(value);
        return value / 100;
    }

    /**
     * Code to convert from hex to bin
     *
     * referred from Stack Overflow and modified according to my need
     * https://stackoverflow.com/questions/9246326/convert-hexadecimal-string-hex-to-a-binary-string
     *
     * @param s hex string that needs to be converted
     * @return String binary
     */
    private String hexToBin(String s) {
        String binIncomplete = new BigInteger(s, 16).toString(2);
        int bLen = binIncomplete.length();
        return "0".repeat(Math.max(0, 8 - bLen)) + binIncomplete;
    }

    /**
     * Raw value in the intermediate step before refining it
     *
     * Start from the end, convert to binary, reverse the binary and add to rawBinary,
     * take substring of necessary location, reverse it, convert to decimal
     *
     * @param split tokenized array of strings from the line
     * @param location of the values in the hex data
     * @return long raw value
     */
    private long getRawValue(String[] split, DataFieldLocation location) {
        StringBuilder rawBinary = new StringBuilder();
        int sLen = split.length;

        for (int i = sLen - 1; i >= 6; i--) {
            rawBinary.append(new StringBuilder(hexToBin(split[i])).reverse());
        }

        int fromIdx = location.getFromByte() * 8 + location.getFromBit();
        int toIdx = location.getToByte() * 8 + location.getToBit();

        return Long.parseLong(new StringBuilder(rawBinary.substring(fromIdx, toIdx + 1))
                .reverse().toString(), 2);
    }
}
