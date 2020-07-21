package Habitats;


public class Coordinate {

    /**
     * gives class Coordinate a X and Y position
     */

    private int PositionX;
    private int PositionY;

    /**
     * Contructor of Coordinate
     * @param positionX
     * @param positionY
     */
    public Coordinate(int positionX, int positionY) {
        PositionX = positionX;
        PositionY = positionY;
    }

    /**
     * setters en getters we need to assign or get position from an object.
     */

    public int getPositionX() {
        return PositionX;
    }

    public void setPositionX(int positionX) {
        PositionX = positionX;
    }

    public int getPositionY() {
        return PositionY;
    }

    public void setPositionY(int positionY) {
        PositionY = positionY;
    }
}
