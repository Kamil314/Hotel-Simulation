import ExterneLibrary.HotelEvent;
import ExterneLibrary.HotelEventType;
import Habitats.*;
import JsonFile.ReadJSON;
import Movement.PathFinder;
import Movement.Route;
import People.Cleaner;
import People.Guest;
import People.Person;

/**
 * De klasse HotelGeneral is een klasse waar wij nieuwe instancies van verschillende klassen in arraylijsten opslaan
 * Hieruit kunnen we belangrijke methodes oproepen om in de simulatie te gaan tekenen
 */


import java.util.ArrayList;

public class HotelGeneral {
    /**
     * values of total size JPanel
     */
    private final int H = 640;
    private final int W = 640;

    /**
     * list to store positions of routes that persons will take
     */
    public ArrayList<Route> routesG = new ArrayList<Route>();
    public ArrayList<Route> routesC = new ArrayList<Route>();

    /**
     * factory for creating different habitats using JSON File
     */
    HabitatFactory habitatFactory = new HabitatFactory();

    /**
     * setting up grid values for tile sizes
     */
    private int howManyTiles = 15;
    private int tileSizeW = W / howManyTiles;
    private int tileSizeH = H / howManyTiles;
    /**
     * position values of certain habitats
     */
    private int lobbyX;
    private int lobbyY;
    private int stairsX;
    private int elevatorX;
    private int elevatorY;

    /**
     * JSON File to get layout for creating habitats using HabitatFactory
     */
    private ReadJSON json = new ReadJSON();

    /**
     * list to store all habitats
     */
    private ArrayList<Habitat> habitats = new ArrayList<>();
    /**
     * list to store all rooms
     */
    private ArrayList<Room> roomList = new ArrayList<Room>();
    /**
     * list to store all guest
     */
    private ArrayList<Guest> inHouseList = new ArrayList<Guest>();
    /**
     * list to store all roomsToClean
     */
    private ArrayList<Room> roomsToClean = new ArrayList<Room>();

    /**
     * list to store all cleaners
     */
    private ArrayList<Cleaner> cleanerList = new ArrayList<Cleaner>();

    /**
     * Specific guest to use in different methods
     */
    private Guest guest;

    /**
     * specific habitat to use in all methods in HotelGeneral
     */
    private Habitat lobby;

    /**
     * variable of currentTick of the simulation
     */
    private int currentTick;
    /**
     * to calculate path person will take
     */
    private PathFinder p;

    /**
     * Constructor initialize habitats, cleaners and pathfinding for simulation
     */
    public HotelGeneral() {
        createHabitats();
        HabitatFilterToRooms();
        createCleaners();
        p = new PathFinder(habitats, 1, 1, 1, 1);

    }

    /**
     * filters rooms in habitatlist and add these rooms to roomlist
     */

    public void HabitatFilterToRooms() {
        for (Habitat habitat : habitats) {
            if (habitat.getAreaType().equals("Room")) {
                roomList.add((Room) habitat);


            }
        }
    }

    /**
     * adding cleaners to cleanerlist
     */
    public void createCleaners() {
        int aantalCleaners = 2;
        for (int i = 0; i < aantalCleaners; i++) {
            String employeeID = "Cleaner" + (i + 1);
            cleanerList.add(new Cleaner("Cleaner", lobbyX + (i + 1), lobbyY, employeeID));

        }

    }

    /**
     * method for creating public areas
     */
    public void createHabitats() {
        habitatFactory.createHabitats(habitats, json);

        int maxX = 0;
        int maxY = 0;
        int minX = 1;
        int minY = 1;

        for (Habitat habitat : habitats) {

            if (habitat.getPositionX() + habitat.getDimW() > maxX) {
                maxX = habitat.getPositionX() + habitat.getDimW();


            }

            if (habitat.getPositionY() + habitat.getDimH() > maxY) {
                maxY = habitat.getPositionY() + habitat.getDimH();

            }
            if (habitat.getPositionY() < minY) {
                minY = habitat.getPositionY() - 1;
            }
            if (habitat.getPositionX() == minX) {
                minX = habitat.getPositionX() - 1;
            }


        }


        stairsX = maxX;
        int elevatorY = minY + 6;

        int width = 1;
        int height = maxY;

        lobbyX = minX + 1;
        lobbyY = height;
        int lobbyW = stairsX - width;
        int lobbyH = 1;

        //lift


        Habitat elevator = new Elevator("Elevator", minX, elevatorY, width, width);
        habitats.add(elevator);
        elevatorX = elevator.getPositionX();
        elevatorY = elevator.getPositionY();


        Habitat elevatorHousing = new Elevator("ElevatorHousing", minX, minY, height, width);
        habitats.add(elevatorHousing);

        //trap
        Habitat stairs = new Stairs("Stairs", stairsX, minY, height, width);
        habitats.add(stairs);

        //lobby
        lobby = new Lobby("Lobby", lobbyX, lobbyY, lobbyH, lobbyW);
        habitats.add(lobby);


    }

    /**
     * method checking guest in and moving guest to room
     *
     * @param event
     */
    public void checkin(HotelEvent event) {

        String guestID = event.Data.keySet().toString().replaceAll("[\\[\\]\']", "").replaceAll("People.Guest: ", "");
        String guestClassificationPrefrence = event.Data.values().toString();

        int Xpos = lobbyX;
        int Ypos = lobbyY;


        guest = new Guest("guest", Xpos, Ypos, guestID, guestClassificationPrefrence);


        Room freeRoom = checkAvailability(event, guest);
        if (freeRoom == null) {
            System.out.println("no rooms available");
            return;
        } else {
            setRoomToGuest(freeRoom, guest);
        }


        System.out.println("guest " + guestID + " has checked in room: " + freeRoom.getHabitatID());


        Coordinate cStart = new Coordinate(Xpos, Ypos);
        Coordinate cEnd = new Coordinate(freeRoom.getPositionX(), freeRoom.getPositionY());
        Route route = p.CalculateBestRoute(cStart, cEnd);
        route.setEndHabitat(freeRoom);
        route.guest = guest;
        routesG.add(route);
    }

    /**
     * method for checking guest out and adiing room to roomsToClean
     *
     * @param event
     */
    public void checkOut(HotelEvent event) {
        Room guestRoom = null;
        String idToCheck = event.Data.values().toString().replaceAll("[\\[\\]]", "");

        for (Room room : roomList) {
            if (idToCheck.equals(room.getGuestID())) {
                room.setGuestID("");
                room.setDirty(true); // als de optie moet bestaan om de kamer op dirty te zetten dan room.isDirty(true);

                guestRoom = room;
                roomsToClean.add(guestRoom);
            }


        }

        inHouseList.removeIf(id -> id.getPersonID().equals(idToCheck));

        if (guestRoom != null) { // deze check moet niet nodig zijn er gaat iets anders fout.
            System.out.println("guest " + idToCheck + ": is checked out of room number: " + guestRoom.getHabitatID());

            System.out.println(guestRoom.toString());
        }

    }

    /**
     * method for checking if there is availble rooms using guest preferences
     *
     * @param event
     * @param guest
     * @return
     */
    public Room checkAvailability(HotelEvent event, Guest guest) {

        String classificationToCheck = event.Data.values().toString().replaceAll("[\\[\\]]", "");

        ArrayList<Room> availablerooms = new ArrayList<>();

        boolean available;
        int freeRoomIndex = -1;


        for (Room room : roomList) {

            if ((room.getClassification() + "s").equalsIgnoreCase(classificationToCheck)) {
                if (room.isAvailable()) {
                    availablerooms.add(room);

                }
            }
        }

        if (availablerooms.size() > 0) {
            Room roomtoCheckIn = availablerooms.get(0);

            for (Room room : roomList) {
                if (room.equals(roomtoCheckIn)) {
                    freeRoomIndex = roomList.indexOf(room);
                }
            }
        }

        if (freeRoomIndex == -1)
            return null;

        Room freeRoom = roomList.get(freeRoomIndex);
        return freeRoom;

    }

    /**
     * method for setting room to guest
     *
     * @param room
     * @param guest
     */

    public void setRoomToGuest(Room room, Guest guest) {
        if (guest != null) {
            guest.setRoomnumber(room.getHabitatID());

            //roomList.get(roomIndex).setAvailable(false); //als de optie moet bestaan om de kamer op inUse te zetten dan guestRoom.isInUse(true);
            room.setAvailable(false);
            room.setGuestID(guest.getGuestID());

            inHouseList.add(guest);

        }
    }

    /**
     * method for moving guest
     *
     * @param event
     */
    public void moveGuest(HotelEvent event) {
        String guestIDtoMove = event.Data.values().toString().replaceAll("[\\[\\]]", "");
        String personToMove = "guest";
        int guestIndex = 0;
        String habitatType = "";
        int habitatIndex = 0;
        boolean checkPersonToMoveExist = false;


        ArrayList<Habitat> habitatsToGo = new ArrayList<Habitat>();

        switch (event.Type) {

            case CHECK_OUT:
                guestIDtoMove = "People.guest: " + guestIDtoMove;
                habitatType = "Lobby";
                break;
            case NEED_FOOD:
                guestIDtoMove = "People.guest: " + guestIDtoMove;
                habitatType = "Restaurant";
                break;
            case GOTO_CINEMA:
                habitatType = "Cinema";
                break;
            case GOTO_FITNESS:
                guestIDtoMove = event.Data.keySet().toString().replaceAll("[\\[\\]\']", "").replaceAll("People.Guest: ", "");
                habitatType = "Fitness";
                break;
            case EVACUATE:
                habitatType = "Lobby";
                break;
        }
        for (Guest person : inHouseList) {

            if (person.getPersonID().equalsIgnoreCase(guestIDtoMove)) {
                guestIndex = inHouseList.indexOf(person);
                checkPersonToMoveExist = true;

            }


        }

        Guest guestToMove = inHouseList.get(guestIndex);


        for (Habitat habitat : habitats) {
            if (habitat.getAreaType().equalsIgnoreCase(habitatType)) {
                habitatsToGo.add(habitat);
            }
        }


        Habitat habitatToGo = habitatsToGo.get(habitatIndex);


        if (checkPersonToMoveExist) {
            System.out.println("guest " + guestToMove.getPersonID() + " is at: " + "x: " + guestToMove.getPositionX() + ", y:" + guestToMove.getPositionY());

            System.out.println("guest " + guestToMove.getPersonID() + "  need to go to: " + habitatToGo.getAreaType() + " at position " + "x: " + habitatToGo.getPositionX() + ", y:" + habitatToGo.getPositionY());
            System.out.println(habitatToGo.getAreaType() + " max capacity is : " + habitatToGo.getMaxCapacity() + ", current capacity is: " + habitatToGo.getCapacity());


            Coordinate cStart = new Coordinate(guestToMove.getPositionX(), guestToMove.getPositionY());
            Coordinate cEnd = new Coordinate(habitatToGo.getPositionX(), habitatToGo.getPositionY());
            Route route = p.CalculateBestRoute(cStart, cEnd);
            route.setEndHabitat(habitatToGo);
            route.guest = guestToMove;
            routesG.add(route);

            if (habitatToGo.getMaxCapacity() >= habitatToGo.getCapacity()) {
                habitatToGo.setCapacity(habitatToGo.getCapacity() + 1);
            }
            System.out.println("guest " + guestToMove.getPersonID() + " moved to " + habitatToGo.getAreaType() + ", Guest is at: " + "x: " + habitatToGo.getPositionX() + ", y:" + guestToMove.getPositionY());
            System.out.println(habitatToGo.getAreaType() + " max capacity is : " + habitatToGo.getMaxCapacity() + ", current capacity is: " + habitatToGo.getCapacity());
        }


        System.out.println(habitatsToGo.size());
        System.out.println(habitatIndex);


        if (habitatToGo.getCapacity() > habitatToGo.getMaxCapacity() && habitatToGo.getMaxCapacity() != 0 && habitatIndex < habitatsToGo.size() - 1) {
            System.out.println(habitatToGo.getAreaType() + " is full. go to ");
            System.out.println(habitatIndex);
            habitatIndex = +1;
            System.out.println(habitatIndex);
            habitatToGo = habitatsToGo.get(habitatIndex);

            System.out.println(habitatToGo.getAreaType());
            System.out.println("guest " + guestToMove.getPersonID() + " is at: " + "x: " + guestToMove.getPositionX() + ", y:" + guestToMove.getPositionY());

            System.out.println("guest " + guestToMove.getPersonID() + "  need to go to: " + habitatToGo.getAreaType() + " at position " + "x: " + habitatToGo.getPositionX() + ", y:" + habitatToGo.getPositionY());
            System.out.println(habitatToGo.getAreaType() + " max capacity is : " + habitatToGo.getMaxCapacity() + ", current capacity is: " + habitatToGo.getCapacity());


            Coordinate cStart = new Coordinate(guestToMove.getPositionX(), guestToMove.getPositionY());
            Coordinate cEnd = new Coordinate(habitatToGo.getPositionX(), habitatToGo.getPositionY());
            Route route = p.CalculateBestRoute(cStart, cEnd);
            route.setEndHabitat(habitatToGo);
            route.guest = guestToMove;
            routesG.add(route);

            if (habitatToGo.getMaxCapacity() >= habitatToGo.getCapacity()) {
                habitatToGo.setCapacity(habitatToGo.getCapacity() + 1);
            }

            System.out.println("guest " + guestToMove.getPersonID() + " moved to " + habitatToGo.getAreaType() + ", Guest is at: " + "x: " + guestToMove.getPositionX() + ", y:" + guestToMove.getPositionY());
            System.out.println(habitatToGo.getAreaType() + " max capacity is : " + habitatToGo.getMaxCapacity() + ", current capacity is: " + habitatToGo.getCapacity());

            if (habitatToGo.getCapacity() > habitatToGo.getMaxCapacity() && habitatToGo.getMaxCapacity() != 0 && habitatIndex == habitatsToGo.size() - 1) {
                goBackToRoom(habitatsToGo, guestToMove, habitatToGo);


            }

        }


    }

    /**
     * method for moving guest back to room when habitat is full
     *
     * @param habitatsToGo
     * @param guestToMove
     * @param habitatToGo
     */

    public void goBackToRoom(ArrayList<Habitat> habitatsToGo, Guest guestToMove, Habitat habitatToGo) {
        System.out.println(habitatToGo.getAreaType() + " is full. go to ");
        habitatsToGo.clear();

        int habitatIndex = 0;

        for (Room room : roomList) {

            if (room.getGuestID() != null) {
                if (room.getGuestID().equalsIgnoreCase(guestToMove.getPersonID())) {
                    habitatsToGo.add(room);
                }
            }
        }


        habitatToGo = habitatsToGo.get(habitatIndex);


        System.out.println(habitatToGo.getAreaType());

        System.out.println("guest " + guestToMove.getPersonID() + " is at: " + "x: " + guestToMove.getPositionX() + ", y:" + guestToMove.getPositionY());

        System.out.println("guest " + guestToMove.getPersonID() + "  need to go to: " + habitatToGo.getAreaType() + " at position " + "x: " + habitatToGo.getPositionX() + ", y:" + habitatToGo.getPositionY());

        Coordinate cStart = new Coordinate(guestToMove.getPositionX(), guestToMove.getPositionY());
        Coordinate cEnd = new Coordinate(habitatToGo.getPositionX(), habitatToGo.getPositionY());
        Route route = p.CalculateBestRoute(cStart, cEnd);
        route.setEndHabitat(habitatToGo);
        route.guest = guestToMove;
        routesG.add(route);

        System.out.println("guest " + guestToMove.getPersonID() + " moved to " + habitatToGo.getAreaType() + ", Guest is at: " + "x: " + guestToMove.getPositionX() + ", y:" + guestToMove.getPositionY());

    }

    /**
     * method for moving cleaners
     *
     * @param event
     */

    public void moveCleaner(HotelEvent event) {

        String guestID = event.Data.values().toString().replaceAll("[\\[\\]]", "");
        int cleanerIndex = 0;
        Room roomToGo;
        int roomIndex = 0;
        guestID = "People.guest: " + guestID;

        Cleaner cleaner = cleanerList.get(cleanerIndex);

        for (Room room : roomList) {
            if (room.getGuestID() != null) {
                if (room.getGuestID().equals(guestID)) {
                    if (event.Type.equals(HotelEventType.CLEANING_EMERGENCY)) {
                        room.setEmergency(true);
                        roomsToClean.add(room);
                    } else if (event.Type.equals(HotelEventType.CHECK_OUT)) {
                        room.setDirty(true);
                        roomsToClean.add(room);
                    }
                }
            }
        }


        if (cleanerList.size() <= 2 && cleanerList.size() > 1) {
            if (!cleaner.isFree()) {
                cleaner.setFree(true);
                cleanerIndex++;
                cleaner = cleanerList.get(cleanerIndex);

                if (cleanerIndex > 1) {
                    cleanerIndex = 0;
                    cleaner = cleanerList.get(cleanerIndex);
                }

            }
        } else {
            cleaner = cleanerList.get(cleanerIndex);
        }

        if (!roomsToClean.isEmpty()) {
            for (Room roomToClean : roomsToClean) {
                if (roomToClean.isEmergency()) {
                    p.setBackToLobby(false);
                    roomIndex = roomsToClean.indexOf(roomToClean);
                    roomToGo = roomsToClean.get(roomIndex);


                    Coordinate cStart = new Coordinate(cleaner.getPositionX(), cleaner.getPositionY());
                    Coordinate cEnd = new Coordinate(roomToGo.getPositionX(), roomToGo.getPositionY());
                    Route route = p.CalculateBestRoute(cStart, cEnd);
                    route.setEndRoom(roomToGo);
                    route.cleaner = cleaner;
                    routesC.add(route);

                    roomToClean.setEmergency(false);
                }
            }


            for (Room roomToClean : roomsToClean) {
                if (!roomToClean.isEmergency()) {
                    if (roomToClean.isDirty()) {
                        p.setBackToLobby(false);
                        roomIndex = roomsToClean.indexOf(roomToClean);
                        roomToGo = roomsToClean.get(roomIndex);


                        Coordinate cStart = new Coordinate(cleaner.getPositionX(), cleaner.getPositionY());
                        Coordinate cEnd = new Coordinate(roomToGo.getPositionX(), roomToGo.getPositionY());
                        Route route = p.CalculateBestRoute(cStart, cEnd);
                        route.setEndRoom(roomToGo);
                        route.cleaner = cleaner;
                        routesC.add(route);
                        roomToClean.setDirty(false);
                    }
                }
            }

        }
        System.out.println("amount of rooms to clean: " + roomsToClean.size());
        //  resetCleaner(cleaner);
    }

    /**
     * method for removing cleaned rooms from roomsToClean list
     */
    public void editRoomsToClean() {

        if (!roomsToClean.isEmpty()) {
            roomsToClean.removeIf(room -> !room.isDirty() || !room.isEmergency());
        }
    }

    /**
     * resets cleaner back to initial position at lobby
     *
     * @param cleaner
     */
    public void resetCleaner(Cleaner cleaner) {

        if (roomsToClean.isEmpty()) {
            p.setBackToLobby(true);
            Habitats.Coordinate cStart = new Habitats.Coordinate(cleaner.getPositionX(), cleaner.getPositionY());
            Habitats.Coordinate cEnd = new Habitats.Coordinate(lobbyX, lobbyY);
            Route route = p.CalculateBestRoute(cStart, cEnd);
            route.setEndHabitat(lobby);
            route.cleaner = cleaner;
            routesG.add(route);
        }
    }

    /**
     * For running methods in Hotelgenral class by each type of event
     *
     * @param event
     */
    public void eventHappend(HotelEvent event) {

        switch (event.Type) {

            case CHECK_IN:
                checkin(event);

                break;

            case CHECK_OUT:
                moveGuest(event);
                checkOut(event);
                moveCleaner(event);
                editRoomsToClean();

                break;
            case NEED_FOOD:
                moveGuest(event);

                break;
            case CLEANING_EMERGENCY:
                moveCleaner(event);
                editRoomsToClean();
                break;
            case GOTO_CINEMA:
                moveGuest(event);

                break;
            case GOTO_FITNESS:
                moveGuest(event);

                break;
            case EVACUATE:
                moveGuest(event);
                moveCleaner(event);
                inHouseList.clear();
                cleanerList.clear();
                if (inHouseList.isEmpty()) {
                    System.out.println("Everbody evacuated!!");
                }
                break;
            case GODZILLA:
                System.out.println("Hotel is destroyed by Godzilla!! all of your guests died!!");
                inHouseList.clear();
                cleanerList.clear();
                break;
            case START_CINEMA:
                break;
            case NONE:
                System.out.println("nothing happend");
                break;
        }
    }


    public ArrayList<Habitat> getHabitats() {
        return habitats;
    }


    public int getTileSizeW() {
        return tileSizeW;
    }

    public int getTileSizeH() {
        return tileSizeH;
    }

    public int getH() {
        return H;
    }

    public int getW() {
        return W;
    }

    public ArrayList<Guest> getInHouseList() {
        return inHouseList;
    }


    public void setCurrentTick(int currentTick) {
        this.currentTick = currentTick;
    }


    public ArrayList<Cleaner> getCleanerList() {
        return cleanerList;
    }
}



