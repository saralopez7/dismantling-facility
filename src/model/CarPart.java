package model;
public class CarPart {

	private int id;
	private double weight;
	private String chassisNo;
	private String type;
	
	public CarPart(int id, String chassisNo, double weight, String type) {
		this.id = id;
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
