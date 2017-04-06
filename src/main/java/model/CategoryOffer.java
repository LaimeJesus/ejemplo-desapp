package model;

import org.joda.time.Interval;

import abstracts.Offer;
import util.Category;
import util.Money;

public class CategoryOffer extends Offer {
	
	private Category category;
	
	public CategoryOffer(Integer aDiscountRate, Interval anInterval, Category aCategory) {
		super(aDiscountRate, anInterval);
		this.setCategory(aCategory);
	}

	@Override
	protected Money getPreviousPrice(ProductList productList) {
		return productList.getTotalAmount();
	}

	@Override
	protected boolean verifyProductRequirements(ProductList productListToVerify) {
		return productListToVerify.getAllProducts().stream().anyMatch(
				selectedProduct -> selectedProduct.getProduct().getCategory().equals(this.getCategory())
				);
	}

	public Category getCategory() {
		return this.category;
	}
	
	public void setCategory(Category newCategory) {
		this.category = newCategory;
	}
	
}
