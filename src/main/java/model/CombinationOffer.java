package model;

import java.util.List;

import abstracts.Offer;
import exceptions.MoneyCannotSubstractException;
import util.Money;

public class CombinationOffer extends Offer {

	
	Product combinatedProduct;
	
	public CombinationOffer(Product aRelatedProduct , Product aCombinateProduct , Integer aDiscountRate) {
		super(aRelatedProduct,aDiscountRate);
		this.setCombinatedProduct(aCombinateProduct);
	}

	@Override
	public Money getFinalPrice() {
		return this.getPreviousPrice().percentage(this.getDiscountRate());
	}

	@Override
	public Money getPreviousPrice() {
		return this.getRelatedProduct().getPrice().add(this.getCombinatedProduct().getPrice());
	}

	@Override
	public Boolean meetRequirements(List<SelectedProduct> selectedProducts, List<Offer> apliedOffers) {
		return selectedProducts.stream().anyMatch( 
				selectedProduct -> selectedProduct.getProduct().equals(this.getRelatedProduct()));
	}

	@Override
	public Money getSavingObteined() throws MoneyCannotSubstractException {
		return this.getPreviousPrice().minus(this.getFinalPrice());
	}

	@Override
	public void applyOffer(ProductList productListToApply) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	
	public Product getCombinatedProduct() {
		return this.combinatedProduct;
	}
	
	public void setCombinatedProduct(Product aNewCombinatedProduct) {
		this.combinatedProduct = aNewCombinatedProduct;
	}

}
