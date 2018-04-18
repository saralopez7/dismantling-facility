package model;

import java.util.ArrayList;

public class Package implements Product  {
	
	private String type;
	private int id;
	private ArrayList<CarPart> carParts;
	
	public Package(String type, int id, ArrayList<CarPart> carParts) {
		this.type = type;
		this.id = id;
		this.carParts = carParts;
	}

	public String getPartType() {
		return type;
	}

	public void setPartType(String type) {
		this.type = type;
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
