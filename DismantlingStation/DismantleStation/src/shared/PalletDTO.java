package shared;

import java.util.ArrayList;
import java.util.List;

public class PalletDTO {

	private int id;
	private String type;
	private double capacity;
	private List<CarPartDTO> carParts;
	
	/**
	 * Used for GET, POST and PUT requests.
	 * @param id
	 * @param type
	 * @param capacity
	 */
	public PalletDTO(int id, String type, double capacity) {
		this.id = id;
		this.type = type;
		this.capacity = capacity;
		this.carParts = new ArrayList<CarPartDTO>();
	}
	
	/**
	 * Used for POST requests.
	 * @param type
	 * @param capacity
	 */
	public PalletDTO(String type, double capacity) {
		this.type = type;
		this.capacity = capacity;
		this.carParts = new ArrayList<CarPartDTO>();
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

	public List<CarPartDTO> getCarParts() {
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
	public void addCarPart(CarPartDTO carPart) {
		carParts.add(carPart);
	}
	
}