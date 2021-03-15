import java.io.IOException;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is useful for testing. It is a text-only interface for the World object.
 * It also establishes that World and the controller/viewer are independent
 * 
 * @author Sammy
 *
 */
public class TextTestStart extends Application {
    public static void main(String[] args) throws IOException, ParseException {

        World Edinbugh = new World();

        Edinbugh.Initialize(0, "The Crags");
        Edinbugh.Interpreter("where");
        // The scanner is used to take console input. You have to type in the commands
        // to interpreter manually
        @SuppressWarnings("resource")
        Scanner scan = new Scanner(System.in);

        System.out.println("Command Words:");
        System.out.println("GOLEFT, GORIGHT, GOFWD, where, ADD, REMOVE");

        while (true) {
            String input = scan.nextLine();
            Edinbugh.Interpreter(input);
        }

    }

    @Override
    public void start(Stage arg0) throws Exception {

    }
}