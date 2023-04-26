import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationGUI extends JFrame {
    JLabel label;

    public SimulationGUI() {
        super("Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        label = new JLabel("Hello, World!");
        add(label);
        // Create a table with 8 rows and 8 columns.
        JTable table = new JTable(new Object[][] {
                { "Key 1", "Value 1" },
                { "Key 2", "Value 2" },
                { "Key 3", "Value 3" },
                { "Key 4", "Value 4" },
                { "Key 5", "Value 5" },
                { "Key 6", "Value 6" },
                { "Key 7", "Value 7" },
                { "Key 8", "Value 8" }
        }, new Object[] { "Key", "Value" });

        // Create a panel to hold the table.
        JPanel panel = new JPanel(new GridLayout(1, 0));
        panel.add(table);

        // Add the panel to the frame.
        add(panel, BorderLayout.CENTER);

        addPlayButton();

        // Make the frame visible.
        setVisible(true);
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
}
