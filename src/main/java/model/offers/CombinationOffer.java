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
	public boolean isEqualsToMe(Offer offer) {
		if (offer != null && offer instanceof CombinationOffer) {
			CombinationOffer current = (CombinationOffer) offer;
			return
				this.getCombinatedProduct().equals(current.getCombinatedProduct()) &&
				this.getRelatedProduct().equals(current.getRelatedProduct()) &&
				this.getDiscountRate().equals(current.getDiscountRate()) &&
				this.getValidPeriod().equals(current.getValidPeriod());
		}
		return false;
	}

	@Override
	public String toString() {
		return this.getRelatedProduct().toString() + " with " + this.getCombinatedProduct().toString();
	}

}
