package rest.dtos;

import model.registers.PurchaseRecord;

public class RecordDTO {

	public String date;
	public String listname;

	public RecordDTO(PurchaseRecord pr) {
		date = pr.getPurchasingDate().toString();
		listname = pr.getPurchasingList().getName();		
	}

}
