import java.util.Collection;

public class Car {

	private double weight;

	private String chassisNo;

	private String model;

	private Collection<CarPart> carPart;

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
