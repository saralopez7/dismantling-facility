package application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;


import model.CarPart;
import model.Product;
import shared.ICar;

public interface IPoliceStationBase {

	ICar getCarByChassisNo(String chassisNo) throws RemoteException, SQLException;

	List<CarPart> getCarPartsByChassisNo(String chassisNo) throws MalformedURLException, IOException;

	List<Product> getProductsWithStolenParts(String chassisNo) throws MalformedURLException, IOException;

}
