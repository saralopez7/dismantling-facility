package shared;

public class CarPartDTO {
	
	private int id;
	private double weight;
	private String chassisNo;
	private String type;
	
	/**
	 * Used for GET requests.
	 * @param id
	 * @param chassisNo
	 * @param weight
	 * @param type
	 */
	public CarPartDTO(int id, String chassisNo, double weight, String type) {
		this.id = id;
		this.weight = weight;
		this.type = type;
		this.chassisNo = chassisNo;
	}

	/**
	 * Used for POST and PUT requests.
	 * @param chassisNo
	 * @param weight
	 * @param type
	 */
	public CarPartDTO(String chassisNo, double weight, String type) {
		this.weight = weight;
		this.type = type;
		this.chassisNo = chassisNo;
	}
	
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

}
