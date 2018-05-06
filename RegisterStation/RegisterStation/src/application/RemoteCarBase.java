package application;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import shared.CarBase;
import shared.CarDTO;
import shared.ICar;

public class RemoteCarBase implements CarBase {
	
	private Map<String, RemoteCar> cars = new HashMap<>();

	@Override
	public ICar create(String chassisNo, String model, double weight) throws RemoteException, Exception {
		CarDTO carDTO = DAOLocator.getDAO().create(chassisNo, model, weight);
		RemoteCar car = new RemoteCar(carDTO);
		cars.put(chassisNo, car);
		return car;	
	}

	@Override
	public ICar getCar(String chassisNo) throws RemoteException, SQLException {
		CarDTO car = DAOLocator.getDAO().getCar(chassisNo);
		if(car != null ) {
			if (!cars.containsKey(chassisNo)) {
				cars.put(chassisNo, new RemoteCar(car));
			}
			return cars.get(chassisNo);
		}
		
		return null;
	}

	@Override
	public List<ICar> getAllCars() throws RemoteException {
		Collection<CarDTO> allCars = DAOLocator.getDAO().getAllCars();
		LinkedList<ICar> list = new LinkedList<ICar>();
		for(CarDTO car : allCars) {
			if (!cars.containsKey(car.getChassisNo()))
				cars.put(car.getChassisNo(), new RemoteCar(car));
			
			list.add(cars.get(car.getChassisNo()));
		}
		
		return list;
	}

	@Override
	public void deleteCar(String chassisNo) throws RemoteException {
		DAOLocator.getDAO().deleteCar(chassisNo);
	}
	
	@Override
	public List<ICar> getCarsByModel(String model) throws RemoteException {
		Collection<CarDTO> allCars = DAOLocator.getDAO().getAllCars();
		LinkedList<ICar> list = new LinkedList<ICar>();
		for(CarDTO car : allCars) {
			if (!cars.containsKey(car.getChassisNo()))
				cars.put(car.getChassisNo(), new RemoteCar(car));
			
			if(car.getModel().equals(model))
				list.add(cars.get(car.getChassisNo()));
		}
		
		return list;
	}
	
}
