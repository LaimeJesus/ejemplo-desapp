package model.registers;

import org.joda.time.DateTime;

import model.products.ProductList;
import util.Entity;
import util.Money;

public class PurchaseRecord extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -616092953405797730L;
	private DateTime purchasingDate;
//	private ProductList purchasingList;
	private String name = "";
	private Money totalAmount = new Money(0,0);

	public PurchaseRecord(){
		this.setPurchasingDate(DateTime.now());
	}
	
	public PurchaseRecord(ProductList aProductList) {
		this.setPurchasingDate(DateTime.now());
//		this.setPurchasingList(aProductList);
		this.setName(aProductList.getName());
		this.setTotalAmount(aProductList.getMoneyOfProducts());
	}

	public DateTime getPurchasingDate() {
		return this.purchasingDate;
	}

	public void setPurchasingDate(DateTime purchasingDate) {
		this.purchasingDate = purchasingDate;
	}


	@Override
	public String toString() {
		return this.getName() + " - " + this.getPurchasingDate().toString();
	}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Money getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Money totalAmount) {
    this.totalAmount = totalAmount;
  }
	
}
