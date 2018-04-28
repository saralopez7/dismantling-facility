package application;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import data.DatabaseManager;

public class RegisterStationServer {
	public static void main(String[] args) throws RemoteException, AlreadyBoundException, ClassNotFoundException {
		
		RemoteCarBase registerStation = new RemoteCarBase();
		Remote skeleton = UnicastRemoteObject.exportObject(registerStation, 8085);
		Registry registry = LocateRegistry.createRegistry(1099);
		registry.rebind("CarRegisterStation", skeleton);
		
		// Register CarDAO
		DatabaseManager carDAOServer = new DatabaseManager();
		registry.rebind("carDao", carDAOServer);
		
		System.out.println("Register Station server bound");
	}
}
