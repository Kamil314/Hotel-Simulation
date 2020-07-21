package Habitats;

import JsonFile.ReadJSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

import static java.lang.Math.toIntExact;


public class HabitatFactory {


    /**
     * method to create new instances of habitats using/reading a JSON file.
     *
     * @param habitatArrayList
     * @param jsonlayout
     */

    public void createHabitats(ArrayList<Habitat> habitatArrayList, ReadJSON jsonlayout) {
        JSONArray jsonArray = jsonlayout.getJsonArray();

        jsonArray.forEach(item -> {
                    int cap = 0;
                    int W;
                    int H;
                    int x;
                    int y;
                    String classification = "";
                    String areatype;


                    JSONObject obj = (JSONObject) item;

                    areatype = (String) obj.get("AreaType");

                    String dimension = (String) obj.get("Dimension");
                    String[] StringDimension = dimension.trim().split("\\s*,\\s*");//remove any leading, trailing white spaces and split the string from rest of the white spaces
                    W = Integer.parseInt(StringDimension[0]);
                    H = Integer.parseInt(StringDimension[1]);

                    String position = (String) obj.get("Position");
                    String[] StringPosistions = position.trim().split("\\s*,\\s*");//remove any leading, trailing white spaces and split the string from rest of the white spaces
                    x = Integer.parseInt(StringPosistions[0]);
                    y = Integer.parseInt(StringPosistions[1]);


                    if (obj.containsKey("Capacity")) {

                        Object capacity = obj.get("Capacity");

                        if (capacity instanceof String) {
                            String capaCity = (String) obj.get("Capacity");
                            cap = Integer.parseInt(capaCity);


                        } else if (capacity instanceof Long) {
                            cap = toIntExact((long) obj.get("Capacity"));


                        }
                    } else if (obj.containsKey("Classification")) {
                        classification = (String) obj.get("Classification");

                    }


                    switch (areatype) {
                        case "Restaurant":
                            habitatArrayList.add(new Restaurant(areatype, x, y, H, W, cap));
                            break;
                        case "Room":

                            habitatArrayList.add(new Room(areatype, x, y, H, W, classification));

                            break;
                        case "Fitness":
                            habitatArrayList.add(new Fitness(areatype, x, y, H, W));

                            break;
                        case "Cinema":
                            habitatArrayList.add(new Cinema(areatype, x, y, H, W));

                            break;

                    }

                }
        );


    }

}

