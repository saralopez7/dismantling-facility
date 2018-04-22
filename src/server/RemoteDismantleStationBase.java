package server;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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
import model.CarPart;
import model.Pallet;
import shared.CarPartDTO;
import shared.DismantleStationBase;
import shared.PalletDTO;

@Path("/server")
public class RemoteDismantleStationBase implements DismantleStationBase {

	private DatabaseManager dismantleStationDAOServer;
	
	public RemoteDismantleStationBase() {
		this.dismantleStationDAOServer = DatabaseManager.getInstance();
	}
	
	@POST
	@Path("/parts")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response createCarPart(CarPart carPart) {
		CarPartDTO carPartDTO = dismantleStationDAOServer.createCarPart(
				carPart.getChassisNo(), carPart.getWeight(), carPart.getType());

		return carPartDTO == null ? Response.status(400).build() : Response.status(201).entity(carPart).build();
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
	public List<CarPart> getAllCarParts(@DefaultValue("") @QueryParam("chassisNo") String chassisNo, 
			@DefaultValue("") @QueryParam("type") String type) {
		
		Collection<CarPartDTO> carParts = dismantleStationDAOServer.readCarParts(chassisNo, type);
		LinkedList<CarPart> list = new LinkedList<CarPart>();
		
		for(CarPartDTO carPartDTO: carParts) {
			CarPart carPart = new CarPart(carPartDTO.getId(), 
					carPartDTO.getChassisNo(), carPartDTO.getWeight(), carPartDTO.getType());
			
			list.add(carPart);
		}
					
		return list;
	}
	
	@POST
	@Path("/pallets")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response createPallet(Pallet pallet) {
		PalletDTO partDTO = dismantleStationDAOServer.createPallet(pallet.getType(), pallet.getCapacity());
		return partDTO == null ? Response.status(400).build() : Response.status(201).entity(pallet).build();
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

}