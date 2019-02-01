import java.util.HashMap;
import javafx.scene.image.Image;
import java.util.ArrayList;

/**
 * This stores all the relevant information about a location - its name, what
 * locations it is connected to, items stored here, and the Images.
 *
 * @author Sammy
 * @version 15/11/2018
 */
public class Location {
	/**
	 * @param ForwardLocations stores as a hashmap the name of the location ahead of
	 *                         this view. It is marked on the viewer by red
	 *                         crosshairs.
	 */
	private HashMap<Integer, String> ForwardLocations;
	private String name;
	/**
	 * @param Images stores the images indexed by viewangle.
	 */
	private HashMap<Integer, Image> Images;
	/**
	 * @param Items stores all the items present at ths location. (If functioning
	 *              properly, there should only be one item here)
	 */
	private ArrayList<Image> Items;

	/**
	 * Constructor for objects of class Location
	 */
	public Location(HashMap<Integer, String> ForwardLocations, String name, HashMap<Integer, Image> Images,
			ArrayList<Image> Items) {
		// initialise instance variables
		this.ForwardLocations = ForwardLocations;
		this.name = name;
		this.Images = Images;
		this.Items = Items;
	}

	/**
	 * 
	 * @param angle will be a viewangle
	 * @return the name of the location ahead of this one
	 */
	public String getForward(int angle) {
		String nextloc = ForwardLocations.get(angle);
		return nextloc;
	}

	/**
	 * 
	 * @return the name of this Location
	 */
	public String getName() {
		return name.toString();
	}

	/**
	 * 
	 * @return the number of views available at this location
	 */
	public Integer getLength() {
		return ForwardLocations.size();
	}

	/**
	 * 
	 * @param angle usually the current viewangle
	 * @return the image from a viewangle
	 */
	public Image getImage(int angle) {
		return Images.get(angle);
	}

	/**
	 * Removes an item from Items and returns it
	 * 
	 * @return null if there are no items, or the last item added if items are
	 *         present
	 */
	public Image getItem() {
		if (getItemsnumber() == 0) {
			// prevents null pointer exceptions
			return null;
		} else {
			Image theitem = Items.get(Items.size() - 1);
			Items.remove(Items.size() - 1);
			return theitem;
		}
	}

	/**
	 * Adds an item to the Items Hashmap
	 * 
	 * @param the item to be added
	 */
	public void putItem(Image theitem) {
		Items.add(theitem);
	}

	/**
	 * 
	 * @return the number of Items stored here.
	 */
	public Integer getItemsnumber() {
		return Items.size();
	}

	/**
	 * Does not remove the item it returns. Used for display purposes.
	 * 
	 * @return the item to be displayed.
	 */
	public Image showItem() {
		if (getItemsnumber() == 0) {
			return null;
		}
		// Uses first-in-last out to return the last item that was put inside.
		Image theitem = Items.get(Items.size() - 1);
		return theitem;
	}
}
