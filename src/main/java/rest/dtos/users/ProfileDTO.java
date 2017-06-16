package rest.dtos.users;

import java.util.List;

import model.users.Profile;
import rest.dtos.productlists.ProductListDTO;
import rest.dtos.productlists.PurchaseRecordDTO;
import util.Address;

public class ProfileDTO {
	public int id;
	public List<ProductListDTO> productLists;
	public List<PurchaseRecordDTO> purchaseRecords;
	public Address address;

	public ProfileDTO(Profile profile) {
		id = profile.getId();
		productLists = ProductListDTO.createProductLists(profile.getAllProductList());
		purchaseRecords = PurchaseRecordDTO.createPurchaseRecords(profile.getPurchaseRecords());
		address = profile.getAddress();
	}

}
