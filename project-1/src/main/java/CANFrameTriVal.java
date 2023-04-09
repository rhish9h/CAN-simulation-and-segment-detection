import java.util.Objects;

/**
 * CANFrame that holds data for a single Frame Value.
 */
public class CANFrameTriVal extends CANFrame {
    private FrameVal value1;
    private FrameVal value2;
    private FrameVal value3;
    private String description1;
    private String description2;
    private String description3;

    public CANFrameTriVal() {
    }

    public CANFrameTriVal(long msgNumber, String frameID, double time, FrameVal value1,
                          FrameVal value2, FrameVal value3, String description1, String description2,
                          String description3) {
        super(msgNumber, frameID, time);
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.description1 = description1;
        this.description2 = description2;
        this.description3 = description3;
    }

    public FrameVal getValue1() {
        return value1;
    }

    public void setValue1(FrameVal value1) {
        this.value1 = value1;
    }

    public FrameVal getValue2() {
        return value2;
    }

    public void setValue2(FrameVal value2) {
        this.value2 = value2;
    }

    public FrameVal getValue3() {
        return value3;
    }

    public void setValue3(FrameVal value3) {
        this.value3 = value3;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getDescription3() {
        return description3;
    }

    public void setDescription3(String description3) {
        this.description3 = description3;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" %10s | %s %n", value1, description1)
                + String.format("%29s %10s | %s %n", "", value2, description2)
                + String.format("%29s %10s | %s", "", value3, description3);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CANFrameTriVal that)) return false;
        if (!super.equals(o)) return false;

        if (!Objects.equals(value1, that.value1)) return false;
        if (!Objects.equals(value2, that.value2)) return false;
        if (!Objects.equals(value3, that.value3)) return false;
        if (!Objects.equals(description1, that.description1)) return false;
        if (!Objects.equals(description2, that.description2)) return false;
        return Objects.equals(description3, that.description3);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (value1 != null ? value1.hashCode() : 0);
        result = 31 * result + (value2 != null ? value2.hashCode() : 0);
        result = 31 * result + (value3 != null ? value3.hashCode() : 0);
        result = 31 * result + (description1 != null ? description1.hashCode() : 0);
        result = 31 * result + (description2 != null ? description2.hashCode() : 0);
        result = 31 * result + (description3 != null ? description3.hashCode() : 0);
        return result;
    }
}
