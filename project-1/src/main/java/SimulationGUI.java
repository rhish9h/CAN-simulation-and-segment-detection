import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class SimulationGUI extends JFrame implements Observer {
    JLabel simulationData;

    public SimulationGUI() {
        super("Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);

        JLabel simulationDataHeading = getSimulationDataHeading();
        simulationData = new JLabel("[Placeholder for simulation data]");
        simulationData.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Create a panel to hold the table.
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(simulationDataHeading);
        panel.add(simulationData);

        // Add the panel to the frame.
        add(panel, BorderLayout.NORTH);

        addPlayButton();

        // Make the frame visible.
        setVisible(true);
    }

    private JLabel getSimulationDataHeading() {
        String heading = String.format("   %20s |   %10s |    %10s |         %10s |         %10s |         %10s |  %30s \n",
                "Current Time", "Vehicle Speed", "Steer Angle", "Yaw Rate", "Lat Accel", "Long Accel", "GPS Lat/Long");
        JLabel simulationDataHeading = new JLabel(heading);
        simulationDataHeading.setFont(new Font("Monospaced", Font.PLAIN, 12));
        return simulationDataHeading;
    }

    private void addPlayButton() {
        // Create a button to close the frame.
        JButton button = new JButton("Play/Pause");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // Add the button to the frame.
        add(button, BorderLayout.SOUTH);
    }

    @Override
    public void update(Observable o, Object arg) {
        simulationData.setText((String) arg);
    }
}
