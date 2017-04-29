package model.offers;

import org.joda.time.Interval;

import model.products.ProductList;
import util.Category;
import util.Money;

public class CategoryOffer extends Offer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1815897013800073580L;
	private Category category;
	
	public CategoryOffer(Integer aDiscountRate, Interval anInterval, Category aCategory) {
		super(aDiscountRate, anInterval);
		this.setCategory(aCategory);
	}

	@Override
	public Money getPreviousPrice(ProductList productList) {
		return productList.getTotalAmount();
	}

	@Override
	protected boolean verifyProductRequirements(ProductList productListToVerify) {
		return productListToVerify.getAllProducts().stream().anyMatch(
				selectedProduct -> selectedProduct.getProduct().getCategory().equals(this.getCategory())
				);
	}

	
	public CategoryOffer(){
		super();
	}
	
	public Category getCategory() {
		return this.category;
	}
	
	public void setCategory(Category newCategory) {
		this.category = newCategory;
	}
	
}
