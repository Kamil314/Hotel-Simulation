package Movement;

import Habitats.Coordinate;
import Habitats.Habitat;

import java.awt.*;
import java.util.ArrayList;

public class PathFinder {
    private ArrayList<Habitat> habitats;
    private Habitat stairs;
    private int tileSizeW;
    private int tileSizeH;
    private int startX;
    private int startY;
    private int fps = 10;
    private Route route;
    private boolean backToLobby = false;


    /**
     * Constructor for PathFinding
     *
     * @param habitats
     * @param startX
     * @param startY
     * @param tileSizeW
     * @param tileSizeH
     */
    public PathFinder(ArrayList<Habitat> habitats, int startX, int startY, int tileSizeW, int tileSizeH) {
        this.habitats = habitats;
        this.tileSizeH = tileSizeH;
        this.tileSizeW = tileSizeW;
        this.startX = startX;
        this.startY = startY;

        for (Habitat habitat : habitats) {
            if (habitat.getAreaType().contains("Stairs")) {
                stairs = habitat;
            }
        }


    }

    /**
     * method For calcultating best route and add coordinate to route
     *
     * @param start
     * @param end
     * @return
     */
    public Route CalculateBestRoute(Coordinate start, Coordinate end) { // deze functie is nog niet compleet. Hier moet een shortes path algorithm voor gemaakt worden. nu gaan de personen altijd via trap

        System.out.println("Calculate best route.");
        route = new Route();
        int x = start.getPositionX();
        int y = start.getPositionY();

        while (x != end.getPositionX() && y != end.getPositionY()) {
            if (y == end.getPositionY()) {
                if (x < end.getPositionX()) {
                    x++;
                } else if (x > end.getPositionX()) {
                    x--;
                }
                route.AddCoordinate(new Coordinate(x, y));

            } else {

                while (x != stairs.getPositionX()) {
                    x++;
                    route.AddCoordinate(new Coordinate(x, y));
                }

                while (y != end.getPositionY()) {
                    if (y < end.getPositionY()) {
                        y++;
                    } else {
                        y--;
                    }
                    route.AddCoordinate(new Coordinate(x, y));
                }

                while (y == end.getPositionY() && x != end.getPositionX()) {
                    x--;
                    route.AddCoordinate(new Coordinate(x, y));
                }


            }
        }
        System.out.println("Calculate best route done.");
        return route;
    }

    /**
     * calculating path in fps to move person per frame
     *
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @param frame
     * @return
     */
    public int[] calculatePos(int startX, int startY, int endX, int endY, int frame) {
        int x = (int) ((((endX - startX) / (float) fps * frame)) + startX);
        int y = (int) ((((endY - startY) / (float) fps * frame)) + startY);
        return new int[]{x, y};
    }

    /**
     * Method to draw guests routes per frame per second
     *
     * @param g
     * @param route
     * @return
     */

    public boolean DrawRouteG(Graphics2D g, Route route) {

        if (route.Frame % fps == 0 && route.Frame != 0) {
            route.RouteIndex++;
        }

        g.setColor(Color.blue);

        Coordinate start = route.GetCoordinate(route.RouteIndex);
        Coordinate end = route.GetCoordinate(route.RouteIndex + 1);

        if (end != null) {
            int xStart = start.getPositionX() * tileSizeW;
            int yStart = start.getPositionY() * tileSizeH;
            int xEnd = end.getPositionX() * tileSizeW;
            int yEnd = end.getPositionY() * tileSizeH;


            int[] xy = calculatePos(xStart, yStart, xEnd, yEnd, route.Frame % fps);
            g.fillRect(xy[0], xy[1], 10, 10);
            route.Frame++;


            return false;
        } else {


            route.guest.setPositionX(route.getEndHabitat().getPositionX());
            route.guest.setPositionY(route.getEndHabitat().getPositionY());

            return true;
        }


    }

    /**
     * Method to draw cleaners routes per frame per second
     *
     * @param g
     * @param route
     * @return
     */

    public boolean DrawRouteC(Graphics2D g, Route route) {

        if (route.Frame % fps == 0 && route.Frame != 0) {
            route.RouteIndex++;
        }

        g.setColor(Color.red);
        Coordinate start = route.GetCoordinate(route.RouteIndex);
        Coordinate end = route.GetCoordinate(route.RouteIndex + 1);


        if (end != null) {
            int xStart = start.getPositionX() * tileSizeW;
            int yStart = start.getPositionY() * tileSizeH;
            int xEnd = end.getPositionX() * tileSizeW;
            int yEnd = end.getPositionY() * tileSizeH;


            //   g.drawLine(xStart,yStart,xEnd,yEnd);


            int[] xy = calculatePos(xStart, yStart, xEnd, yEnd, route.Frame % fps);
            g.fillRect(xy[0], xy[1], 10, 10);
            route.Frame++;


            return false;
        } else {

            route.cleaner.setFree(false);
            if (!backToLobby) {
                route.cleaner.setPositionX(route.getEndRoom().getPositionX());
                route.cleaner.setPositionY(route.getEndRoom().getPositionY());


            } else {
                route.cleaner.setPositionX(route.getEndHabitat().getPositionX());
                route.cleaner.setPositionY(route.getEndHabitat().getPositionY());

            }


            return true;
        }


    }


    public void setBackToLobby(boolean backToLobby) {
        this.backToLobby = backToLobby;
    }
}


