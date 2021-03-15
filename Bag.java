import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.image.Image;

/**
 * Inherited from location - Bag has only the Items hashmap and stores all items
 * that are carried.
 * 
 * @author Sammy
 *
 */
public class Bag extends Location {
    public Bag(HashMap<Integer, String> ForwardLocations, String name, HashMap<Integer, Image> Images,
            ArrayList<Image> Items) {
        //Only Items hashmap and methods are allowed for Bag - all else is set to null.
        super(ForwardLocations, name, Images, Items);
        ForwardLocations = null;
        name = "Bag";
        Images = null;
    }

}