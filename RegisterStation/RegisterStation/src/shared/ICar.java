package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICar extends Remote {
	String getChassisNo() throws RemoteException;
	String getModel() throws RemoteException;
	double getWeight() throws RemoteException;
	String printCar() throws RemoteException;
}
