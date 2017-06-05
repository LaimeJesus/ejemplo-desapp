package rest.dtos.users;

import java.util.List;

import model.users.Profile;
import rest.dtos.PurchaseRecordDTO;
import util.Address;

public class ProfileDTO {

	public List<PurchaseRecordDTO> purchaseRecords;
	public Address address;

	public ProfileDTO(Profile profile) {
		purchaseRecords = PurchaseRecordDTO.createPurchaseRecords(profile.getPurchaseRecords());
		address = profile.getAddress();
	}

}
