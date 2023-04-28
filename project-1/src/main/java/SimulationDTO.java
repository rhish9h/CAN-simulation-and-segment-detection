/**
 * Data transfer object used to send data from receiver to gui,
 * as part of transfer from Observable to Observer during the update
 */
public class SimulationDTO {
    private String sensorData;
    private String segmentData;

    public SimulationDTO(String sensorData, String segmentData) {
        this.sensorData = sensorData;
        this.segmentData = segmentData;
    }

    public String getSensorData() {
        return sensorData;
    }

    public String getSegmentData() {
        return segmentData;
    }
}
