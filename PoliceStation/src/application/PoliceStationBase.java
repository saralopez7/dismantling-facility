package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import model.CarPart;
import model.Product;
import shared.CarBase;
import shared.ICar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Path("/server")
public class PoliceStationBase implements IPoliceStationBase {
	
	@GET
	@Path("/CarRegisterStation/{chassisNo}")	
	@Override
	public ICar getCarByChassisNo(@PathParam("chassisNo") String chassisNo) throws RemoteException, SQLException {
		Registry registry = LocateRegistry.getRegistry(1099);
		ICar car = null;
			try {
				CarBase carRegStation = 
						(CarBase) registry.lookup("CarRegisterStation");

				car = carRegStation.getCar(chassisNo);
			} catch (NotBoundException e) {
				e.printStackTrace();
			} 
	
		return car;
	}

	@GET
	@Path("/DismantleStation/{chassisNo}")	
	@Override
	public List<CarPart> getCarPartsByChassisNo(@PathParam("chassisNo") String chassisNo)
			throws MalformedURLException, IOException {
		
		URL url = new URL("http://localhost:8080/DismantleStation/server/parts?chassisNo=" + chassisNo);
		List<CarPart> carParts = new ArrayList<>();
		
		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			 //Get Response  
		    InputStream inputStream = connection.getInputStream();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		    StringBuilder response = new StringBuilder(); 
		    String line;
		    
		    while ((line = reader.readLine()) != null) {
		        response.append(line);
		        response.append('\r');
		    }
		    reader.close();
		      
		    Gson gson = new Gson();
		    Type carPartListType = new TypeToken<List<CarPart>>(){}.getType();
		    carParts = gson.fromJson(response.toString(), carPartListType);  
		      		  		      
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return carParts;
	}

	@GET
	@Path("/PackingStation/{chassisNo}")	
	@Override
	public List<Product> getProductsWithStolenParts(@PathParam("chassisNo") String chassisNo) 
			throws MalformedURLException, IOException {
		
		// TODO: Use it to request products with the given parts
		List<CarPart> stolenCarParts = getCarPartsByChassisNo(chassisNo); 
		URL url = new URL("");
		List<Product> products = new ArrayList<>();
		
		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			 //Get Response  
		    InputStream inputStream = connection.getInputStream();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		    StringBuilder response = new StringBuilder(); 
		    String line;
		    
		    while ((line = reader.readLine()) != null) {
		        response.append(line);
		        response.append('\r');
		    }
		    reader.close();
		      
		    Gson gson = new Gson();
		    Type productListType = new TypeToken<List<Product>>(){}.getType();
		    products = gson.fromJson(response.toString(), productListType);  
		      		  		      
		} catch (Exception e) {
			e.printStackTrace();
		}

		return products;
	}

}
