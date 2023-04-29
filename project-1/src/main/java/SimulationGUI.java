import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * GUI for the simulation, shows the real time sensor data and the
 * segments data list as they are detected
 * This is also an Observer of the Sensor Data Receiver, whenever data updates, GUI updates
 */
public class SimulationGUI extends JFrame implements Observer {
    private JLabel simulationData;
    private JPanel panel;

    /**
     * Constructor to build the GUI,
     * Consists of a panel with a grid of column 1,
     * grid rows have simulation heading, dashes, sim data,
     * segments heading, their dashes and segment data as they get added
     */
    public SimulationGUI() {
        super("Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 700);

        simulationData = new JLabel(String.format("%100s", "[Click Play to start Simulation]"));
        simulationData.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Create a panel to hold the table.
        panel = new JPanel(new GridLayout(0, 1));
        panel.add(getSimulationDataHeading());
        panel.add(getDashes());
        panel.add(simulationData);
        panel.add(new JLabel("\n"));
        panel.add(getSegmentsHeading());
        panel.add(new JLabel(" ---------------------------------------------------------------------------------" +
                "--------------------------------------------------------------------------------"));

        // Add the panel to the frame.
        add(panel, BorderLayout.NORTH);

        addPlayButton();

        // Make the frame visible.
        setVisible(true);
    }

    /**
     * Build and get the simulation data heading in the correct format
     * @return JLabel with simulation data heading
     */
    private JLabel getSimulationDataHeading() {
        String heading = String.format("   %28s |   %10s |    %10s |         %10s |         %10s |         %10s |  %30s \n",
                "Current Time", "Vehicle Speed", "Steer Angle", "Yaw Rate", "Lat Accel", "Long Accel", "GPS Lat/Long");
        JLabel simulationDataHeading = new JLabel(heading);
        simulationDataHeading.setFont(new Font("Monospaced", Font.PLAIN, 12));
        return simulationDataHeading;
    }

    /**
     * Build and get the dashes that match the sensor headings
     * @return JLabel with dashes
     */
    private JLabel getDashes() {
        String dashes = String.format("   %28s |  %10s |  %10s |  %10s |  %10s |  %10s |  %30s \n",
                "-------------", "--------------", "-------------", "-----------------", "-----------------",
                "-----------------", "------------------------------");
        JLabel dashLabel = new JLabel(dashes);
        dashLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
        return dashLabel;
    }

    /**
     * Build and get the segments heading in the right format
     * @return JLabel with segments heading
     */
    private JLabel getSegmentsHeading() {
        String segmentsHeading = String.format(" %8s | %19s | %19s | %11s | %13s | %13s | %10s | %10s | %10s | %10s |" +
                        " %11s | %11s",
                "Seg Type", "GPS Start Lat/Lon", "GPS End Lat/Lon", "Avg Veh Spd", "Max Accel", "Min Accel",
                "Max Veh Spd", "Min Veh Spd", "Len of str", "Curve dir", "Deg of curve", "Max str angle");
        JLabel segHeadLabel = new JLabel(segmentsHeading);
        segHeadLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
        return segHeadLabel;
    }

    /**
     * Builds and places a play / pause button at the bottom of the GUI window
     */
    private void addPlayButton() {
        // Create a button to close the frame.
        JButton button = new JButton("Play/Pause");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Simulation.getPaused()) {
                    Simulation.pauseSimulation();
                } else {
                    Simulation.unpauseSimulation();
                }
            }
        });

        // Add the button to the frame.
        add(button, BorderLayout.SOUTH);
    }

    /**
     * Whenever the Observable updates its data, this method will be triggered
     * updates the real time sensor data and adds a segment if sent
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        SimulationDTO simulationDTO = (SimulationDTO) arg;
        simulationData.setText(simulationDTO.getSensorData());

        if (simulationDTO.getSegmentData() != null) {
            addSegment(simulationDTO.getSegmentData());
        }
    }

    /**
     * Build and add a segment label to the display grid in the right format
     * @param segment formatted string of the segment data
     */
    private void addSegment(String segment) {
        JLabel segmentLabel = new JLabel(segment + "\n");
        segmentLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
        panel.add(segmentLabel);
        panel.revalidate();
    }
}
