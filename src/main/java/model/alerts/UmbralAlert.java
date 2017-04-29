package model.alerts;

import model.products.Product;
import model.products.ProductList;
import util.Category;

public class UmbralAlert extends Alert{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2108128507040668799L;
	private Category category;
	private Double acceptedPercentage;

	
	public UmbralAlert(Category aCategory, Double anAcceptedPercentage, Boolean isOn) {
		super(isOn);
		this.setCategory(aCategory);
		this.setAcceptedPercentage(anAcceptedPercentage);
	}

	public UmbralAlert() {
		super(true);
	}

	private void setCategory(Category aCategory) {
		this.category = aCategory;
	}

	private void setAcceptedPercentage(Double aPercentage) {
		this.acceptedPercentage = aPercentage;
	}
	
	private Double calculateActualPercentageOfACategory(ProductList aProductList, Integer aQuantity) {
		Integer categoryQuantity = aProductList.getQuantityOfProducts(this.getCategory()) + aQuantity;
		Integer totalProductsQuantity = aProductList.getQuantityOfProducts() + aQuantity;
		Double fraction = ((double)categoryQuantity/(double)totalProductsQuantity); 
		return fraction * 100.0;
	}

	private Double getAcceptedPercentage() {
		return this.acceptedPercentage;
	}

	private Category getCategory() {
		return this.category;
	}
	
	private Boolean isLesserThanTheAcceptedPercentage(ProductList aProductList,Integer aQuantity){
		return this.getAcceptedPercentage() < this.calculateActualPercentageOfACategory(aProductList, aQuantity);
	}

	@Override
	public Boolean satisfy(ProductList aProductList) {
		return isLesserThanTheAcceptedPercentage(aProductList, 0); 
	}

	@Override
	public Boolean satisfy(ProductList aProductList, Product aProduct, Integer aQuantity) {
		return aProduct.getCategory().equals(this.getCategory()) && this.isLesserThanTheAcceptedPercentage(aProductList, aQuantity); 
		
	}
}
