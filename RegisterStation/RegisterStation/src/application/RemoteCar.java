package application;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import shared.CarDTO;
import shared.ICar;

public class RemoteCar extends UnicastRemoteObject implements ICar {
	
	private static final long serialVersionUID = 1L;
	private String chassisNo;
	private String model;
	private double weight;
	
	public RemoteCar(String chassisNo, String model, double weight) throws RemoteException {
		this.chassisNo = chassisNo;
		this.model = model;
		this.weight = weight;
	}
	
	public RemoteCar(CarDTO car) throws RemoteException {
		this(car.getChassisNo(), car.getModel(), car.getWeight());
	}
	
	@Override
	public String getChassisNo() {
		return chassisNo;
	}

	@Override
	public String getModel() {
		return model;
	}

	
	@Override
	public double getWeight() throws RemoteException {
		return weight;
	}
	
	public String printCar() throws RemoteException {
		return "RemoteCar [chassisNo=" + chassisNo + ", model=" + model + ", weight=" + weight + "]";
	}

}
