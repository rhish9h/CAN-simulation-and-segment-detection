/**
 * POJO used to store sensor info/description that is used for parsing CAN bus data
 */
public class SensorInfo {
    private final DataFieldLocation location;
    private final int fieldBitSize;
    private final String description;
    private final long maxValue;
    private final Range range;
    private final FrameVal stepSizeWithUnit;

    public SensorInfo(DataFieldLocation location, int fieldBitSize, String description, long maxValue,
                      Range range, FrameVal stepSizeWithUnit) {
        this.location = location;
        this.fieldBitSize = fieldBitSize;
        this.description = description;
        this.maxValue = maxValue;
        this.range = range;
        this.stepSizeWithUnit = stepSizeWithUnit;
    }

    public DataFieldLocation getLocation() {
        return location;
    }

    public int getFieldBitSize() {
        return fieldBitSize;
    }

    public String getDescription() {
        return description;
    }

    public long getMaxValue() {
        return maxValue;
    }

    public Range getRange() {
        return range;
    }

    public FrameVal getStepSizeWithUnit() {
        return stepSizeWithUnit;
    }
}
