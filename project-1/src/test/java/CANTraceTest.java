import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CANTraceTest {

    @Test
    void getNextMessage() {
        List<CANFrame> frames = new ArrayList<>();
        CANFrameSingleVal singleVal1 = new CANFrameSingleVal(1, "frameId", 10.3,
                new FrameVal(1.0, "m/s"), "description");
        CANFrameSingleVal singleVal2 = new CANFrameSingleVal(2, "frameId", 10.2,
                new FrameVal(1.0, "m/s"), "description");
        CANFrameSingleVal target1 = new CANFrameSingleVal(1, "frameId", 10.3,
                new FrameVal(1.0, "m/s"), "description");
        CANFrameSingleVal target2 = new CANFrameSingleVal(2, "frameId", 10.2,
                new FrameVal(1.0, "m/s"), "description");
        frames.add(singleVal1);
        frames.add(singleVal2);
        CANTrace canTrace = new CANTrace(frames);
        assertEquals(target1, canTrace.getNextMessage());
        assertEquals(target2, canTrace.getNextMessage());
    }

    @Test
    void resetNextMessage() {
        List<CANFrame> frames = new ArrayList<>();
        CANFrameSingleVal singleVal1 = new CANFrameSingleVal(1, "frameId", 10.3,
                new FrameVal(1.0, "m/s"), "description");
        CANFrameSingleVal singleVal2 = new CANFrameSingleVal(2, "frameId", 10.2,
                new FrameVal(1.0, "m/s"), "description");
        CANFrameSingleVal target = new CANFrameSingleVal(1, "frameId", 10.3,
                new FrameVal(1.0, "m/s"), "description");
        frames.add(singleVal1);
        frames.add(singleVal2);
        CANTrace canTrace = new CANTrace(frames);
        canTrace.getNextMessage();
        canTrace.getNextMessage();
        canTrace.resetNextMessage();
        assertEquals(target, canTrace.getNextMessage());
    }

    @Test
    void addCANFrame() {
        CANTrace canTrace = new CANTrace();
        CANFrameSingleVal singleVal = new CANFrameSingleVal(1, "frameId", 10.3,
                new FrameVal(1.0, "m/s"), "description");
        CANFrameSingleVal target = new CANFrameSingleVal(1, "frameId", 10.3,
                new FrameVal(1.0, "m/s"), "description");
        canTrace.addCANFrame(singleVal);
        assertEquals(target, canTrace.getNextMessage());
    }

    @Test
    void sortByTime() {
        List<CANFrame> frames = new ArrayList<>();
        CANFrameSingleVal singleVal1 = new CANFrameSingleVal(1, "frameId", 10.1,
                new FrameVal(1.0, "m/s"), "description");
        CANFrameSingleVal singleVal2 = new CANFrameSingleVal(2, "frameId", 10.2,
                new FrameVal(1.0, "m/s"), "description");
        CANFrameSingleVal singleVal3 = new CANFrameSingleVal(2, "frameId", 0.1,
                new FrameVal(1.0, "m/s"), "description");
        CANFrameSingleVal target1 = new CANFrameSingleVal(2, "frameId", 0.1,
                new FrameVal(1.0, "m/s"), "description");
        CANFrameSingleVal target2 = new CANFrameSingleVal(1, "frameId", 10.1,
                new FrameVal(1.0, "m/s"), "description");
        CANFrameSingleVal target3 = new CANFrameSingleVal(2, "frameId", 10.2,
                new FrameVal(1.0, "m/s"), "description");
        frames.add(singleVal1);
        frames.add(singleVal2);
        frames.add(singleVal3);
        List<CANFrame> target = new ArrayList<>();
        target.add(target1);
        target.add(target2);
        target.add(target3);
        CANTrace canTrace = new CANTrace(frames);
        canTrace.sortByTime();
        CANTrace targetTrace = new CANTrace(target);
        assertEquals(targetTrace, canTrace);
    }
}