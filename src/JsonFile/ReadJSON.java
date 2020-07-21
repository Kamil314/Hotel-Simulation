package JsonFile;

import org.json.simple.JSONArray;

import org.json.simple.parser.JSONParser;

import java.io.FileReader;


public class ReadJSON {
    /**
     *  Stores objects from Layout File in JSONArray
     */
    private JSONArray jsonArray;

    /**
     * Constuctor for choosing Layout File
     */
    public ReadJSON() {


        try {
            String layoutName = "hotel4";
            jsonArray = (JSONArray) readJsonSimple(layoutName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * method for getting and parsing Layout file in Layouts package
     * @param filename
     * @return JSONparser
     * @throws Exception
     */
    public static Object readJsonSimple(String filename) throws Exception {
        String filePath = "src/Layouts/" + filename + ".layout";
        FileReader reader = new FileReader(filePath);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }

    /**
     * getter to use Jsonarray as Layout for Simulation
     * @return jsonArray
     */
    public JSONArray getJsonArray() {
        return jsonArray;
    }
}


