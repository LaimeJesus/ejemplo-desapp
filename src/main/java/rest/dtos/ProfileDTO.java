package rest.dtos;

import java.util.ArrayList;
import java.util.List;

import model.registers.PurchaseRecord;
import model.users.Profile;

public class ProfileDTO {

	String address;
	List<RecordDTO> records;

	public ProfileDTO(Profile profile) {
		records = new ArrayList<RecordDTO>();
		for(PurchaseRecord pr : profile.getPurchaseRecords()){
			records.add(new RecordDTO(pr));
		}
		address = profile.getAddress().getAddress();
	}

}
