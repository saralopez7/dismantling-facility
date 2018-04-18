import java.util.Collection;

public class Pallet {

	private int id;

	private String type;

	private double capacity;

	private Collection<CarPart> carPart;

	private Collection<Product> product;

	public int getId() {
		return id;
	}

	public double getCapacity() {
		return capacity;
	}

	public String getType() {
		return type;
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

}
