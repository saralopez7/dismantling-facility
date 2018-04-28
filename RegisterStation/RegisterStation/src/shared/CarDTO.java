package shared;

import java.io.Serializable;


public class CarDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private double weight;
	private String chassisNo;
	private String model;
	
	public CarDTO(String chassisNo, String model, double weight){
		this.chassisNo = chassisNo;
		this.model = model;
		this.weight = weight;
		
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
}
