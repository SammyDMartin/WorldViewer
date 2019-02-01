import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

public class Viewer {

	// These are all the objects that are interacted with by the controller.

	/**
	 * @param imageView1 is the image view window for the Item found at the current
	 *                   locaton.
	 */
	@FXML
	private ImageView imageView1;

	/**
	 * @param imageView is the view window for the main location image.
	 */
	@FXML
	private ImageView imageView;
	/**
	 * @param locationbox displays the current location and viewing angle to the
	 *                    user.
	 */
	@FXML
	private TextField locationbox;
	/**
	 * @param bagbox displays the number of items contained in the bag.
	 */
	@FXML
	private TextField bagbox;
	@FXML
	private Button forwardbutton;
	@FXML
	private Button leftbutton;
	@FXML
	private Button rightbutton;
	@FXML
	private MenuItem putdown;
	@FXML
	private MenuItem pickup;

	private World WorldTarget;

	/**
	 * Create the world and start the viewer at the default location and viewangle.
	 * 
	 * @throws IOException
	 */
	public void Initialise() throws IOException {
		World Edinburgh = new World();

		// Can Initialize the world 'Edinburgh' at any location and viewangle by
		// changing this method call.
		Edinburgh.Initialize(0, "The Crags");
		WorldTarget = Edinburgh;

		// Updates all aspects of the viewer in order.
		showBox();
		showItem();
		show();
		updateButtons();

	}

	/**
	 * Updates the items displayed in viewer to be in line with World.
	 */
	private void showItem() {
		// Gets current items from World
		Image item = WorldTarget.UpdateItems();
		imageView1.setImage(item);
		bagbox.setText(WorldTarget.UpdateBagDisplay().toString());
	}

	/**
	 * Tests the left, forward and right buttons to see if the same image is
	 * returned after being called. Disables any buttons that return the same image.
	 * Checks to see if picking up or putting down items is allowed and
	 * disables/enables relevant buttons.
	 */
	private void updateButtons() {
		Image testimage = WorldTarget.UpdateView();
		WorldTarget.Interpreter("GOLEFT");
		if (WorldTarget.UpdateView().equals(testimage)) {
			// If image isn't updated on left turn (because turn method in World has refused
			// command,
			// Disable left turn button
			leftbutton.setDisable(true);
		} else {
			WorldTarget.Interpreter("GORIGHT");
			leftbutton.setDisable(false);
			// Enable turn button and correct viewangle after 'test' turn
		}

		WorldTarget.Interpreter("GORIGHT");
		if (WorldTarget.UpdateView().equals(testimage)) {
			// If image isn't updated on right turn (because turn method in World has
			// refused command,
			// Disable right turn button
			rightbutton.setDisable(true);
		} else {
			WorldTarget.Interpreter("GOLEFT");
			rightbutton.setDisable(false);
			// Enable turn button and correct viewangle after 'test' turn
		}
		if (WorldTarget.isForward() == false) {
			forwardbutton.setDisable(true);
			// Disables forward button if there is no forward location
		} else {
			forwardbutton.setDisable(false);
			// Enables forward button if there is forward location
		}

		// BAG HANDLING

		// Checks Bag conditions first - if there are items in the bag, they can be put
		// down, otherwise not.
		if (WorldTarget.UpdateBagDisplay().equals(0)) {
			putdown.setVisible(false);
		} else {
			putdown.setVisible(true);
		}
		// Checks if item is already at location - if so Item can be picked up but an
		// item
		// cannot be put down.
		if (WorldTarget.UpdateCurrentItems().equals(1)) {
			pickup.setVisible(true);
			putdown.setVisible(false);
		} else {
			pickup.setVisible(false);

		}

	}

	/**
	 * updates the image view from World - is always called after a turn or go
	 * forward.
	 */

	private void show() {
		Image toshow = WorldTarget.UpdateView();
		imageView.setImage(toshow);
	}

	/**
	 * updates the location display box on the viewer.
	 */
	private void showBox() {
		locationbox.setText(WorldTarget.UpdateBox());
	}

	/**
	 * invoked when the forward button is pressed - sends the command to world,
	 * which updates the location and items. Then refreshes everything in the view.
	 */
	public void forward(ActionEvent event) {
		WorldTarget.Interpreter("GOFWD");
		// Must update view, buttons, locationbox and items after going forward.
		show();
		showItem();
		showBox();
		updateButtons();
	}

	/**
	 * Invoked when the left button is pressed.
	 * 
	 */
	public void left(ActionEvent event) {
		WorldTarget.Interpreter("GOLEFT");
		// Updates location box and image after World is changed
		// Items don't need to be updated
		show();
		showBox();
		updateButtons();
	}

	/**
	 * Invoked when the right button is pressed.
	 * 
	 */
	public void right(ActionEvent event) {
		WorldTarget.Interpreter("GORIGHT");
		// Updates location box, Buttons and image after World is changed
		// Items don't need to be updated
		show();
		showBox();
		updateButtons();
	}

	/**
	 * Invoked when the 'pick up' command from the menu is entered.
	 * 
	 */
	public void add(ActionEvent event) {
		WorldTarget.Interpreter("ADD");
		// Only Items and buttons need be updated after pick up
		showItem();
		updateButtons();
	}

	/**
	 * Invoked when the 'put down' command from the menu is entered.
	 * 
	 */
	public void remove(ActionEvent event) {
		WorldTarget.Interpreter("REMOVE");
		// Only Items and buttons need be updated after put down
		showItem();
		updateButtons();
	}
}
