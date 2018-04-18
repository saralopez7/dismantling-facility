package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pallet {

	private int id;
	private String type;
	private double capacity;
	private Map<String, List<CarPart>> carParts;
	
	public Pallet(String type, double capacity) {
		this.type = type;
		this.capacity = capacity;
		this.carParts = new HashMap<String, List<CarPart>>();
	}

	public int getId() {
		return id;
	}

	public double getCapacity() {
		return capacity;
	}

	public String getType() {
		return type;
	}

	public Map<String, List<CarPart>> getCarParts() {
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
	
	public void addCarPart(CarPart carPart) {
		List<CarPart> carPartsByType;
		String carPartType = carPart.getType();
		
		if(!carParts.containsKey(carPartType)) {
			carPartsByType = new ArrayList<>();
			carParts.put(carPart.getType(), carPartsByType);
		}
		carParts.get(carPartType).add(carPart);
	}

}
