package People;

import java.util.ArrayList;

/**
 * De klasse Cleaner zorgt ervoor dat er een nieuwe instantie van cleaner kan worden aangemaakt
 */


public class Cleaner extends Person {
    /**
     * checks if cleaner is free
     */
    private boolean isFree = true;

    /**
     * Constructor for cleaner
     *
     * @param personType
     * @param positionX
     * @param positionY
     * @param cleanerID
     */
    public Cleaner(String personType, int positionX, int positionY, String cleanerID) {
        super(personType, positionX, positionY, cleanerID);
    }


    /**
     * getter and setter for setting cleaner free or not
     *
     * @return
     */
    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }
}
