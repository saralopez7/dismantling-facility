package model;

import java.util.List;

public class Car {

	private double weight;
	private String chassisNo;
	private String model;
	private List<CarPart> carParts;

	public Car(String chassisNo, String model, double weight, List<CarPart> carParts) {
		this.chassisNo = chassisNo;
		this.model = model;
		this.weight = weight;
		this.carParts = carParts;
	}
	
	public double getWeight() {
		return weight;
	}

	public String getChassisNo() {
		return chassisNo;
	}

	public String getModel() {
		return model;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public void setChassisNo(String chassisNo) {
		this.chassisNo = chassisNo;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	public List<CarPart> getCarParts(){
		return carParts;
	}
	
	public void setCarParts(List<CarPart> carParts) {
		this.carParts = carParts;
	}
}
