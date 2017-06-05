package rest.dtos;

import java.util.List;
import java.util.stream.Collectors;

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
	public static List<PurchaseRecordDTO> createPurchaseRecords(List<PurchaseRecord> purchaseRecords) {
		return purchaseRecords.stream().map(x -> new PurchaseRecordDTO(x)).collect(Collectors.toList());
	}

}
