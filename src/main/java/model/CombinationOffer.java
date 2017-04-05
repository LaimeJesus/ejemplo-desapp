package model;

import abstracts.Offer;
import util.Money;

public class CombinationOffer extends Offer {
		
	Product relatedProduct;
	Product combinatedProduct;
	
	public CombinationOffer(Product aRelatedProduct , Product aCombinateProduct , Integer aDiscountRate) {
		super(aDiscountRate);
		this.setRelatedProduct(aRelatedProduct);
		this.setCombinatedProduct(aCombinateProduct);
	}

	@Override
	public Money getPreviousPrice() {
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
