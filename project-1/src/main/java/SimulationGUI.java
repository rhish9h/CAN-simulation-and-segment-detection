import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class SimulationGUI extends JFrame implements Observer {
    private JLabel simulationData;
    private JPanel panel;

    public SimulationGUI() {
        super("Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);

        simulationData = new JLabel(String.format("%100s", "[Click Play to start Simulation]"));
        simulationData.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Create a panel to hold the table.
        panel = new JPanel(new GridLayout(0, 1));
        panel.add(getSimulationDataHeading());
        panel.add(getDashes());
        panel.add(simulationData);
        panel.add(new JLabel("\n"));
        panel.add(getSegmentsHeading());

        // Add the panel to the frame.
        add(panel, BorderLayout.NORTH);

        addPlayButton();

        // Make the frame visible.
        setVisible(true);
    }

    private JLabel getSimulationDataHeading() {
        String heading = String.format("   %22s |   %10s |    %10s |         %10s |         %10s |         %10s |  %30s \n",
                "Current Time", "Vehicle Speed", "Steer Angle", "Yaw Rate", "Lat Accel", "Long Accel", "GPS Lat/Long");
        JLabel simulationDataHeading = new JLabel(heading);
        simulationDataHeading.setFont(new Font("Monospaced", Font.PLAIN, 12));
        return simulationDataHeading;
    }

    private JLabel getDashes() {
        String dashes = String.format("   %22s |  %10s |  %10s |  %10s |  %10s |  %10s |  %30s \n",
                "-------------", "--------------", "-------------", "-----------------", "-----------------",
                "-----------------", "------------------------------");
        JLabel dashLabel = new JLabel(dashes);
        dashLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
        return dashLabel;
    }

    private JLabel getSegmentsHeading() {
        String segmentsHeading = String.format(" %12s | %19s | %19s | %10s | %10s | %10s | %10s | %10s | %10s | %10s",
                "Segment Type", "GPS Start Lat/Lon", "GPS End Lat/Lon", "Avg Veh Speed", "Max Accel", "Min Accel",
                "Max Veh Speed", "Min Veh Speed", "Len of straight", "Curve direction", "Deg of curve", "Max str angle");
        JLabel segHeadLabel = new JLabel(segmentsHeading);
        segHeadLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
        return segHeadLabel;
    }

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

    @Override
    public void update(Observable o, Object arg) {
        SimulationDTO simulationDTO = (SimulationDTO) arg;
        simulationData.setText(simulationDTO.getSensorData());

        if (simulationDTO.getSegmentData() != null) {
            addSegment(simulationDTO.getSegmentData());
        }
    }

    private void addSegment(String segment) {
        JLabel segmentLabel = new JLabel(segment + "\n");
        segmentLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
        panel.add(segmentLabel);
        panel.revalidate();
    }
}
