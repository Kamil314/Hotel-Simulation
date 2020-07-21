package People;

import Habitats.Coordinate;

import javax.swing.*;
import java.awt.*;


public class Person extends Coordinate {
    /**
     * intialize values for person
     */
    private String personType;
    private String personID;
    private Image image;

    /**
     * constructor of Person
     *
     * @param personType
     * @param positionX
     * @param positionY
     * @param personID
     */
    public Person(String personType, int positionX, int positionY, String personID) {
        super(positionX, positionY);
        this.personType = personType;
        this.personID = personID;
        setImage();
    }

    /**
     * for getting values of Person
     *
     * @return
     */
    public String getPersonType() {
        return personType;
    }

    public String getPersonID() {
        return personID;
    }

    /**
     * getter and setter for setting and getting image for person
     *
     * @return
     */
    public Image getImage() {
        return image;
    }


    public void setImage() {
        ImageIcon ii = new ImageIcon("src/Images/" + this.personType + ".png");
        this.image = ii.getImage();
    }

    @Override
    public String toString() {
        return "Person{" +
                "personType=" + personType +
                ", personID=" + personID +
                "Xpos:" + getPositionX() +
                "Ypos:" + getPositionY() + '}';


    }
}
