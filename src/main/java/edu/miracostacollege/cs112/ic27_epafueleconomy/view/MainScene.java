package edu.miracostacollege.cs112.ic27_epafueleconomy.view;


import edu.miracostacollege.cs112.ic27_epafueleconomy.controller.Controller;
import edu.miracostacollege.cs112.ic27_epafueleconomy.model.Vehicle;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * The <code>MainScene</code> represents the very first scene for the Vehicle Fuel Economy application.
 * It contains filters for make, minimum combined MPG, and maximum annual fuel cost.
 * The <code>MainScene</code> also allows for a user to add a new vehicle or remove an existing one.
 */
public class MainScene extends Scene {
    public static final int WIDTH = 850;
    public static final int HEIGHT = 600;
    private ImageView vehiclesIV = new ImageView();
    private ComboBox<String> makesCB;
    private Slider minCombinedMPGSlider = new Slider(0.0, 50.0, 0.0);
    private Slider maxAnnualFuelCostSlider = new Slider(0.0, 5000.0, 5000.0);
    private ListView<Vehicle> vehiclesLV = new ListView<>();
    private Button addButton = new Button("+ Add Vehicle");
    private Button removeButton = new Button("- Remove Vehicle");


    private Controller controller = Controller.getInstance();
    private ObservableList<Vehicle> vehiclesList;
    private Vehicle selectedVehicle;

    /**
     * Constructs a new <code>MainScene</code>, which represents the very first scene for the Vehicle Fuel Economy application.
     * It contains filters for make, minimum combined MPG, and maximum annual fuel cost.
     * The <code>MainScene</code> also allows for a user to add a new vehicle or remove an existing one.
     */
    public MainScene() {
        super(new GridPane(), WIDTH, HEIGHT);

        vehiclesIV.setImage(new Image("fuel_economy.png"));
        vehiclesIV.setFitWidth(WIDTH);
        vehiclesList = controller.getAllVehicles();
        vehiclesLV.setItems(vehiclesList);
        vehiclesLV.setPrefWidth(WIDTH);

        //TODO: Add a listener on the selected item property of the vehiclesLV
        //TODO: In the changed(...) method, assign the selected vehicle to the new value
        //TODO: If the selected vehicle is null (no vehicle selected), then disable the removeButton
        //TODO: Otherwise, it should be enabled.
        vehiclesLV.getSelectionModel().selectedItemProperty().addListener((obsVal, oldVal, newVal) -> updateSelectedVehicle(newVal));

        makesCB = new ComboBox<>(controller.getDistinctMakes());
        //Start combo box at index 0 (empty string) instead of -1
        makesCB.getSelectionModel().select(0);
        makesCB.setOnAction(e -> filter());


        minCombinedMPGSlider.setShowTickMarks(true);
        minCombinedMPGSlider.setShowTickLabels(true);
        minCombinedMPGSlider.setSnapToTicks(true);
        minCombinedMPGSlider.setMajorTickUnit(5.0f);
        minCombinedMPGSlider.setMinorTickCount(4);
        // TODO: For the slider, set its mouse dragged event to call filter()
        minCombinedMPGSlider.setOnMouseDragged(e -> filter());


        maxAnnualFuelCostSlider.setShowTickMarks(true);
        maxAnnualFuelCostSlider.setShowTickLabels(true);
        maxAnnualFuelCostSlider.setSnapToTicks(true);
        maxAnnualFuelCostSlider.setMajorTickUnit(500.0f);
        maxAnnualFuelCostSlider.setMinorTickCount(4);
        // TODO: For the slider, set its mouse dragged event to call filter()
        maxAnnualFuelCostSlider.setOnMouseDragged(e -> filter());


        addButton.setOnAction(e -> addVehicle());
        removeButton.setDisable(true);
        removeButton.setOnAction(e -> removeVehicle());

        GridPane pane = new GridPane();
        pane.setHgap(10.0);
        pane.setVgap(5);
        pane.setPadding(new Insets(5));
        pane.add(vehiclesIV, 0, 0, 2, 1);

        pane.add(new Label("Make:"), 0, 1);
        pane.add(makesCB, 1, 1);

        pane.add(new Label("Min Combined MPG:"), 0, 3);
        pane.add(minCombinedMPGSlider, 1, 3);

        pane.add(new Label("Max Annual Fuel Cost $"), 0, 4);
        pane.add(maxAnnualFuelCostSlider, 1, 4);

        pane.add(addButton, 0, 5, 2, 1);
        pane.add(vehiclesLV, 0, 6, 2, 1);
        pane.add(removeButton, 0, 7, 2, 1);

        this.setRoot(pane);
    }

    private void updateSelectedVehicle(Vehicle newVal) {
        selectedVehicle = newVal;
        removeButton.setDisable(selectedVehicle == null);
    }

    /**
     * Allows the user to remove an existing vehicle.
     * However, if the selected vehicle is null, just return (do nothing)
     * Make sure to update the list view to select nothing after removing.
     */
    private void removeVehicle() {
        //TODO: If the selected vehicle is null, just return (nothing to do)
        if (selectedVehicle == null)
            return;
        //TODO: Otherwise, remove the selected vehicle
        vehiclesList.remove(selectedVehicle);
        //TODO: and update the list view to select -1 (nothing)
        vehiclesLV.getSelectionModel().select(-1);

    }

    /**
     * Allows the user to add a new vehicle by navigating to the AddScene.
     */
    private void addVehicle() {
        //TODO: Use the ViewNavigator to load the AddScene
        ViewNavigator.loadScene("Add Scene", new AddScene(this));
    }

    /**
     * Reads the necessary information (make, min combined MPG, max annual fuel cost)
     * in order to send the criteria to the Controller for filtering.
     */
    private void filter() {
        String make = makesCB.getSelectionModel().getSelectedItem();
        double minCombinedMPG = minCombinedMPGSlider.getValue();
        double maxAnnualFuelCost = maxAnnualFuelCostSlider.getValue();

        vehiclesList = controller.filter(make, minCombinedMPG, maxAnnualFuelCost);
        vehiclesLV.setItems(vehiclesList);
    }
}
