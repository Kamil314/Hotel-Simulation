package Habitats;

import javax.swing.*;
import java.awt.*;


public class Habitat extends Coordinate implements iHabitat {

    private static int countID;
    /**
     * intializing different variables for habitats
     */
    private String areaType;
    private int dimH;
    private int dimW;
    private int habitatID;
    private int capacity;
    private int maxCapacity;

    /**
     * variable for setting image to habitat
     */
    private Image image;

    /**
     * Constructor of Habitat
     *
     * @param areaType
     * @param positionX
     * @param positionY
     * @param dimH
     * @param dimW
     */
    public Habitat(String areaType, int positionX, int positionY, int dimH, int dimW) {
        super(positionX, positionY);
        this.areaType = areaType;
        this.dimH = dimH;
        this.dimW = dimW;
        habitatID = countID++;
        setImage();


    }

    /**
     * getter and setters for values of habitat
     *
     * @return
     */
    public String getAreaType() {
        return areaType;
    }


    public int getDimH() {
        return dimH;
    }

    public int getDimW() {
        return dimW;
    }

    public int getHabitatID() {
        return habitatID;
    }


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * method for drawing habitats at specific positions with colors
     *
     * @param g2d
     * @param tileSizeW
     * @param tileSizeH
     */
    @Override
    public void draw(Graphics2D g2d, int tileSizeW, int tileSizeH) {
        int Xposition = (this.getPositionX() * tileSizeW) + 4;
        int Yposition = (this.getPositionY() * tileSizeH) + 4;
        int Hdimension = (this.getDimH() * tileSizeH - 8);
        int Wdimension = (this.getDimW() * tileSizeW - 8);

        switch (this.getAreaType()) {
            case "Room":

                g2d.setColor(Color.GREEN);

                break;
            case "Fitness":
                g2d.setColor(Color.BLUE);

                break;
            case "Cinema":
                g2d.setColor(Color.MAGENTA);

                break;
            case "Restaurant":
                g2d.setColor(Color.RED);

                break;
            case "Lobby":
                g2d.setColor(Color.cyan);

                break;
            case "Elevator":
                g2d.setColor(Color.YELLOW);

                break;
            case "ElevatorHousing":
                g2d.setColor(Color.YELLOW);

                break;
            case "Stairs":
                g2d.setColor(Color.ORANGE);

                break;

        }


        if (this.getAreaType().equalsIgnoreCase("ElevatorHousing")) {
            g2d.drawRect(Xposition, Yposition, Wdimension, Hdimension);
        } else {
            g2d.fillRect(Xposition, Yposition, Wdimension, Hdimension);

        }

    }

    /**
     * Getter and setter for getting and setting images to habitats
     *
     * @return
     */
    public Image getImage() {
        return image;
    }

    public void setImage() {
        ImageIcon ii = new ImageIcon("src/Images/" + this.getAreaType() + ".png");
        this.image = ii.getImage();
    }


    @Override
    public String toString() {
        return "Habitat{" +
                "areaType='" + areaType + '\'' +
                ", dimH=" + dimH +
                ", dimW=" + dimW +
                ", habitatID=" + habitatID +
                '}';
    }
}
