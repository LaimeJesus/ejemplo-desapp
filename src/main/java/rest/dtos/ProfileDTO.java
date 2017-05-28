package rest.dtos;

import java.util.ArrayList;
import java.util.List;

import model.registers.PurchaseRecord;
import model.users.Profile;

public class ProfileDTO {

	public String address;
	public List<RecordDTO> records;

	public ProfileDTO(Profile profile) {
		this.records = new ArrayList<RecordDTO>();
		for(PurchaseRecord pr : profile.getPurchaseRecords()){
			this.records.add(new RecordDTO(pr));
		}
		address = "HARDCODEADO";
	}

}
