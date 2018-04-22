package model;

import java.util.ArrayList;

public interface Product {

	int getId();
	void setId(int id);
	ArrayList<CarPart> getCarParts();
	void setCarParts(ArrayList<CarPart> carParts);
	
}
