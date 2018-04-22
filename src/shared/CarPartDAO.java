package shared;

import java.util.Collection;

public interface CarPartDAO {
	CarPartDTO createCarPart(String chassisNo, double weight, String type);
	Collection<CarPartDTO> readCarParts(String chassisNo, String type);
	CarPartDTO readCarPart(int id);
	void updateCarPart(CarPartDTO carPart);
}
