package shared;

import java.util.Collection;

public interface PalletDAO {
	PalletDTO createPallet(String type, double capacity);
	Collection<PalletDTO> readPallets(String type);
	void updatePallet(PalletDTO pallet);
	PalletDTO readPallet(int id);
	PalletDTO assignPartToPallet(int id);
}
