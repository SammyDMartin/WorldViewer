import java.util.ArrayList;
import javafx.scene.image.Image;
import java.io.IOException;

/**
 * World stores all the world knowledge and Image objects. It takes commands as
 * inputs and stores Items and Images for access by Viewer.
 *
 * @author Sammy
 * @version 15/11/2018
 */
public class World {
    /**
     * @param viewangle describes the orientation of the viewer as an integer.
     * 
     */
    private Integer viewangle;
    /**
     * @param currentlocation is the Location object currently being used to display
     *                        images and items.
     */
    private Location currentlocation;
    /**
     * @param Locations ArrayList stores all the Location objects for quick access
     */
    private ArrayList<Location> Locations;
    /**
     * StoredItems contains all the items that were picked up.
     */
    private Bag StoredItems;

    /**
     * Initalizes most variables empty.
     */
    public World() {
        viewangle = null;
        currentlocation = null;
        Locations = new ArrayList<Location>();
        StoredItems = new Bag(null, null, null, new ArrayList<Image>());
    }

    /**
     * This large method that loads up all the objects necessary to complete the
     * world map.
     * <p>
     * Layout:
     * <p>
     * An Integer,String Hashmap is loaded from JSON An Integer, Image Hashmap is
     * loaded from JSON [If there are Items here] An items hashmap is made and
     * values added
     * <p>
     * A new Location(Forwards Hashmap, String Name, Images hashmap, Items Hashmap)
     * is made
     * <p>
     * 
     * @param startview     the viewing angle that World will Initialize at
     * @param startlocation the name of the location that World will Initialize at
     * @throws IOException
     */
    public void Initialize(int startview, String startlocation) {
        MapReader reader = new MapReader();
        reader.Startup();
        // Creates Location Arthurs Seat
        Location ArthursSeat = new Location(reader.ExtractMap("ArthursSeatF"), "Arthurs Seat",
                reader.ExtractImageMap("ArthursSeatI"), new ArrayList<Image>());
        // Creates Location Crags
        Location Crags = new Location(reader.ExtractMap("CragsF"), "The Crags", reader.ExtractImageMap("CragsI"),
                new ArrayList<Image>());
        // Creates Items Bag for Carlton Hill
        ArrayList<Image> CarltonB = new ArrayList<>();
        CarltonB.add(new Image("R3.png"));
        // Creates Location Carlton Hill
        Location CarltonHill = new Location(reader.ExtractMap("CarltonHillF"), "Carlton Hill",
                reader.ExtractImageMap("CarltonHillI"), CarltonB);
        // Creates Items Bag for Observatory
        ArrayList<Image> ObservatoryB = new ArrayList<>();
        ObservatoryB.add(new Image("R1.png"));
        // Creates Location Observatory
        Location Observatory = new Location(reader.ExtractMap("ObservatoryF"), "Observatory Hill",
                reader.ExtractImageMap("ObservatoryI"), ObservatoryB);
        // Creates Items Bag for Pier
        ArrayList<Image> PierB = new ArrayList<>();
        PierB.add(new Image("lobster.png"));
        // Creates Location Pier
        Location Pier = new Location(reader.ExtractMap("PierF"), "Granton Pier", reader.ExtractImageMap("PierI"),
                PierB);
        // Creates Items Bag for Cragmillar Castle
        ArrayList<Image> CragmillarB = new ArrayList<>();
        CragmillarB.add(new Image("coin.png"));
        // Creates Location Cragmillar Castle
        Location Cragmillar = new Location(reader.ExtractMap("CragmillarF"), "Cragmillar Castle",
                reader.ExtractImageMap("CragmillarI"), CragmillarB);

        // All the newly created objects are added to the Locations HashMap.
        Locations.add(ArthursSeat);
        Locations.add(Crags);
        Locations.add(CarltonHill);
        Locations.add(Observatory);
        Locations.add(Pier);
        Locations.add(Cragmillar);

        // This block sets the current location and view angle to what was specified in
        // the parmeters.
        for (Location L : Locations) {
            String locationname = L.getName();
            if (startlocation.equals(locationname)) {
                currentlocation = L;
                viewangle = startview;
            }
        }
        System.out.println("Done!");
    }

    /**
     * The public method to send commands to World.
     * 
     * @param Command a String instructing the World to turn, go forward or
     *                add/remove items
     */
    public void Interpreter(String Command) {
        if (Command.equals("GOFWD")) {
            goForward();
        }
        if (Command.equals("GOLEFT")) {
            turn(-1);
        }
        if (Command.equals("GORIGHT")) {
            turn(+1);
        }
        if (Command.equals("where")) {
            System.out.println(UpdateBox());
        }
        if (Command.equals("ADD")) {
            AddtoBag();
        }
        if (Command.equals("REMOVE")) {
            RemovefromBag();
        }
    }

    // PRIVATE methods to control location

    /**
     * Fetches forward locations' name from currentlocation. Iterates through
     * Locations to find that location and updates location and viewangle.
     */
    private void goForward() {
        // Gets forward location
        String next = currentlocation.getForward(viewangle);
        if (next == null) {
            System.out.println("//NO FORWARD LOCATION\\");
        } else {
            System.out.println("//INTERFACE ESTABLISHED\\");
            System.out.println("//STANDBY FOR TELEPORT\\");
            for (Location L : Locations) {
                // Iterates through all Locations looking for one with target name
                String locationname = L.getName();
                if (next.equals(locationname)) {
                    // Sets new location and view angle
                    currentlocation = L;
                    viewangle = 0;
                }
            }
        }
    }

    /**
     * Updates the viewangle to be left or right (if the turn move called was legal)
     * 
     * @param LorR +1 for right turn, -1 for left turn
     */
    private void turn(Integer LorR) {
        Integer totalangles = currentlocation.getLength();
        // For modular arithmatic - total number of view angles here
        Integer startangle = viewangle;
        if (LorR.equals(1)) {
            viewangle = (viewangle + 1) % totalangles;
            // turns right with looping back to 0 by modular arithmatic
        }
        if (LorR.equals(-1)) {
            if (viewangle.equals(0)) {
                viewangle = totalangles - 1;
                // Loops the other way (i.e. from 0 to max) if turning left at 0
            } else {
                viewangle = (viewangle - 1) % totalangles;
                // turns left
            }
        }
        // This block checks if there is an image at the new location.

        Image check = currentlocation.getImage(viewangle);

        if (check != null) {
        } else {
            viewangle = startangle;
            // Cancels the turn if no image is present
            // If the view does not wrap around then returning
            // to starting point is forbidden by this means
        }
    }

    // PUBLIC methods to return facts about location
    /**
     * Contains an error handling mechanism. If there is no image a cartoon mountain
     * is returned.
     * 
     * @return the current view's image
     */
    public Image UpdateView() {
        Image toshow = currentlocation.getImage(viewangle);

        if (toshow != null) {
            return toshow;
        } else {
            Image fakeout = new Image("cartoon.jpg");
            System.out.println("//VOID DETECTED\\");
            return fakeout; // If no image is found then return the cartoon mountain image.
        }

    }

    /**
     * 
     * @return A string containing the current view's name and viewangle.
     */
    public String UpdateBox() {
        String loc = currentlocation.getName() + " " + viewangle.toString();
        // Converts the current location and view into a user-readable format
        return loc;
    }

    /**
     * 
     * @return if there is a forward location. Used to grey out the FORWARD button.
     */
    public boolean isForward() {
        if (currentlocation.getForward(viewangle) != null) {
            return true;
        } else {
            return false;
        }
    }

    // PRVATE methods to control items

    /**
     * Adds an Item (Image object) to the bag and removes it from the location.
     * 
     */
    private void AddtoBag() {
        if (currentlocation.getItemsnumber().equals(1)) {
            Image theitem = currentlocation.getItem();
            StoredItems.putItem(theitem);
        } else {
            // Prints some diagnostics if something goes wrong and the method tries to add
            // from empty location.
            System.out.println("Items here:");
            System.out.println(currentlocation.getItemsnumber());
            System.out.println("Items in bag:");
            System.out.println(StoredItems.getItemsnumber());
        }
    }

    /**
     * Adds an item (Image object) to the bag and removes it from the location.
     */
    private void RemovefromBag() {
        if (StoredItems.getItemsnumber().equals(0)) {
            // Should not be invoked in normal operation
            // only for testing/if erroneous method call is made by GUI
            // If there is nothing to put down
            System.out.println("Bag Empty");
        } else {
            if (currentlocation.getItemsnumber().equals(0)) {
                // Returns correct item from bag
                Image theitem = StoredItems.getItem();
                currentlocation.putItem(theitem);
            } else {
                System.out.println("Location full-up!");
                // If there is already an item at this location
                // Should not be invoked in normal operation
                // only for testing/if erroneous method call is made by GUI
            }
        }
    }

    // PUBLIC methods to return facts about items

    /**
     * 
     * @return the number of items in the bag. Used to update the display dial and
     *         to check if putdown is allowed.
     */
    public Integer UpdateBagDisplay() {
        Integer bagsize = StoredItems.getItemsnumber();
        return bagsize;
    }

    /**
     * 
     * @return the item at the current location. Returns a blank image if there is
     *         no item to display.
     */
    public Image UpdateItems() {
        {
            Image itemtoshow = currentlocation.showItem();

            if (itemtoshow != null) {
                return itemtoshow;
            } else {
                // returns blank image - to prevent nullpointerexceptions
                Image empty = new Image("blank.jpg");
                return empty;
            }

        }
    }

    /**
     * @return if there is anything at the current location.
     */
    public Integer UpdateCurrentItems() {
        Integer locationsize = currentlocation.getItemsnumber();
        // In normal operation should only be 1 or 0
        return locationsize;
    }
}