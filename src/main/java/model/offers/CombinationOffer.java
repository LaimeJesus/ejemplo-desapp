package model.offers;

import org.joda.time.Interval;

import model.products.Product;
import model.products.ProductList;
import util.Money;

public class CombinationOffer extends Offer {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -4616900153311628958L;
	private Product relatedProduct;
	private Product combinatedProduct;
		
	public CombinationOffer(Product aRelatedProduct , Product aCombinateProduct , Integer aDiscountRate , Interval validPeriod) {
		super(aDiscountRate, validPeriod);
		this.setRelatedProduct(aRelatedProduct);
		this.setCombinatedProduct(aCombinateProduct);
	}

	@Override
	public Money getPreviousPrice(ProductList productListToGetPrice) {
		return this.getRelatedProduct().getPrice().add(this.getCombinatedProduct().getPrice());
	}
	
	@Override
	protected boolean verifyProductRequirements(ProductList productListToVerify) {
		return productListToVerify.thisProductIsSelected(this.getRelatedProduct()) &&
			   productListToVerify.thisProductIsSelected(this.getCombinatedProduct());	
	}

	
	public CombinationOffer() {
		super();
	}
	
	public Product getRelatedProduct() {
		return this.relatedProduct;
	}
	
	public void setRelatedProduct(Product newRelatedProduct) {
		this.relatedProduct = newRelatedProduct;
	}
	
	public Product getCombinatedProduct() {
		return this.combinatedProduct;
	}
	
	public void setCombinatedProduct(Product aNewCombinatedProduct) {
		this.combinatedProduct = aNewCombinatedProduct;
	}

	@Override
	public boolean equals(Object anotherPossibleOffer ) {
		if(this.isMyType(anotherPossibleOffer)){
			CombinationOffer newOffer = (CombinationOffer) anotherPossibleOffer;
			return this.totalEquals(newOffer);
		}
		return false;
	}
	
	private boolean isMyType(Object anotherPossibleOffer) {
		return anotherPossibleOffer != null && anotherPossibleOffer instanceof CombinationOffer;
	}

	public boolean totalEquals( CombinationOffer someOffer ) {
		return
			this.getDiscountRate().equals(someOffer.getDiscountRate()) &&
			this.getValidPeriod().equals(someOffer.getValidPeriod()) &&
			this.getRelatedProduct().equals(someOffer.getRelatedProduct()) &&
			this.getCombinatedProduct().equals(someOffer.getCombinatedProduct());
	}

}
