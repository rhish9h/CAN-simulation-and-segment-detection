import java.util.Objects;

/**
 * POJO to store frame's value and unit data
 */
public class FrameVal {
    private double value;
    private String unit;

    public FrameVal() {
    }

    public FrameVal(double value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FrameVal frameVal)) return false;

        if (Double.compare(frameVal.value, value) != 0) return false;
        return Objects.equals(unit, frameVal.unit);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(value);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("%8.2f %7s", value, unit);
    }
}
