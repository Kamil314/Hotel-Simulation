import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DisplaySwitch implements ActionListener {
    /**
     * starts and stops the simulation;
     */
    private Simulation simulation;
    /**
     * starts and stops the events for the simulation;
     */
    private HotelEventAdapter adapter;
    /**
     * stores different panels. For switching between panels
     */
    private JPanel panelContainer;
    /**
     * stores panelContainer. To switch between different panels
     */
    private CardLayout container;

    /**
     * constructor for instantiating a new JFrame, JPanels and containers
     * to switch from 1st panel to the 2nd panel and to start the Simulation
     **/

    DisplaySwitch() {
        HotelGeneral hotelGeneral = new HotelGeneral();
        JFrame display = new JFrame("Project Hotel Groep 5");
        display.setSize(hotelGeneral.getW(), hotelGeneral.getH());
        display.pack();
        display.setResizable(false);
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.setLocationRelativeTo(null);
        display.setVisible(true);
        display.setSize(new Dimension(hotelGeneral.getW(), hotelGeneral.getH()));
        display.setPreferredSize(new Dimension(hotelGeneral.getW(), hotelGeneral.getH()));
        display.setFocusable(true);


        JButton runSimulation = new JButton("Run simulation");
        runSimulation.addActionListener(this);
        Menu menu = new Menu();
        menu.add(runSimulation);

        JButton stopSimulation = new JButton("Stop simulation");
        stopSimulation.addActionListener(this::actionStopSimulation);


        simulation = new Simulation(hotelGeneral);

        simulation.add(stopSimulation);


        container = new CardLayout();


        panelContainer = new JPanel();
        panelContainer.setLayout(container);
        panelContainer.add(menu);
        panelContainer.add(simulation);


        display.add(panelContainer);


        adapter = new HotelEventAdapter(hotelGeneral);

    }


    /**
     * overrides Action Listener, actionPerformed method, when run Simulation button is clicked
     * Display switches to next JPanel in panelContainer, simulation and events will start
     *
     * @param e
     */

    @Override
    public void actionPerformed(ActionEvent e) {

        container.next(panelContainer);
        simulation.start();
        adapter.run();
    }

    /**
     * when button stop is clicked the simulation and events will stop
     * when simulation is stopped you have to rerun Main to refresh the siulation
     *
     * @param e
     */
    public void actionStopSimulation(ActionEvent e) {
        simulation.stop();
        adapter.stop();
    }

}