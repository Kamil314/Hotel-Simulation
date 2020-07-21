import People.Cleaner;
import People.Person;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class DrawingPersonsTest {

    private Graphics g2d;

    /**
     * Wij vonden heel belangrijk om te testen of wij de benodigde informatie
     * uit onze arraylists kunnen halen waar wij onze gasten en schoonmakers opslaan.
     * Als deze methodes niet goed zouden werken, is het bijna onmogelijk om de gasten
     * en de schoonmakers te tekenen in ozne hotelsimulatie.
     */

    @Test
    public void drawCleaners() {

        HotelGeneral general = new HotelGeneral();
        for (Cleaner cleaner : general.getCleanerList()) {
            if (cleaner.getPersonType().equals("Cleaner")) {

                System.out.println("Found a cleaner");
            } else {
                System.out.println("Found someone else");
                assertEquals("Guest", "Cleaner");
            }
        }
    }


    @Test
    public void drawGuest() {
        HotelGeneral general = new HotelGeneral();


        for (Person guest : general.getInHouseList()) {
            if (guest.getPersonType().equals("guest")) {
                System.out.println("Found someone else");
            } else {
                System.out.println("Found a Guest");
            }

            assertEquals("Onzin", "guestasd");

        }
    }

}