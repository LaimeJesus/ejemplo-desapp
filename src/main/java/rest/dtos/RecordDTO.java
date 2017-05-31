package rest.dtos;

import org.joda.time.DateTime;

import model.registers.PurchaseRecord;

public class RecordDTO {

	
	public String listname;
	public DateTime date;

	public RecordDTO(PurchaseRecord pr) {
		date = pr.getPurchasingDate();
		listname = pr.getPurchasingList().getName();		
	}

}
