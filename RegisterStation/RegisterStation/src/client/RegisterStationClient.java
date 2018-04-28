package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

import shared.CarBase;
import shared.ICar;

public class RegisterStationClient {

	static CarBase carRegStation;
	
	public static void main(String[] args) throws Exception {
		
		Registry registry = LocateRegistry.getRegistry(1099);
		carRegStation = (CarBase) registry.lookup("CarRegisterStation");
		boolean running = true;

		//Station Logic
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Welcome to the Register Station. Choose an option:");

		while(running) {
			System.out.println("\n1) Register a Car. \n");
			System.out.println("2) Read Car by Chassis number. \n");
			System.out.println("3) Read all cars. \n");
			System.out.println("4) Quit. \n");

			String option = keyboard.next();
			
			switch(option) {
				case "1":
					System.out.println("Enter the car's chassis number: \n");
					String chassisNo = keyboard.next();
					
					System.out.println("Enter the car's model: \n");
					String model = keyboard.next();
					
					System.out.println("Enter the car's weight: \n");
					double weight = keyboard.nextDouble();
					
					carRegStation.create(chassisNo, model, weight);
					
					System.out.println("The following car has been added to the Database: ");
					
					carRegStation.getCar(chassisNo).toString();
					break;
					
				case "2":
					System.out.println("Enter the car's chassis number: \n");
					chassisNo = keyboard.next();
	
					ICar foundCar = carRegStation.getCar(chassisNo);
					if(foundCar == null)
						System.out.println("No car found with chassisNo:" + chassisNo);
					else
						System.out.println(foundCar.printCar());
					break;
					
				case "3":
					List<ICar> cars = carRegStation.getAllCars();
					for(ICar car : cars)
						System.out.println(car.printCar());
					break;
					
				case "4":
					running = false;
					break;
					
				default:
					System.out.println("Wrong choice. \n");
					break;
			}
			
		}
		
		keyboard.close();
	}
	
}
