package client;

import static org.junit.Assert.assertEquals;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shared.CarBase;
import shared.ICar;

public class RegisterStationClientTest {

	private CarBase carRegStation;
	private static final String CHASSIS_NO = "762837493DA";
	
	@Before
	public void setUp() throws RemoteException, NotBoundException, SQLException {
		Registry registry = LocateRegistry.getRegistry(1099);
		carRegStation = (CarBase) registry.lookup("CarRegisterStation");
	}
	
	@After
	public void tearDown() throws RemoteException {
		carRegStation.deleteCar(CHASSIS_NO);
	}
	
	@Test
	public void testRegisterCar() throws RemoteException, SQLException, Exception {
		carRegStation.create(CHASSIS_NO, "Mercedes", 2434.45);
		
		List<ICar> cars = carRegStation.getAllCars();
		
		assertEquals(5, cars.size());
		assertEquals("762837493DA      ", cars.get(4).getChassisNo());
		assertEquals("Mercedes", cars.get(4).getModel());
	}
	
	@Test
	public void testGetCar() throws SQLException, Exception {
		ICar car = carRegStation.getCar("123456789AD      ");
				
		assertEquals("123456789AD      ", car.getChassisNo());
		assertEquals(1,243.12, car.getWeight());
		assertEquals("Mercedes", car.getModel());

	}

}
