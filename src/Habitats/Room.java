package Habitats;

import java.awt.*;

public class Room extends Habitat {

    /**
     * intializing different variables for rooms
     */
    private String classification;
    private String guestID;
    private boolean available = true;
    private boolean dirty = false;
    private boolean emergency = false;

    /**
     * Constructor for Room
     * @param areaType
     * @param positionX
     * @param positionY
     * @param dimH
     * @param dimW
     * @param classification
     */
    public Room(String areaType, int positionX, int positionY, int dimH, int dimW, String classification) {
        super(areaType, positionX, positionY, dimH, dimW);
        this.classification = classification;

    }


    /**
     * Override method for drawing Rooms at specific positions
     * @param g2d
     * @param tileSizeW
     * @param tileSizeH
     */
    @Override
    public void draw(Graphics2D g2d, int tileSizeW, int tileSizeH)
    {
        int Xposition = (this.getPositionX() * tileSizeW) + 4;
        int Yposition = (this.getPositionY() * tileSizeH) + 4;
        int Hdimension = (this.getDimH() * tileSizeH - 8);
        int Wdimension = (this.getDimW() * tileSizeW - 8);

        g2d.setColor(Color.GREEN);
        g2d.fillRect(Xposition, Yposition, Wdimension, Hdimension);
        g2d.setColor(Color.black);
        g2d.drawString(this.classification.charAt(0) + "" , Xposition + 5, Yposition + 15);
    }

    /**
     * getter and setters for values of Room
     * @return
     */
    public String getClassification() {
        return classification;
    }

    public String getGuestID() {
        return guestID;
    }

    public void setGuestID(String guestID) {
        this.guestID = guestID;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isEmergency() {
        return emergency;
    }

    public void setEmergency(boolean emergency) {
        this.emergency = emergency;
    }

    @Override
    public String toString() {
        return "Room{" +
                "areaType='" + getAreaType() +
                ", dimH=" + getDimH() +
                ", dimW=" + getDimW() +
                ", PosX=" + getPositionX() +
                ", PosY=" + getPositionY() +
                ", habitatID=" + getHabitatID() +
                ", classification=" + classification +
                ", guestID=" + guestID +
                ", available=" + available +
                '}';
    }
}
