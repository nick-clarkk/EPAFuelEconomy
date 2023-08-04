package edu.miracostacollege.cs112.ic27_epafueleconomy.controller;

import edu.miracostacollege.cs112.ic27_epafueleconomy.model.Model;
import edu.miracostacollege.cs112.ic27_epafueleconomy.model.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The <code>Controller</code> is a Singleton object that relays all commands between the Model and View
 * (and vice versa).  There is only one Controller object, accessible by a call to the static getInstance()
 * method.
 *
 * @author Michael Paulding
 * @version 1.0
 */
public class Controller {

	private static Controller theInstance;
	private ObservableList<Vehicle> mAllVehiclesList;
	private ObservableList<Vehicle> mFilteredVehiclesList;
	private ObservableList<String> mDistinctMakes;

	private Controller() {}
	/**
	 * Gets the one instance of the Controller.
	 * @return The instance
	 */
	public static Controller getInstance() {
		if (theInstance == null) {
			theInstance = new Controller();

			// TODO: Note - if your capstone only uses binary file (not CSV)
			// TODO: You can remove the if statement and ONLY populate from the binary file.
			// TODO: Delete the else clause entirely.
			if (Model.binaryFileHasData())
				theInstance.mAllVehiclesList = Model.populateListFromBinaryFile();
			else
				theInstance.mAllVehiclesList = Model.populateListFromCSVFile();

			// TODO: Be sure to sort the data
			FXCollections.sort(theInstance.mAllVehiclesList);

			theInstance.mDistinctMakes = theInstance.initializeDistinctMakes();
			FXCollections.sort(theInstance.mDistinctMakes);

			theInstance.mFilteredVehiclesList = FXCollections.observableArrayList();
		}
		return theInstance;
	}

	/**
	 * Gets the list of all vehicles.
	 * @return The list of all vehicles.
	 */
	public ObservableList<Vehicle> getAllVehicles() {
		return mAllVehiclesList;
	}

	/**
	 * Adds a vehicle to the list in sorted order. Also updates the list of distinct makes.
	 * @param vehicle - The vehicle to be added.
	 */
	public void addVehicle(Vehicle vehicle)
	{
		mAllVehiclesList.add(vehicle);
		// TODO: Be sure to sort the data after adding a new vehicle
		//Sort in the list
		FXCollections.sort(mAllVehiclesList);
		addMake(vehicle.getMake());
		//Sort in combo box
		FXCollections.sort(mDistinctMakes);
	}

	/**
	 * Adds a make to the list of distinct makes (only if not present already).
	 * @param make The make to be added.
	 */
	private void addMake(String make)
	{
		if (!mDistinctMakes.contains(make))
			mDistinctMakes.add(make);
	}

	/**
	 * Makes a request for the model to save all vehicle data to
	 * a persistent binary file.
	 */
	public void saveData() {
		Model.writeDataToBinaryFile(mAllVehiclesList);
	}

	/**
	 * Gets a list of the distinct vehicle makes.
	 * @return The list of the distinct vehicle makes.
	 */
	public ObservableList<String> getDistinctMakes() {
		return mDistinctMakes;
	}

	/**
	 * Initializes the list of distinct makes with empty as the first value.
	 * @return The list of distinct makes with empty as the first value.
	 */
	private ObservableList<String> initializeDistinctMakes()
	{
		ObservableList<String> distinctMakes = FXCollections.observableArrayList();
		// TODO: Add an empty entry to the list (blank), so user has option to see all makes
		// TODO: Loop through all the vehicles and add their make to the distinctMakes, only if
		// TODO: the make is not already there (prevent duplicates).
		//Add an empty string first to not have NPE
		distinctMakes.add("");
		for (Vehicle v : mAllVehiclesList)
			//Add the vehicle's make to the distinct makes (prevent duplicates)
			if (!distinctMakes.contains(v.getMake()))
				distinctMakes.add(v.getMake());
		return distinctMakes;
	}

	/**
	 * Filters data based on make, minimum MPG and maximum annual fuel cost.
	 * @param make The make selected.
	 * @param minMPG The minimum MPG
	 * @param maxAnnualFuelCost The maximum annual fuel cost
	 * @return The filtered list based on the three criteria.
	 */
	public ObservableList<Vehicle> filter(String make, double minMPG, double maxAnnualFuelCost) {
		//get make from combo box, minMPG/maxAnnual from sliders
		//Make is defaulted to an empty string to avoid NPE problems
		//TODO: Clear the filtered vehicles list
		mFilteredVehiclesList.clear();
		//TODO: Add each Vehicle that matches the criteria to the filtered vehicles list.
		for (Vehicle v : mAllVehiclesList) {
			//Check for empty string to show all cars instead of filtering
			if (("".equals(make) || v.getMake().equals(make)) && v.getCombinedMPG() >= minMPG && v.getAnnualFuelCost() <= maxAnnualFuelCost)
			mFilteredVehiclesList.add(v);
		}
		//TODO: Return the filtered vehicles list.

		return mFilteredVehiclesList;
	}
}
