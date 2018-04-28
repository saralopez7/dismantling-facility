package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface CarDAO extends Remote {
	CarDTO create(String chassisNo, String model, double weight) throws RemoteException, SQLException, Exception;
	CarDTO getCar(String chassisNo) throws RemoteException, SQLException;
	List<CarDTO> getAllCars() throws RemoteException;
	void deleteCar(String chassisNo) throws RemoteException;
}
