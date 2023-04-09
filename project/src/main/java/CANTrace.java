import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class consists of a collection of CANFrames, also has APIs to interact with them.
 */
public class CANTrace {
    private List<CANFrame> frames;
    private int curIdx = 0;

    public CANTrace() {
        this.frames = new ArrayList<>();
    }

    public CANTrace(List<CANFrame> frames) {
        this.frames = frames;
    }

    /**
     * Display all the frames in the CAN Trace in a pretty table.
     */
    public void printTrace() {
        System.out.println("-----------------------------------------------------------------");
        System.out.format("%6s | %8s | %7s | %8s %7s | %s%n",
                "Msg No", "Frame ID", "Time", "Value", "Unit", "Description");
        System.out.println("-----------------------------------------------------------------");

        for (CANFrame frame : frames) {
            System.out.println(frame.toString());
        }
    }

    /**
     * Fetch the next message as it is being iterated.
     * @return CANFrame next message
     */
    public CANFrame getNextMessage() {
        if (curIdx < frames.size()) {
            return frames.get(curIdx++);
        }
        return null;
    }

    /**
     * Reset the iterator to point to the first element in the collection
     */
    public void resetNextMessage() {
        curIdx = 0;
    }

    /**
     * Add given frame to the CAN Frame collection
     * @param frame that needs to be added
     */
    public void addCANFrame(CANFrame frame) {
        frames.add(frame);
    }

    /**
     * Sort the collection of CANFrames in ascending order of time
     */
    public void sortByTime() {
        frames.sort((a, b) -> {
            double timeDiff = a.getTime() - b.getTime();
            if (timeDiff > 0) {
                return 1;
            } else if (timeDiff < 0) {
                return -1;
            } else {
                return 0;
            }
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CANTrace canTrace)) return false;

        if (curIdx != canTrace.curIdx) return false;
        return Objects.equals(frames, canTrace.frames);
    }

    @Override
    public int hashCode() {
        int result = frames != null ? frames.hashCode() : 0;
        result = 31 * result + curIdx;
        return result;
    }
}
