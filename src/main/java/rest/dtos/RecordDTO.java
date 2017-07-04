package rest.dtos;

import model.registers.PurchaseRecord;
import rest.dtos.generics.DateDTO;

public class RecordDTO {

	
	public String listname;
	public String name;
	public DateDTO date;

	public RecordDTO(PurchaseRecord pr) {
		date = new DateDTO(pr.getPurchasingDate());
		listname = pr.getName();		
	}

}
