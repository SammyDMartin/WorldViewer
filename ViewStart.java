import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

// Citation: This is an adaptation of the JavaFx8 class example,
// original .java file can be found at https://groups.inf.ed.ac.uk/ijp/2018/dist/Assignments/Assignment2/ijp2018-assignment2.zip
public class ViewStart extends Application {

	public void start(Stage stage) {
		try {
			
			//Loads FXML file and starts up the GUI, begining with the Anchor Pane
			FXMLLoader fxmlLoader = new FXMLLoader();
			String viewerFxml = "WorldViewer.fxml";
			AnchorPane page = (AnchorPane) fxmlLoader.load(this.getClass().getResource(viewerFxml).openStream());
			Scene scene = new Scene(page);
			stage.setScene(scene);
			
			//Set application title and prevent resizing
			stage.setTitle("AutoNavigator");
			stage.setIconified(true);
			stage.setResizable(false);
			
			//Hands control over to Viewer until closed.
			Viewer controller = (Viewer) fxmlLoader.getController();
			controller.Initialise();

			stage.show();

		} catch (IOException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
			System.exit(1);
		}
	}

	public static void main(String args[]) {
		launch(args);
		System.exit(0);
	}
}
