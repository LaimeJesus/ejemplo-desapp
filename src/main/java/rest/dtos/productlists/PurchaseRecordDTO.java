package rest.dtos.productlists;

import java.util.List;
import java.util.stream.Collectors;

import model.registers.PurchaseRecord;
import rest.dtos.generics.DateDTO;

public class PurchaseRecordDTO {
	public int id;
	public ProductListDTO productlist;
	public DateDTO purchaseDate;
	public PurchaseRecordDTO(){
		
	}
	public PurchaseRecordDTO(PurchaseRecord purchaseRecord) {
		productlist = new ProductListDTO(purchaseRecord.getPurchasingList());
		purchaseDate = new DateDTO(purchaseRecord.getPurchasingDate());
	}
	public static List<PurchaseRecordDTO> createPurchaseRecords(List<PurchaseRecord> purchaseRecords) {
		return purchaseRecords.stream().map(x -> new PurchaseRecordDTO(x)).collect(Collectors.toList());
	}

}
