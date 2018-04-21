package model;

import java.util.ArrayList;

public class System implements Product {

	private String carModel;
	private int id;
	private ArrayList<CarPart> carParts;
	
	public System(String carModel, ArrayList<CarPart> carParts) {
		this.carModel = carModel;
		this.carParts = carParts;
	}

	/**
	 * Needed deserializing object from entity stream on HTTP request
	 */
	public System() {}
	
	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public ArrayList<CarPart> getCarParts(){
		return carParts;
	}
	
	@Override
	public void setCarParts(ArrayList<CarPart> carParts) {
		this.carParts = carParts;
	}
}
