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
		List<SelectedProduct> list = productList.getSelectedProductsBy(this.getCategory());
		Money result = new Money(0,0);
		for (SelectedProduct current : list) {
			result = result.add( current.getProduct().getPrice().times(current.getQuantity()) );
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
	public boolean equals(Object anotherPossibleOffer ) {
		if(this.isMyType(anotherPossibleOffer)){
			CategoryOffer newOffer = (CategoryOffer) anotherPossibleOffer;
			return this.totalEquals(newOffer);
		}
		return false;
	}
	
	private boolean isMyType(Object anotherPossibleOffer) {
		return anotherPossibleOffer != null && anotherPossibleOffer instanceof CategoryOffer;
	}

	public boolean totalEquals( CategoryOffer someOffer ) {
		return 
			this.getDiscountRate().equals(someOffer.getDiscountRate()) &&
			this.getValidPeriod().equals(someOffer.getValidPeriod()) &&
			this.getCategory().equals(someOffer.getCategory());
	}
	
}
