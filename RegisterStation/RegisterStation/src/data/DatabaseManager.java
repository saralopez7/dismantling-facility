package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import shared.CarDAO;
import shared.CarDTO;

public class DatabaseManager extends UnicastRemoteObject implements CarDAO {
	
	private static final long serialVersionUID = 1;
	private DatabaseHelper<CarDTO> helper;

	public DatabaseManager() throws RemoteException, ClassNotFoundException {
		try {
			helper = new DatabaseHelper<CarDTO>();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public CarDTO create(String chassisNo, String model, double weight) throws SQLException, Exception {
		
		try {
			helper.addCar(chassisNo, model, weight);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new CarDTO(chassisNo, model, weight);
	}
	
	private CarDTO createCar(ResultSet rs) throws SQLException{
		String chassisNo = rs.getString("chassisNo");
		String model = rs.getString("model");
		double weight = rs.getDouble("weight");
		return new CarDTO(chassisNo, model, weight);
	}

	@Override
	public CarDTO getCar(String chassisNo) throws RemoteException{
		return helper.mapSingle((rs) -> createCar(rs), "SELECT * FROM car where chassisNo = ?",
				chassisNo);
	}

	@Override
	public List<CarDTO> getAllCars() throws RemoteException {
		return helper.map((rs) -> createCar(rs), "SELECT * FROM car");
	}

	@Override
	public void deleteCar(String chassisNo) throws RemoteException {
		try {
			helper.deleteCar(chassisNo);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
}
