package model.offers;

import java.util.List;

import org.joda.time.Interval;

import model.products.ProductList;
import model.products.SelectedProduct;
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
		Money result = new Money(0,0);
		for (SelectedProduct p : productList.getAllProducts()) {
			if (p.getProduct().getCategory().equals(this.getCategory())) {
				result = result.add(p.getFinalPrice());
			}
		}
		return result;
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
	
	@Override
	protected boolean isEqualsToMe(Offer offer) {
		if (offer != null && offer instanceof CategoryOffer) {
			CategoryOffer current = (CategoryOffer) offer;
			return 
				this.getCategory().equals(current.getCategory()) && 
				this.getDiscountRate().equals(current.getDiscountRate()) &&
				this.getValidPeriod().equals(current.getValidPeriod());
		}
		return false;
	}
}
