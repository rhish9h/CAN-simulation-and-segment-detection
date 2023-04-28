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
