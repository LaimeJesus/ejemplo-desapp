package model;

import abstracts.Offer;
import util.Money;

public class CrossingOffer extends Offer {
	
	private Product relatedProduct;
	private Integer maxQuantity;
	private Integer minQuantity;

	public CrossingOffer(Integer aDiscountRate , Product newRelatedProduct , Integer maxQuantity , Integer minQuantity) {
		super(aDiscountRate);
		this.setMaxQuantity(maxQuantity);
		this.setMinQuantity(minQuantity);
	}

	@Override
	public Money getPreviousPrice() {
		return this.getRelatedProduct().getPrice().times(this.getMaxQuantity());
	}

	@Override
	protected boolean verifyProductRequirements(ProductList productListToVerify) {
		return productListToVerify.getAllProducts().stream().anyMatch (
			selectedProduct -> selectedProduct.getProduct().equals(this.getRelatedProduct()) &&
							   selectedProduct.getQuantity() >= this.getMaxQuantity()	
				);
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

}
