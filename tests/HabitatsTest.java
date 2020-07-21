import Habitats.Habitat;
import Habitats.Room;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class HabitatsTest {


    /**
     * Onderstaande tests hebben als doel
     * om te kijken of bepaalde ruimtes in onze "habitats arraylist"
     * zitten. Deze test is cruciaal voor ons project
     * en daarom is er gekozen om de onderstaande ruimtes te testen.
     */

    @Test
    public void habitatArrayRoomTest() {
        ArrayList<Room> roomList = new ArrayList<Room>();
        ArrayList<Habitat> habitats = new ArrayList<>();
        Habitat habitat = new Habitat("Room", 1, 1, 1, 1);

        assertEquals(habitat, "Guest");

        //   assertEquals(habitat.getAreaType().equals("Room"),habitat.getAreaType().equals("Room") );

    }

    @Test
    public void habitatArrayFitnessTest() {
        ArrayList<Room> roomList = new ArrayList<Room>();
        ArrayList<Habitat> habitats = new ArrayList<>();
        Habitat habitat = new Habitat("Fitness", 1, 1, 1, 1);

        assertEquals(habitat, "Room");

    }

    @Test
    public void habitatArrayRestaurantTest() {
        ArrayList<Room> roomList = new ArrayList<Room>();
        ArrayList<Habitat> habitats = new ArrayList<>();
        Habitat habitat = new Habitat("Restaurant", 1, 1, 1, 1);

        assertEquals(habitat, "Fitness");

    }

    @Test
    public void habitatArrayCinemaTest() {
        ArrayList<Room> roomList = new ArrayList<Room>();
        ArrayList<Habitat> habitats = new ArrayList<>();
        Habitat habitat = new Habitat("Cinema", 1, 1, 1, 1);

        assertEquals(habitat, habitat);

    }
}