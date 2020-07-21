import Habitats.Habitat;
import Movement.PathFinder;
import Movement.Route;
import People.Person;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Simulation extends JPanel implements Runnable {

    /**
     * variable for path finding for persons
     */
    PathFinder p;
    /**
     * instance to get and set values and methods from class HotelGeneral
     */

    private HotelGeneral general;
    /**
     * size of total JPanel size
     */
    private int H;
    private int W;
    /**
     * standard size for each tile
     */
    private int tileSizeW;
    private int tileSizeH;
    /**
     * arraylist containing habitats for drawing
     */
    private ArrayList<Habitat> habitats;
    /**
     * standard size for persons
     */
    private int personsize;
    /**
     * centers person on a tile
     */
    private int center;
    private Thread t;
    /**
     * value of the frame
     */
    private int frame = 0;
    /**
     * set currentTick at value of i
     */

    private int i;

    /**
     * Constructor for setting initial values
     * initiating pathfinder for drawing walking guest and cleaners
     *
     * @param general
     */
    Simulation(HotelGeneral general) {
        this.general = general;
        this.H = general.getH();
        this.W = general.getW();
        tileSizeW = general.getTileSizeW();
        tileSizeH = general.getTileSizeH();

        personsize = tileSizeH / 4;
        center = (tileSizeH / 2) - (personsize / 2);

        habitats = general.getHabitats();
        p = new PathFinder(habitats, 1, 7, tileSizeW, tileSizeH);
    }

    /**
     * Method to start simulation thread
     */

    public void start() {
        System.out.println("Simulation has started its thread");
        if (t == null) {
            t = new Thread(this, "simulation");
            t.start();

        }
    }

    /**
     * method to stop simulation thread
     */
    public void stop() {
        System.out.println("Simulation has stopped its thread");
        t.stop();
    }

    /**
     * Overides paintComponent for drawing using methods in this class(Simulation)
     *
     * @param g
     */

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        doDrawing((Graphics2D) g);


    }

    /**
     * method for repainting this class
     */
    public void redraw() {
        this.repaint();

    }


    /**
     * runs all draw-methods for Drawing
     *
     * @param g2d
     */

    private void doDrawing(Graphics2D g2d) {


        drawGrid(g2d);
        drawHabitats(g2d);

        drawGuest(g2d);

        drawCleaners(g2d);

        drawWalkingGuests(g2d);
        drawWalkingCleaners(g2d);

    }

    /**
     * Draws grid on JPanel
     */

    private void drawGrid(Graphics2D g2d) {
        for (int tileX = 0; tileX < H; tileX += tileSizeH) {
            for (int tileY = 0; tileY < W; tileY += tileSizeW) {
                g2d.drawRect(tileX, tileY, tileSizeW, tileSizeH);
            }
        }
    }


    /**
     * Draws all habitats at its position
     */
    private void drawHabitats(Graphics2D g2d) {

        for (Habitat habitat : habitats) {

            habitat.draw(g2d, tileSizeW, tileSizeH);
            g2d.drawImage(habitat.getImage(), (habitat.getPositionX() * tileSizeW), (habitat.getPositionY() * tileSizeH), habitat.getDimW()*tileSizeW, habitat.getDimH()*tileSizeH, null, null);

        }

    }


    /**
     * Method to draw guests on its position
     *
     * @param g2d
     */

    public void drawGuest(Graphics2D g2d) {

        for (Person guest : general.getInHouseList()) {

            if (guest.getPersonType().equalsIgnoreCase("guest")) {
                g2d.setColor(Color.BLACK);
                g2d.fillRect(((guest.getPositionX() * tileSizeW)) + center, (guest.getPositionY() * tileSizeH) + center, personsize, personsize);
                g2d.drawImage(guest.getImage(), (guest.getPositionX() * tileSizeW) + center, (guest.getPositionY() * tileSizeH) + center, personsize, personsize, null, null);

            }

        }
    }

    /**
     * Method to draw walking guest
     *
     * @param g2d
     */

    public void drawWalkingGuests(Graphics2D g2d) {

        ArrayList<Route> routesDone = new ArrayList<Route>();
        for (Route route : general.routesG) {
            boolean done = p.DrawRouteG(g2d, route);
            if (done) {
                routesDone.add(route);
            }
        }

        for (Route route : routesDone) {
            System.out.println("Removing routesG");
            System.out.println(general.routesG.size());
            general.routesG.remove(route);
            System.out.println(general.routesG.size());
        }
    }

    /**
     * method to draw cleaner on its position
     *
     * @param g2d
     */
    public void drawCleaners(Graphics2D g2d) {

        for (Person cleaner : general.getCleanerList()) {

            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect((cleaner.getPositionX() * tileSizeW) + (center + center / 2), (cleaner.getPositionY() * tileSizeH) + center, personsize, personsize);
            g2d.drawImage(cleaner.getImage(), (cleaner.getPositionX() * tileSizeW) + (center + center / 2), (cleaner.getPositionY() * tileSizeH) + center, personsize, personsize, null, null);

        }


    }

    /**
     * Method to draw walking cleaner
     *
     * @param g2d
     */
    public void drawWalkingCleaners(Graphics2D g2d) {

        ArrayList<Route> routesDone = new ArrayList<Route>();
        for (Route route : general.routesC) {
            boolean done = p.DrawRouteC(g2d, route);
            if (done) {
                routesDone.add(route);
            }
        }

        for (Route route : routesDone) {
            System.out.println("Removing routesC");
            System.out.println(general.routesC.size());
            general.routesC.remove(route);
            System.out.println(general.routesC.size());
        }
    }


    /**
     * method to repaint this class (Simulation)
     **/

    @Override
    public void run() {

        boolean stop = false;
        while (!stop) {
            frame++;

            try {

                Thread.sleep(50);
            } catch (Exception e) {

            }
            redraw();

            if (frame % 20 == 0) {
                general.setCurrentTick(i);
            }
        }
    }


}






