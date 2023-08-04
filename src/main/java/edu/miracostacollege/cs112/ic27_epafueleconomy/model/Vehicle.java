package edu.miracostacollege.cs112.ic27_epafueleconomy.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;

/**
 * The <code>Vehicle</code> class represents a gasoline vehicle, with information such as its year, make,
 * model, city MPG, highway MPG, combined MPG (55% city, 45% highway), annual fuel cost and fuel economy rating.
 *
 * @author Michael Paulding
 * @version 1.0
 */
public class Vehicle implements Serializable, Comparable<Vehicle> {
	// TODO: Ensure the class is Serializable so it may be written to a binary file.
	// TODO: Ensure the class implements Comparable, so that Vehicles may be sorted
	// TODO: by year, then by make, then by model.
	private int mYear;
	private String mMake;
	private String mModel;
	private double mCityMPG;
	private double mHighwayMPG;
	private double mCombinedMPG;
	private double mAnnualFuelCost;
	private int mFuelEconomyRating;

	private static final NumberFormat currency = NumberFormat.getCurrencyInstance();
	public static final DecimalFormat noDP = new DecimalFormat("0");

	public Vehicle(int year, String make, String model, double cityMPG, double highwayMPG, double annualFuelCost, int fuelEconomyRating) {
		mYear = year;
		mMake = make;
		mModel = model;
		mCityMPG = cityMPG;
		mHighwayMPG = highwayMPG;
		// Combined MPG is calculated as 55% city mpg and 45% highway mpg by the EPA
		mCombinedMPG = 0.55 * mCityMPG + 0.45 * mHighwayMPG;
		mAnnualFuelCost = annualFuelCost;
		mFuelEconomyRating = fuelEconomyRating;
	}

	public int getYear() {
		return mYear;
	}

	public void setYear(int year) {
		mYear = year;
	}

	public String getMake() {
		return mMake;
	}

	public void setMake(String make) {
		mMake = make;
	}

	public String getModel() {
		return mModel;
	}

	public void setModel(String model) {
		mModel = model;
	}

	public double getCityMPG() {
		return mCityMPG;
	}

	public void setCityMPG(double cityMPG) {
		mCityMPG = cityMPG;
	}

	public double getHighwayMPG() {
		return mHighwayMPG;
	}

	public void setHighwayMPG(double highwayMPG) {
		mHighwayMPG = highwayMPG;
	}

	public double getCombinedMPG() {
		return mCombinedMPG;
	}

	public void setCombinedMPG(double combinedMPG) {
		mCombinedMPG = combinedMPG;
	}

	public double getAnnualFuelCost() {
		return mAnnualFuelCost;
	}

	public void setAnnualFuelCost(double annualFuelCost) {
		mAnnualFuelCost = annualFuelCost;
	}

	public int getFuelEconomyRating() {
		return mFuelEconomyRating;
	}

	public void setFuelEconomyRating(int fuelEconomyRating) {
		mFuelEconomyRating = fuelEconomyRating;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Vehicle vehicle = (Vehicle) o;
		return mYear == vehicle.mYear && Double.compare(vehicle.mCityMPG, mCityMPG) == 0 && Double.compare(vehicle.mHighwayMPG, mHighwayMPG) == 0 && Double.compare(vehicle.mCombinedMPG, mCombinedMPG) == 0 && Double.compare(vehicle.mAnnualFuelCost, mAnnualFuelCost) == 0 && mFuelEconomyRating == vehicle.mFuelEconomyRating && Objects.equals(mMake, vehicle.mMake) && Objects.equals(mModel, vehicle.mModel);
	}

	@Override
	public int hashCode() {
		return Objects.hash(mYear, mMake, mModel, mCityMPG, mHighwayMPG, mCombinedMPG, mAnnualFuelCost, mFuelEconomyRating);
	}

	@Override
	public String toString() {
		return "Vehicle[" +
						"Year=" + mYear +
						", Make=" + mMake +
						", Model=" + mModel +
						", City=" + noDP.format(mCityMPG) + " mpg" +
						", Hwy=" + noDP.format(mHighwayMPG) + " mpg" +
						", Comb=" + noDP.format(mCombinedMPG) + " mpg" +
						", Annual Fuel Cost =" + currency.format(mAnnualFuelCost) +
						", FE Rating (1-10)=" + mFuelEconomyRating +
						']';
	}

	@Override
	public int compareTo(Vehicle other) {
		//Sort by year first
		int yearComp = this.mYear - other.mYear;
		if (yearComp != 0) return yearComp;

		//Next is the make
		int makeComp = this.mMake.compareTo(other.mMake);
		if (makeComp != 0) return makeComp;

		return this.mModel.compareTo(other.mModel);
	}
}
