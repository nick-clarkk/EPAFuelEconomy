package edu.miracostacollege.cs112.ic27_epafueleconomy.view;

import edu.miracostacollege.cs112.ic27_epafueleconomy.controller.Controller;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The <code>View</code> class represents the user interface for the application.  It initializes the
 * primary stage (window) and sets a custom icon (see code below), then instructs the ViewNavigator
 * to load the very first scene (the MainScene).
 *
 * @author Michael Paulding
 * @version 1.2
 */
public class View extends Application {

	/**
	 * Starts the application by setting the stage (window) with a custom icon, then navigating
	 * to the first scene, which happens to be the MainScene for this application.
	 * @param primaryStage The primary stage (window)
	 * @throws Exception if one occurs during startup.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Changing the icon (top left of stage) to custom icon (saved in resources folder)
		primaryStage.getIcons().add(new Image("fuel_economy_icon.png"));
		ViewNavigator.setStage(primaryStage);
		ViewNavigator.loadScene("Vehicle Fuel Economy", new MainScene());
	}

	/**
	 * Stop is called whenever the application shuts down (e.g. user closes the window).
	 * In this case we request the Controller to initiate the saving of data to the binary file.
	 * @throws Exception if one occurs during shutdown.
	 */
	@Override
	public void stop() throws Exception {
		// TODO: Uncomment once Serialization has been added.
		Controller.getInstance().saveData();
	}

	/**
	 * The entry point to this JavaFX application.  Application.launch will eventually make a call
	 * to the start() method.
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

}
