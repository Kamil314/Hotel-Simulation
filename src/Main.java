import javax.swing.*;


public class Main {
    /**
     * Main method to create new instance of DisplaySwitch
     *
     * @param args
     */
    public static void main(String[] args) {


        SwingUtilities.invokeLater(() -> {

            try {
                DisplaySwitch display = new DisplaySwitch();

            } catch (Exception e) {
                e.printStackTrace();
            }


        });

    }
}

