package edu.miracostacollege.cs112.ic27_epafueleconomy.view;

import edu.miracostacollege.cs112.ic27_epafueleconomy.controller.Controller;
import edu.miracostacollege.cs112.ic27_epafueleconomy.model.Vehicle;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * The <code>AddScene</code> class allows a user to enter the information to add a new Vehicle to the collection
 * of reviews.  Fields are validated (must be provided) before the user is able to save the information.
 *
 * @author Michael Paulding
 * @version 1.1
 */
public class AddScene extends Scene {
    private Scene previousScene;

    public static final int WIDTH = 500;
    public static final int HEIGHT = 250;
    private Controller controller = Controller.getInstance();

    private TextField yearTF = new TextField();
    private Label yearErrorLabel = new Label("Year is required.");

    private TextField makeTF = new TextField();
    private Label makeErrorLabel = new Label("Make is required.");

    private TextField modelTF = new TextField();
    private Label modelErrorLabel = new Label("Model is required.");

    private TextField cityMPGTF = new TextField();
    private Label cityMPGErrorLabel = new Label("City MPG is required.");

    private TextField highwayMPGTF = new TextField();
    private Label highwayMPGErrorLabel = new Label("Highway MPG is required.");

    private TextField annualFuelCostTF = new TextField();
    private Label annualFuelCostErrorLabel = new Label("Annual fuel cost is required.");

    private TextField fuelEconomyRatingTF = new TextField();
    private Label fuelEconomyRatingErrorLabel = new Label("Fuel economy rating is required.");

    private Button saveButton = new Button("Save");
    private Button cancelButton = new Button("Cancel");

    /**
     * In the <code>AddScene</code>, a user is able to add a new Vehicle to the collection of reviews.
     * All fields are required (error message will appear if not provided)
     * @param previousScene A reference to the MainScene so that after saving (or canceling), user
     *                  is returned to the main scene.
     */
    public AddScene(Scene previousScene) {
        super(new GridPane(), WIDTH, HEIGHT);
        this.previousScene = previousScene;

        GridPane pane = new GridPane();
        pane.setHgap(10.0);
        pane.setVgap(5);
        pane.setPadding(new Insets(5));

        pane.add(new Label("Year:"), 0, 0);
        pane.add(yearTF, 1, 0);
        pane.add(yearErrorLabel, 2, 0);
        yearErrorLabel.setTextFill(Color.RED);
        yearErrorLabel.setVisible(false);

        pane.add(new Label("Make:"), 0, 1);
        pane.add(makeTF, 1, 1);
        pane.add(makeErrorLabel, 2, 1);
        makeErrorLabel.setTextFill(Color.RED);
        makeErrorLabel.setVisible(false);

        pane.add(new Label("Model:"), 0, 2);
        pane.add(modelTF, 1, 2);
        pane.add(modelErrorLabel, 2, 2);
        modelErrorLabel.setTextFill(Color.RED);
        modelErrorLabel.setVisible(false);

        pane.add(new Label("City MPG:"), 0, 3);
        pane.add(cityMPGTF, 1, 3);
        pane.add(cityMPGErrorLabel, 2, 3);
        cityMPGErrorLabel.setTextFill(Color.RED);
        cityMPGErrorLabel.setVisible(false);

        pane.add(new Label("Highway MPG:"), 0, 4);
        pane.add(highwayMPGTF, 1, 4);
        pane.add(highwayMPGErrorLabel, 2, 4);
        highwayMPGErrorLabel.setTextFill(Color.RED);
        highwayMPGErrorLabel.setVisible(false);

        pane.add(new Label("Annual Fuel Cost $"), 0, 5);
        pane.add(annualFuelCostTF, 1, 5);
        pane.add(annualFuelCostErrorLabel, 2, 5);
        annualFuelCostErrorLabel.setTextFill(Color.RED);
        annualFuelCostErrorLabel.setVisible(false);

        pane.add(new Label("Fuel Economy Rating [1-10]:"), 0, 6);
        pane.add(fuelEconomyRatingTF, 1, 6);
        pane.add(fuelEconomyRatingErrorLabel, 2, 6);
        fuelEconomyRatingErrorLabel.setTextFill(Color.RED);
        fuelEconomyRatingErrorLabel.setVisible(false);

        pane.add(cancelButton, 0, 7);
        pane.add(saveButton, 1, 7);

        saveButton.setOnAction(e -> save());
        cancelButton.setOnAction(e -> goBackToPrevScene());
        this.setRoot(pane);
    }

    /**
     * Reads each of the text fields and validates them.  If the required fields
     * are empty, user will see an error message (red text) next to the text field and will be prevented from saving.
     * Otherwise a new Vehicle object will be instantiated and added to the list of all vehicles.
     */
    private void save() {
        int year = 0, fuelEconomyRating=0;
        String make, model;
        double cityMPG=0.0, highwayMPG=0.0, annualFuelCost=0.0;

        try {
            year = Integer.parseInt(yearTF.getText());
            // First car was released in 1886 by Carl Benz
            yearErrorLabel.setVisible(year < 1886);
        }
        catch (NumberFormatException e) {  yearErrorLabel.setVisible(true); }

        make = makeTF.getText();
        makeErrorLabel.setVisible(make.isEmpty());

        model = modelTF.getText();
        modelErrorLabel.setVisible(model.isEmpty());

        try {
            cityMPG = Double.parseDouble(cityMPGTF.getText());
            cityMPGErrorLabel.setVisible(cityMPG < 0.0);
        }
        catch (NumberFormatException e) {  cityMPGErrorLabel.setVisible(true); }

        try {
            highwayMPG = Double.parseDouble(highwayMPGTF.getText());
            highwayMPGErrorLabel.setVisible(highwayMPG < 0.0);
        }
        catch (NumberFormatException e) {  highwayMPGErrorLabel.setVisible(true); }

        try {
            annualFuelCost = Double.parseDouble(annualFuelCostTF.getText());
            annualFuelCostErrorLabel.setVisible(annualFuelCost < 0.0);
        }
        catch (NumberFormatException e) { annualFuelCostErrorLabel.setVisible(true); }

        try {
            fuelEconomyRating = Integer.parseInt(fuelEconomyRatingTF.getText());
            fuelEconomyRatingErrorLabel.setVisible(fuelEconomyRating < 1 || fuelEconomyRating > 10);
        }
        catch (NumberFormatException e) {  fuelEconomyRatingErrorLabel.setVisible(true); }

        //TODO: If any of the error labels are visible, return.
        if (makeErrorLabel.isVisible() || modelErrorLabel.isVisible() || cityMPGErrorLabel.isVisible() || highwayMPGErrorLabel.isVisible()
                || annualFuelCostErrorLabel.isVisible() || fuelEconomyRatingErrorLabel.isVisible())
            return;
        //TODO: Otherwise, instantiate a new Vehicle object and add it to the list of all vehicles.
        Controller.getInstance().addVehicle(new Vehicle(year, make, model, cityMPG, highwayMPG, annualFuelCost, fuelEconomyRating));
        //TODO: Then, go back to the previous scene.
        goBackToPrevScene();
    }

    /**
     * Navigates back to the previous scene without having to create a new one.
     */
    private void goBackToPrevScene() {
        //TODO: Navigate back to the previous scene (e.g. MainScene)
        ViewNavigator.loadScene("Vehicle Fuel Economy", previousScene);
    }

}
