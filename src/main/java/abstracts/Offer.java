package abstracts;

import java.util.List;

import exceptions.MoneyCannotSubstractException;
import model.Product;
import model.ProductList;
import model.SelectedProduct;
import util.Money;

public abstract class Offer {

	private Product relatedProduct;
	
	private Integer discountRate;
	
	
	
	
	public Offer(Product aRelatedProduct , Integer aDiscountRate) {
		this.setRelatedProduct(aRelatedProduct);
		this.setDiscountRate(aDiscountRate);
	}
	
	
	
	
	public abstract Money getFinalPrice();
	
	public abstract Money getPreviousPrice();
	
	public abstract Boolean meetRequirements(List<SelectedProduct> selectedProducts , List<Offer> apliedOffers );
	
	public abstract Money getSavingObteined() throws MoneyCannotSubstractException;
	
	public abstract void applyOffer(ProductList productListToApply);
	
	
	
	
	
	
	
	
	
	public Product getRelatedProduct() {
		return this.relatedProduct;
	}
	
	public void setRelatedProduct(Product newProduct) {
		this.relatedProduct = newProduct;
	}
	
	public Integer getDiscountRate() {
		return this.discountRate;
	}
	
	public void setDiscountRate(Integer newDiscountRate) {
		this.discountRate = newDiscountRate;
	}
}
