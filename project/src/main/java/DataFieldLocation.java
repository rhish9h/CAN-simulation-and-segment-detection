/**
 * POJO to store location of value in hex data
 */
public class DataFieldLocation {
    private final int fromByte;
    private final int fromBit;
    private final int toByte;
    private final int toBit;

    public DataFieldLocation(int fromByte, int fromBit, int toByte, int toBit) {
        this.fromByte = fromByte;
        this.fromBit = fromBit;
        this.toByte = toByte;
        this.toBit = toBit;
    }

    public int getFromByte() {
        return fromByte;
    }

    public int getFromBit() {
        return fromBit;
    }

    public int getToByte() {
        return toByte;
    }

    public int getToBit() {
        return toBit;
    }
}
