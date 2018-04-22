package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import shared.CarPartDAO;
import shared.CarPartDTO;
import shared.PalletDAO;
import shared.PalletDTO;
import shared.PartBellongsToPalletDTO;

public class DatabaseManager implements CarPartDAO, PalletDAO {

	private DatabaseHelper<CarPartDTO> carPartHelper;
	private DatabaseHelper<PalletDTO> palletHelper;
	private DatabaseHelper<PartBellongsToPalletDTO> partsInPalletHelper;

	private static final double DEFAULT_CAPACITY = 1000.56;
	private static final DatabaseManager instance = new DatabaseManager();
	
	public DatabaseManager() {
		carPartHelper = new DatabaseHelper<>("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
		palletHelper = new DatabaseHelper<>("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
		partsInPalletHelper = new DatabaseHelper<>("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
	}
	
	public static DatabaseManager getInstance() {
		return instance;
	}
	
	private CarPartDTO createCarPart(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		String chassisNo = rs.getString("chassisNo");
		double weight = rs.getDouble("weight");
		String type = rs.getString("type");
		return new CarPartDTO(id, chassisNo, weight, type);
	}

	/**
	 * @return null if no rows have been inserted something went wrong
	 * @return new CarPartDTO if rows have been inserted
	 */
	@Override
	public CarPartDTO createCarPart(String chassisNo, double weight, String type) {
		int rows = carPartHelper.executeUpdate("INSERT INTO car_part "
				+ "(chassisNo, weight, type) VALUES (?, ?, ?)", chassisNo, weight, type);
		
		return (rows == 0) ? null : new CarPartDTO(chassisNo, weight, type);
	}

	@Override
	public Collection<CarPartDTO> readCarParts(String chassisNo, String type) {
		if(!chassisNo.equals("") && type.equals("")) {
			return carPartHelper.map((rs) -> createCarPart(rs), "SELECT * FROM car_part where chassisNo = ?", chassisNo);
		} else if(chassisNo.equals("") && !type.equals("")) {
			return carPartHelper.map((rs) -> createCarPart(rs), "SELECT * FROM car_part where type = ?", type);
		} else if(!chassisNo.equals("") && !type.equals("")) {
			return carPartHelper.map((rs) -> createCarPart(rs), "SELECT * FROM car_part where type = ? AND chassisNo = ?",
					type, chassisNo);
		}
		return carPartHelper.map((rs) -> createCarPart(rs), "SELECT * FROM car_part");
	}

	@Override
	public void updateCarPart(CarPartDTO carPart) {
		carPartHelper.executeUpdate("UPDATE car_part SET chassisNo=?, weight=?, type=? WHERE id = ?", 
				carPart.getType(), carPart.getWeight(), carPart.getId());		
	}
	
	/**
	 * Helper method used for reading carParts by id from the database. 
	 * @param id
	 * @return
	 */
	@Override
	public CarPartDTO readCarPart(int id) {
		return carPartHelper.mapSingle((rs) -> createCarPart(rs), "SELECT * FROM car_part where id = ?", id);
	}
	
	/**
	 * @return null if no rows have been inserted something went wrong
	 * @return new PalletDTO if rows have been inserted
	 */
	@Override
	public PalletDTO createPallet(String type, double capacity) {
		int rows = palletHelper.executeUpdate("INSERT INTO pallet (type, capacity) VALUES (?, ?)", type, capacity);
		return (rows == 0) ? null : new PalletDTO(type, capacity); 
	}
	
	private PalletDTO createPallet(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		String type = rs.getString("type");
		double capacity = rs.getDouble("capacity");
		return new PalletDTO(id, type, capacity);
	}
	
	private PartBellongsToPalletDTO createpartBelongsToPallet(ResultSet rs) throws SQLException {
		int part_id = rs.getInt("part_id");
		int pallet_id = rs.getInt("pallet_id");
		return new PartBellongsToPalletDTO(pallet_id, part_id);
	}
	
	@Override
	public Collection<PalletDTO> readPallets(String type) {
		Collection<PalletDTO> pallets;
		
		if(!type.equals("")) {
			pallets = palletHelper.map((rs) -> createPallet(rs), "SELECT * FROM pallet WHERE type =?", type);
		} else {
			pallets = palletHelper.map((rs) -> createPallet(rs), "SELECT * FROM pallet");
		}
		
		Collection<PartBellongsToPalletDTO> partsInPallet = 
				partsInPalletHelper.map((rs) -> createpartBelongsToPallet(rs), "SELECT * FROM part_belongs_to_pallet");

		for(PalletDTO pallet : pallets) {
			for(PartBellongsToPalletDTO partInPallet : partsInPallet) {
				if(pallet.getId() == partInPallet.getPallet_id()) {
					pallet.addCarPart(readCarPart(partInPallet.getPart_id()));
				}
			}
		}
		return pallets;
	}

	@Override
	public void updatePallet(PalletDTO pallet) {
		palletHelper.executeUpdate("UPDATE pallet SET type=?, capacity=? WHERE id = ?", 
				pallet.getType(), pallet.getCapacity(), pallet.getId());
	}

	@Override
	public PalletDTO readPallet(int id) {
		return palletHelper.mapSingle((rs) -> createPallet(rs), "SELECT * FROM pallet where id = ?", id);
	}

	/**
	 * Helper method used to assign parts to pallets
	 * @param type
	 * @return pallet with the given type and capacity bigger than 0
	 */
	private PalletDTO readPalletByType(String type) {
		return palletHelper.mapSingle((rs) -> createPallet(rs), "SELECT * FROM pallet where type = ? AND capacity > 0", type);
	}

	@Override
	public PalletDTO assignPartToPallet(int id) {
		CarPartDTO carPart = readCarPart(id);
		PalletDTO pallet = readPalletByType(carPart.getType());
		if(pallet == null)
			pallet = createPallet(carPart.getType(), DEFAULT_CAPACITY); 
		
		palletHelper.executeUpdate("INSERT INTO part_belongs_to_pallet (part_id, pallet_id) VALUES (?, ?)",
				carPart.getId(), pallet.getId());
		double currentCapacity = pallet.getCapacity() - carPart.getWeight();
		palletHelper.executeUpdate("UPDATE pallet SET capacity=? WHERE id = ?", 
				currentCapacity, pallet.getId());	// update pallet capacity after inserting a car part.
		return pallet;
	}

}
