package Movement;

import Habitats.Coordinate;
import Habitats.Habitat;
import Habitats.Room;
import People.Cleaner;
import People.Guest;

import java.util.ArrayList;

public class Route {
    /**
     * value of frame
     */
    public int Frame;
    /**
     * value of routeIndex
     */
    public int RouteIndex;

    /**
     * persons to move
     */
    public Guest guest;
    public Cleaner cleaner;


    /**
     * list to store cooridinates
     */
    private ArrayList<Coordinate> coordinates = new ArrayList<>();

    /**
     * Room or habitat to go
     */
    private Habitat endHabitat;
    private Room endRoom;

    public void AddCoordinate(Coordinate c) {
        coordinates.add(c);
    }

    /**
     * get list of coordinates
     *
     * @return
     */
    public ArrayList<Coordinate> GetCoordinates() {
        return coordinates;
    }

    /**
     * get list of coordinates
     *
     * @return
     */

    public Coordinate GetCoordinate(int index) {
        if (index < coordinates.size()) {
            return coordinates.get(index);
        } else {
            return null;
        }
    }

    /**
     * getter and setter for Habitat or room to go
     *
     * @return
     */

    public Habitat getEndHabitat() {
        return endHabitat;
    }

    public void setEndHabitat(Habitat endHabitat) {
        this.endHabitat = endHabitat;
    }

    public Room getEndRoom() {
        return endRoom;
    }

    public void setEndRoom(Room endRoom) {
        this.endRoom = endRoom;
    }
}
