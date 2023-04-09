import java.util.Objects;

/**
 * CANFrame that holds data for a single Frame Value.
 */
public class CANFrameSingleVal extends CANFrame {
    private FrameVal value;
    private String description;

    public CANFrameSingleVal() {
        super();
    }

    public CANFrameSingleVal(long msgNumber, String frameID, double time, FrameVal value, String description) {
        super(msgNumber, frameID, time);
        this.value = value;
        this.description = description;
    }

    public FrameVal getValue() {
        return value;
    }

    public void setValue(FrameVal value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" %10s | %s", value, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CANFrameSingleVal singleVal)) return false;
        if (!super.equals(o)) return false;

        if (!Objects.equals(value, singleVal.value)) return false;
        return Objects.equals(description, singleVal.description);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
