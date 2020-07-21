import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class LoadingJsonFileTest {

    /**
     * In de onderstaande tests, worden functionaliteiten
     * getest die cruciaal zijn voor de juiste werkign van onze simulatie.
     * Wij hebben gekozen om de methode te testen die de json file uitleest.
     *
     * @throws IOException - deze foutmelding willen wij krijgen op het moment dat
     * de layout json file niet herkend wordt.
     */

    @Test(expected = FileNotFoundException.class)
    public void testReadFileWrong() throws IOException {
        FileReader reader = new FileReader("verkeerdtextbetand.txt");
        reader.read();
        reader.close();
    }

    @Test(expected = FileNotFoundException.class)
    public void testReadFileLayoutFour() throws IOException {
        FileReader reader = new FileReader("hotel4.layout");
        reader.read();
        reader.close();
    }

    @Test(expected = FileNotFoundException.class)
    public void testReadFileLayoutThree() throws IOException {
        FileReader reader = new FileReader("hotel3.layout");
        reader.read();
        reader.close();
    }

    @Test(expected = FileNotFoundException.class)
    public void testReadFileLayoutTwo() throws IOException {
        FileReader reader = new FileReader("hotel2.layout");
        reader.read();
        reader.close();
    }
}





