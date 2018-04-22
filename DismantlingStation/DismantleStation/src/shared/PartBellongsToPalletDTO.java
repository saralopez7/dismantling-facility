package shared;

public class PartBellongsToPalletDTO {

	private int pallet_id;
	private int part_id;
	
	public PartBellongsToPalletDTO(int pallet_id, int part_id) {
		this.pallet_id = pallet_id;
		this.part_id = part_id;
	}

	public int getPallet_id() {
		return pallet_id;
	}

	public void setPallet_id(int pallet_id) {
		this.pallet_id = pallet_id;
	}

	public int getPart_id() {
		return part_id;
	}

	public void setPart_id(int part_id) {
		this.part_id = part_id;
	}
}
