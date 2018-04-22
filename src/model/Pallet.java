package model;

import java.util.ArrayList;
import java.util.List;

public class Pallet {

	private int id;
	private String type;
	private double capacity;
	private List<CarPart> carParts;
	
	public Pallet(String type, double capacity) {
		this.type = type;
		this.capacity = capacity;
		this.carParts = new ArrayList<CarPart>();
	}

	/**
	 * Needed deserializing object from entity stream on HTTP request
	 */
	public Pallet() {}
	
	public int getId() {
		return id;
	}

	public double getCapacity() {
		return capacity;
	}

	public String getType() {
		return type;
	}

	public List<CarPart> getCarParts() {
		return carParts;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
	
	/**
	 * Used when reading pallets from the database.
	 * @param carPart
	 */
	public void addCarPart(CarPart carPart) {
		carParts.add(carPart);
	}

}
