package model.offers;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import exceptions.MoneyCannotSubstractException;
import model.products.ProductList;
import util.Entity;
import util.Money;

public abstract class Offer extends Entity {

	/**
	 *
	 */
	private static final long serialVersionUID = 846644012300118458L;
	private Integer discountRate;
	@SuppressWarnings("unused")
	private Interval validPeriod;
	private DateTime startDate;
	private DateTime endDate;

	public Offer(Integer aDiscountRate , Interval anInterval) {
		this.setDiscountRate(aDiscountRate);
		this.setValidPeriod(anInterval);
	}

	public abstract Money getPreviousPrice(ProductList productListToGetPrice);

	protected abstract boolean verifyProductRequirements(ProductList productListToVerify);


	public abstract boolean isEqualsToMe(Offer offer);
	
	public Boolean meetRequirements(ProductList productListToVerify){
		return (this.verifyProductRequirements(productListToVerify) &&
				this.verifyOfferRequiremets(productListToVerify) &&
				this.verifyPeriodRequirements());
	}

	private boolean verifyPeriodRequirements() {
		DateTime today = DateTime.now();
		return this.getValidPeriod().contains(today);
	}

	protected boolean verifyOfferRequiremets(ProductList productListToVerify) {
		for (Offer possible : productListToVerify.getAppliedOffers()) {
			if (this.isEqualsToMe(possible)) {
				return false;
			}
		}
		return true;
	}

	public Money getFinalPrice(ProductList productListToGetPrice , Money totalAmount ) {
		try {
			return totalAmount.minus(this.getDiscount(discountRate , productListToGetPrice));
		} catch (MoneyCannotSubstractException e) {
			e.printStackTrace();
			return new Money(0,0);
		}
	}

	public Money getDiscount(Integer discountRate , ProductList productListToGetPrice) {
		return this.getPreviousPrice(productListToGetPrice).percentage(discountRate);
	}



	public Offer() {

	}

	public Integer getDiscountRate() {
		return this.discountRate;
	}

	public void setDiscountRate(Integer newDiscountRate) {
		this.discountRate = newDiscountRate;
	}

	public Interval getValidPeriod() {
		return new Interval(this.getStartDate(),this.getFinalDate());
	}

	public void setValidPeriod(Interval validPeriod) {
		this.setStartDate(validPeriod.getStart());
		this.setEndDate(validPeriod.getEnd());
	}
	
	public DateTime getStartDate() {
		return this.startDate;
	}
	
	public DateTime getFinalDate() {
		return this.endDate;
	}
	
	public void setStartDate(DateTime start) {
		this.startDate = start;
	}
	
	public void setEndDate(DateTime end) {
		this.endDate = end;
	}
	
}
