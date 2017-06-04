package rest.dtos;

import model.products.ProductList;
import model.registers.PurchaseRecord;
import rest.dtos.offers.DateDTO;

public class PurchaseRecordDTO {

	public ProductList productlist;
	public DateDTO purchaseDate;
	public PurchaseRecordDTO(){
		
	}
	public PurchaseRecordDTO(PurchaseRecord purchaseRecord) {
		productlist = purchaseRecord.getPurchasingList();
		purchaseDate = new DateDTO(purchaseRecord.getPurchasingDate());
	}

}
