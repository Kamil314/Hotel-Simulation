package People;

import java.util.ArrayList;

/**
 * De klasse guest zorgt ervoor dat er een nieuwe instantie van guest kan worden aangemaakt
 */


public class Guest extends Person {

    /**
     * intializing variables for guest
     */
    private String guestID;
    private String guestClassificationPrefrence;
    private int roomnumber;

    /**
     * Constructor of guest
     *
     * @param personType
     * @param posX
     * @param posY
     * @param guestID
     * @param guestClassificationPrefrence
     */
    public Guest(String personType, int posX, int posY, String guestID, String guestClassificationPrefrence) {
        super(personType, posX, posY, guestID);
        this.guestID = guestID;
        this.guestClassificationPrefrence = guestClassificationPrefrence;

    }

    /**
     * getter and setter of values of guest
     *
     * @param roomnumber
     */
    public void setRoomnumber(int roomnumber) {
        this.roomnumber = roomnumber;
    }

    public String getGuestID() {
        return guestID;
    }

    @Override
    public String toString() {
        return
                "guestID:" + guestID + "\t" +
                        "roomNumber:" + roomnumber + "\t" +
                        "guestClassificationPrefrence:" + guestClassificationPrefrence + "\t" +
                        "guestXpos:" + getPositionX() + "\t" +
                        "guestYpos:" + getPositionY();

    }

}
