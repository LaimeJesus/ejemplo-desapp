package rest.dtos;

import org.joda.time.DateTime;

import model.registers.PurchaseRecord;

public class RecordDTO {

	DateTime date;
	String listname;

	public RecordDTO(PurchaseRecord pr) {
		date = pr.getPurchasingDate();
		listname = pr.getPurchasingList().getName();		
	}

}
