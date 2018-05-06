package server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import data.DatabaseManager;
import model.Car;
import model.CarPart;
import model.Pallet;
import shared.CarBase;
import shared.CarPartDTO;
import shared.DismantleStationBase;
import shared.ICar;
import shared.PalletDTO;
import shared.PartBellongsToPalletDTO;

@Path("/server")
public class RemoteDismantleStationBase implements DismantleStationBase {

	private DatabaseManager dismantleStationDAOServer;
	private ArrayList<Car> cars;
	
	public RemoteDismantleStationBase() {
		this.dismantleStationDAOServer = DatabaseManager.getInstance();
		this.cars = new ArrayList<>();
	}
	
	@POST
	@Path("/parts")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response createCarPart(CarPart carPart) {
		if(carPart.getChassisNo() != null && carPart.getType() != null && carPart.getWeight() != 0) {
			CarPartDTO carPartDTO = dismantleStationDAOServer.createCarPart(
					carPart.getChassisNo(), carPart.getWeight(), carPart.getType());
			return carPartDTO == null ? Response.status(400).build() : Response.status(201).entity(carPart).build();
		}
		return Response.status(400).build();
	}
	

	@GET
	@Path("/parts/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response getCarPart( @PathParam("id") String id) {
		int parsedId = Integer.parseInt(id);
		CarPartDTO carPartDTO = dismantleStationDAOServer.readCarPart(parsedId);
		
		return carPartDTO != null ? Response.status(200).entity(new CarPart(parsedId, carPartDTO.getChassisNo(),
							carPartDTO.getWeight(), carPartDTO.getType())).build() : Response.status(404).build();		
	}
	
	@GET
	@Path("/parts")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response getAllCarParts(@DefaultValue("") @QueryParam("chassisNo") String chassisNo, 
			@DefaultValue("") @QueryParam("type") String type, @DefaultValue("") @QueryParam("number") String number,
			@DefaultValue("") @QueryParam("model") String model) throws RemoteException {
		
		Collection<CarPartDTO> carParts = new ArrayList<>();

		if(!model.equals("")) {
			List<String> chassisNumbers = getCarByModel(model);
			for(String chassisNumber : chassisNumbers) {
				Collection<CarPartDTO> partsByModel;
				if(number.equals("")) {
					partsByModel = 
							dismantleStationDAOServer.readCarParts(chassisNumber, type, 0);
				} else {
					partsByModel = 
							dismantleStationDAOServer.readCarParts(chassisNumber, type, Integer.parseInt(number));
				}
				
				carParts = Stream.of(carParts, partsByModel).flatMap(Collection::stream).collect(Collectors.toList());
			}
			
			LinkedList<String> list = new LinkedList<String>();

			for(CarPartDTO carPartDTO: carParts) {
				CarPart carPart = new CarPart(carPartDTO.getId(), 
						carPartDTO.getChassisNo(), carPartDTO.getWeight(), carPartDTO.getType());				
				
				list.add(carPart.toString() + ", palletId: " + getPartPallet(carPart.getId()));
				
				return Response.status(200).entity(list).build();
			}
			
		} else {
			if(number.equals("")) {
				carParts = dismantleStationDAOServer.readCarParts(chassisNo, type, 0);
			} else {
				carParts = dismantleStationDAOServer.readCarParts(chassisNo, type, Integer.parseInt(number));
			}
			
			LinkedList<CarPart> list = new LinkedList<CarPart>();

			for(CarPartDTO carPartDTO: carParts) {
				CarPart carPart = new CarPart(carPartDTO.getId(), 
						carPartDTO.getChassisNo(), carPartDTO.getWeight(), carPartDTO.getType());
				
				list.add(carPart);
			}
			
			return Response.status(200).entity(list).build();

		}
		return Response.status(505).build();
	}
	
	private List<String> getCarByModel(String model) throws RemoteException  {
		Registry registry = LocateRegistry.getRegistry(1099);
		List<String> chassisNumbers = new ArrayList<>();
			try {
				CarBase carRegStation = 
						(CarBase) registry.lookup("CarRegisterStation");

				List<ICar> cars = carRegStation.getCarsByModel(model);
				for(ICar car : cars)
					chassisNumbers.add(car.getChassisNo());
			} catch (NotBoundException e) {
				e.printStackTrace();
			} 
	
		return chassisNumbers;
	}

	@POST
	@Path("/pallets")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public Response createPallet(Pallet pallet) {
		if(pallet.getCapacity() != 0 && pallet.getType() != null) {
			PalletDTO partDTO = dismantleStationDAOServer.createPallet(pallet.getType(), pallet.getCapacity());
			return partDTO == null ? Response.status(400).build() : Response.status(201).entity(pallet).build();
		} 
		return Response.status(400).build();
	}
	
	
	@PUT
	@Path("/pallets")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response assignCarPartsToPallet(CarPart carPart) {
		PalletDTO pallet = dismantleStationDAOServer.assignPartToPallet(carPart.getId());
		return Response.status(200).entity(pallet).build();
	}

	@GET
	@Path("pallets/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response getPallet(@PathParam("id") String id) {
		int parsedId = Integer.parseInt(id);
		PalletDTO palletDTO = dismantleStationDAOServer.readPallet(parsedId);
		
		return palletDTO != null ? 
				Response.status(200).entity(new Pallet(parsedId, palletDTO.getType(),
						palletDTO.getCapacity())).build() : Response.status(404).build();
	}
		
	@GET
	@Path("/pallets")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<Pallet> getAllPallets(@DefaultValue("") @QueryParam("type") String type) {
		Collection<PalletDTO> allPallets = dismantleStationDAOServer.readPallets(type);
		LinkedList<Pallet> list = new LinkedList<Pallet>();
					
		for(PalletDTO palletDTO: allPallets) {
			Pallet pallet = new Pallet(palletDTO.getId(), palletDTO.getType(), palletDTO.getCapacity());
			
			for(CarPartDTO carPartDTO : palletDTO.getCarParts()) {
				pallet.addCarPart(new CarPart(carPartDTO.getId(), carPartDTO.getChassisNo(), 
						carPartDTO.getWeight(), carPartDTO.getType()));
			}

			list.add(pallet);
		}
		
		return list;
	}
	
	private int getPartPallet(int id) {
		PartBellongsToPalletDTO partsInPallet = dismantleStationDAOServer.getPartPallet(id);	
		return partsInPallet == null ? 0 : partsInPallet.getPallet_id();
	}
	
	@POST
	@Path("/cars")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response postNotification(Car car) {
		cars.add(car);
		return Response.status(204).build();
	}
	
	@GET
	@Path("/notifications")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response getNotification() {
		ArrayList<Car> newCars = cars;
		cars.clear();
		return cars.size() > 0 ? Response.status(200).entity(newCars).build() :  Response.status(204).build();
	}

}
