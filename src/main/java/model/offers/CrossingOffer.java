package model.offers;

import org.joda.time.Interval;

import exceptions.MoneyCannotSubstractException;
import model.products.Product;
import model.products.ProductList;
import util.Money;

public class CrossingOffer extends Offer {

	/**
	 *
	 */
	private static final long serialVersionUID = -695045664315277760L;
	private Product relatedProduct;
	private Integer maxQuantity;
	private Integer minQuantity;

	public CrossingOffer(Integer aDiscountRate , Product newRelatedProduct , Integer maxQuantity , Integer minQuantity , Interval validPeriod) {
		super(aDiscountRate, validPeriod);
		this.setRelatedProduct(newRelatedProduct);
		this.setMaxQuantity(maxQuantity);
		this.setMinQuantity(minQuantity);
	}

	@Override
	public Money getPreviousPrice(ProductList productListToGetPrice) {
		return getPriceOfRelatedProduct().times(this.getMaxQuantity());
	}

	@Override
	protected boolean verifyProductRequirements(ProductList productListToVerify) {
		return productListToVerify.getAllProducts().stream().anyMatch (
			selectedProduct -> selectedProduct.getProduct().equals(this.getRelatedProduct()) &&
							   selectedProduct.getQuantity() >= this.getMaxQuantity()
				);
	}

	@Override
	public Money getFinalPrice(ProductList productListToGetPrice , Money totalAmount) {
		try {
			return totalAmount.minus(
				(this.getRelatedProduct().getPrice().times(this.getMaxQuantity())).minus(this.getRelatedProduct().getPrice().times(this.getMinQuantity()))
				);
		} catch (MoneyCannotSubstractException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Money(0,0);
		}
	}


	public CrossingOffer() {
		super();
	}

	public Integer getMaxQuantity() {
		return maxQuantity;
	}

	public void setMaxQuantity(Integer maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	public Integer getMinQuantity() {
		return minQuantity;
	}

	public void setMinQuantity(Integer minQuantity) {
		this.minQuantity = minQuantity;
	}

	public Product getRelatedProduct() {
		return relatedProduct;
	}

	public void setRelatedProduct(Product relatedProduct) {
		this.relatedProduct = relatedProduct;
	}

	private Money getPriceOfRelatedProduct() {
		return this.getRelatedProduct().getPrice();
	}

	@Override
	public boolean isEqualsToMe(Offer offer) {
		if (offer != null && offer instanceof CrossingOffer) {
			CrossingOffer current = (CrossingOffer) offer;
			return
				this.getMinQuantity().equals(current.getMinQuantity()) &&
				this.getMaxQuantity().equals(current.getMaxQuantity()) &&
				this.getRelatedProduct().equals(current.getRelatedProduct()) &&
				this.getDiscountRate().equals(current.getDiscountRate()) &&
				this.getValidPeriod().equals(current.getValidPeriod());
		}
		return false;
	}

	@Override
	public String toString() {
		return this.getMaxQuantity() + " x " + this.getMinQuantity() + " of " + this.getRelatedProduct().toString();
	}
}
