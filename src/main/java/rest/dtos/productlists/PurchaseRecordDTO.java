package rest.dtos.productlists;

import java.util.List;
import java.util.stream.Collectors;

import model.registers.PurchaseRecord;
import rest.dtos.generics.DateDTO;
import util.Money;

public class PurchaseRecordDTO {
	public int id;
	public ProductListDTO productlist;
	public DateDTO purchaseDate;
  public String name;
  public Money totalAmount;
	public PurchaseRecordDTO(){
		
	}
	public PurchaseRecordDTO(PurchaseRecord purchaseRecord) {
		id = purchaseRecord.getId();
//		productlist = new ProductListDTO(purchaseRecord.getPurchasingList());
		name = purchaseRecord.getName();
		totalAmount = purchaseRecord.getTotalAmount();
		purchaseDate = new DateDTO(purchaseRecord.getPurchasingDate());
	}
	public static List<PurchaseRecordDTO> createPurchaseRecords(List<PurchaseRecord> purchaseRecords) {
		return purchaseRecords.stream().map(x -> new PurchaseRecordDTO(x)).collect(Collectors.toList());
	}

}
