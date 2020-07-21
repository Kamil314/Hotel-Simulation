import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Menu extends JPanel implements ActionListener {
    /**
     * JTextfields for user to input values before starting simulation.
     */
    private JTextField takeField;
    private JTextField cleaningField;
    private JTextField cinemaField;
    private JTextField simulatieField;

    /**
     * Values to store inputs of user before starting simulation.
     * values will affect how the simulation will run ** is not used yet**
     */
    private int walkingTick;
    private int cleaningTick;
    private int cinemaTick;
    private double simulatieFactor;

    /**
     * Constructor for positioning and initializing JLabels, JTextFields
     */
    public Menu() {


        Dimension size = getPreferredSize();
        size.width = 680;
        setPreferredSize(size);
        setLayout(new GridBagLayout());

        setBorder(BorderFactory.createTitledBorder("Instellen Hotel simulatie"));

        JLabel takeLabel = new JLabel("Hoeveel tick is de duur dat een gast naar boven/beneden loopt?");
        JLabel cleaningLabel = new JLabel("Hoeveel tick is de schoonmaker bezig met schoonmaken?");
        JLabel cinemaLabel = new JLabel("Hoeveel tick is de duur van een film in de cinema?");
        JLabel simulatieLabel = new JLabel("Factor snelheid van de simulatie? Allowed: > 0.0 && < 2.0");

        takeField = new JTextField(10);
        cleaningField = new JTextField(10);
        cinemaField = new JTextField(10);
        simulatieField = new JTextField(10);

        setLayout(new GridBagLayout());

        this.setLocation(takeLabel, GridBagConstraints.LINE_END, 0, 0, null, null);
        this.setLocation(cleaningLabel, GridBagConstraints.LINE_END, 0, 1, null, null);
        this.setLocation(cinemaLabel, GridBagConstraints.LINE_END, 0, 2, null, null);
        this.setLocation(simulatieLabel, GridBagConstraints.LINE_END, 0, 3, null, null);

        this.setLocation(takeField, GridBagConstraints.LINE_START, 1, 0, null, null);
        this.setLocation(cleaningField, GridBagConstraints.LINE_START, 1, 1, null, null);
        this.setLocation(cinemaField, GridBagConstraints.LINE_START, 1, 2, null, null);
        this.setLocation(simulatieField, GridBagConstraints.LINE_START, 1, 3, null, null);

    }

    /**
     * method for placing text and input fields in menu at certain positions.
     *
     * @param field
     * @param anchor
     * @param gridX
     * @param gridY
     * @param weightX
     * @param weightY
     */
    public void setLocation(JComponent field, Integer anchor, Integer gridX, Integer gridY, Double weightX, Double weightY) {
        GridBagConstraints location = new GridBagConstraints();

        if (weightX != null && weightY != null) {
            location.weightx = weightX;
            location.weighty = weightY;
        }

        location.anchor = anchor;
        location.gridx = gridX;
        location.gridy = gridY;

        this.add(field, location);
    }

    /**
     * When "Run simulation" button is clicked inputs in input fields is assigned to JTextFields
     *
     * @param e
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        walkingTick = Integer.parseInt(takeField.getText());
        takeField.setText(Integer.toString(walkingTick));

        cleaningTick = Integer.parseInt(cleaningField.getText());
        cleaningField.setText(Integer.toString(cleaningTick));

        cinemaTick = Integer.parseInt(cinemaField.getText());
        cinemaField.setText(Integer.toString(cinemaTick));

        simulatieFactor = Double.parseDouble(simulatieField.getText());
        simulatieField.setText(Double.toString(simulatieFactor));


    }

    /**
     * Getters to get values to affect how the simulation will run
     */


    public int getWalkingTick() {
        return walkingTick;
    }

    public int getCleaningTick() {
        return cleaningTick;
    }

    public int getCinemaTick() {
        return cinemaTick;
    }

    public double getSimulatieFactor() {
        return simulatieFactor;
    }
}

