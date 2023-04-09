import java.util.Objects;

/**
 * Parent abstract class for different kinds of CANFrame.
 * This holds the common frame information that is used by both single val and tri val frames.
 */
public abstract class CANFrame {
    private long msgNumber;
    private String frameID;
    private double time;

    public CANFrame(long msgNumber, String frameID, double time) {
        this.msgNumber = msgNumber;
        this.frameID = frameID;
        this.time = time;
    }

    public CANFrame() {}

    public long getMsgNumber() {
        return msgNumber;
    }

    public void setMsgNumber(long msgNumber) {
        this.msgNumber = msgNumber;
    }

    public String getFrameID() {
        return frameID;
    }

    public void setFrameID(String frameID) {
        this.frameID = frameID;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CANFrame canFrame)) return false;

        if (msgNumber != canFrame.msgNumber) return false;
        if (Double.compare(canFrame.time, time) != 0) return false;
        return Objects.equals(frameID, canFrame.frameID);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (msgNumber ^ (msgNumber >>> 32));
        result = 31 * result + (frameID != null ? frameID.hashCode() : 0);
        temp = Double.doubleToLongBits(time);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return String.format("%6d | %8s | %7.1f |", msgNumber, frameID, time);
    }
}
