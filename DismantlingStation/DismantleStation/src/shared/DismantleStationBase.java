package shared;

import java.rmi.RemoteException;
import java.util.List;

import javax.ws.rs.core.Response;

import model.Car;
import model.CarPart;
import model.Pallet;

public interface DismantleStationBase {
	
	/**
	 * To Create a car part we need to specify the following parameters in the body of the POST request: 
	 * ChassisNo, weight, type
	 * @param carPart
	 * @return Response with the car part created. 
	 */
	Response createCarPart(CarPart carPart);

	/**
	 * Get Car Part by id. 
	 * @param id - mandatory value
	 * REQUEST FORMAT:
	 	* Query for a part with id = 1:	http://localhost:8080/DismantleStation/server/parts/1
	 * @return car part with id matching the parameter value
	 */
	Response getCarPart(String id);
	
	/**
	 * @param chassisNo - optional value. Query for parts by chassisNo
	 * @param type - optional value. Query for parts by type
	 * @param number - optional value. Limit the number of parts returned.
	 * @param model - optional value. Get parts by car model. 
	 * REQUEST FORMAT:
	 	 * Query for a part by type: http://localhost:8080/DismantleStation/server/parts?type=...
		 * Query for a part by chasssisNo : 
		 * 							http://localhost:8080/DismantleStation/server/parts?chassisNo=...
		 * Query for a part by chasssisNo and type:
		 * 			http://localhost:8080/DismantleStation/server/parts?chassisNo=...&type=...
		 * Get all car parts: http://localhost:8080/DismantleStation/server/parts
	 * @return list of car parts.
	 */
	Response getAllCarParts(String chassisNo, String type, String number, String model) throws RemoteException ;

	
	/**
	 * To Create a pallet we need to specify the following parameters in the body of the POST request: 
	 * weight, type
	 * @param carPart
	 * @return Response with the pallet created.
	 */
	Response createPallet(Pallet pallet);
	
	/**
	 * Assign a car part to a pallet.
	 * To assign a car part we only need to specify only the id in the body of the POST request.
	 * NOTE: We can specify all the carPart parameters in the body of the PUT request but only the id will be used. 
	 * @param carPart - only its id.
	 * @return Response. 
	 */
	Response assignCarPartsToPallet(CarPart carPart);
	
	/**
	 * Get Pallet by id. 
	 * @param id - mandatory value
	 * REQUEST FORMAT:
	 	* Query for a pallet with id = 1:	http://localhost:8080/DismantleStation/server/parts/1
	 * @return car pallet with id matching the parameter value
	 */
	Response getPallet(String id);
	
	/**
	 * @param type - optional value. Query for pallets by type
	 * REQUEST FORMAT:
	 	 * Query for a pallets by type: http://localhost:8080/DismantleStation/server/pallets?type=...
		 * Get all pallets: http://localhost:8080/DismantleStation/server/pallets
	 * @return list of car parts.
	 */
	List<Pallet> getAllPallets(String type);
	
	Response getNotification();
	
	Response postNotification(Car car);

}
