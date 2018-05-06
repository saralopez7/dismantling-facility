package model;

public class CarPart {

	private int id;
	private double weight;
	private String chassisNo;
	private String type;
	
	/**
	 * Constructor used on POST request to create a car part.
	 * @param id
	 * @param chassisNo
	 * @param weight
	 * @param type
	 */
	public CarPart(int id, String chassisNo, double weight, String type) {
		this.id = id;
		this.weight = weight;
		this.type = type;
		this.chassisNo = chassisNo;
	}
	
	/**
	 * Constructor used for GET request : Get car part by id.
	 * @param id
	 */
	public CarPart(int id) {
		this.id = id;
	}
	

	/**
	 * Needed deserializing object from entity stream on HTTP request
	 */
	public CarPart() {}
	
	public int getId() {
		return id;
	}

	public double getWeight() {
		return weight;
	}

	public String getChassisNo() {
		return chassisNo;
	}

	public String getType() {
		return type;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public void setChassisNo(String chassisNo) {
		this.chassisNo = chassisNo;
	}

	public void setType(String type) {
		this.type = type;
	}
    
    	@Override
    	public String toString() {
        	return "id: " + id + ", chassisNo: " + chassisNo + ", type: " + type + ", weight: "  + weight;
    	}
}
