package Habitats;

import java.awt.*;

/**
 * This interface gives any habitat the method to draw. We can use this method to draw habitats in Simulation.
 */

public interface iHabitat {

    /**
     * method to give every habitats that implements iHabitat the method to draw.
     * @param g2d
     * @param tileSizeW
     * @param tileSizeH
     */
    void draw(Graphics2D g2d, int tileSizeW, int tileSizeH);


}
