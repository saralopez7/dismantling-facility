package shared;

import java.rmi.Remote;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface CarBase extends Remote {
	ICar create(String chassisNo, String model, double weight) throws RemoteException, SQLException, Exception;
	ICar getCar(String chassisNo) throws RemoteException, SQLException;
	List<ICar> getCarsByModel(String model) throws RemoteException;
	List<ICar> getAllCars() throws RemoteException;
	void deleteCar(String chassisNo) throws RemoteException;
}
