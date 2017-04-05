package abstracts;

import exceptions.MoneyCannotSubstractException;
import model.ProductList;
import util.Money;

public abstract class Offer {

	private Integer discountRate;
	
	//private period
	
	
	
	
	public Offer(Integer aDiscountRate) {
		this.setDiscountRate(aDiscountRate);
	}
	
	
	
	
	
	public abstract Money getPreviousPrice();
	
	public Boolean meetRequirements(ProductList productListToVerify){
		return (this.verifyProductRequirements(productListToVerify) &&
				this.verifyOfferRequiremets(productListToVerify) &&
				this.verifyPeriodRequirements(productListToVerify));
	}
	
	private boolean verifyPeriodRequirements(ProductList productListToVerify) {
		return true;
		//TODO
	}

	private boolean verifyOfferRequiremets(ProductList productListToVerify) {
		return productListToVerify.isApplicable(this);
	}

	protected abstract boolean verifyProductRequirements(ProductList productListToVerify);
	
	public void applyOffer(ProductList productListToApply) {
		productListToApply.applyOffer(this);
	}
	
	public Money getFinalPrice() throws MoneyCannotSubstractException {
		return this.getPreviousPrice().minus(this.getDiscount(discountRate));
	}

	public Money getDiscount(Integer discountRate) {
		return this.getPreviousPrice().percentage(discountRate);
	}
	
	
	
	
	
	
	
	public Integer getDiscountRate() {
		return this.discountRate;
	}
	
	public void setDiscountRate(Integer newDiscountRate) {
		this.discountRate = newDiscountRate;
	}
}
