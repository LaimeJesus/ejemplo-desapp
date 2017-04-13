package model.offers;

import org.joda.time.Interval;

import model.Product;
import model.ProductList;
import util.Money;

public class CombinationOffer extends Offer {
		
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


}
