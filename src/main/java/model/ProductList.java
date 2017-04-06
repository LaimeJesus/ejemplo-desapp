package model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Duration;

import exceptions.ProductDoesNotExistOnListException;
import exceptions.ProductIsAlreadySelectedException;
import model.offers.Offer;
import util.Money;

public class ProductList {

	
	private String name;	
	private Money totalAmount = new Money(0,0);
	private List<SelectedProduct> allProducts = new ArrayList<SelectedProduct>();
	private List<Offer> appliedOffers = new ArrayList<Offer>();
	
	public ProductList () { 
	}
	
	public ProductList (String newName) {
		this.name = newName;
	}
	
	public void selectProduct(Product product , Integer howMany) throws ProductIsAlreadySelectedException {
		if (this.thisProductIsSelected(product)) {
			throw new ProductIsAlreadySelectedException("El producto que intenta seleccionar, ya se encuentra dentro del listado");
		} else {
			this.addProductToList(new SelectedProduct(product, howMany));
			this.updateAmount( this.calculateAmount(product.getPrice() , howMany) );

		}
	}
	
	public boolean thisProductIsSelected (Product someProduct) {
		//allProducts.contains(someProduct);
		for (SelectedProduct currentProduct : allProducts) {
			if (currentProduct.getProduct().equals(someProduct)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isEmpty() {
		return this.allProducts.isEmpty();
	}
	
	public void addProductToList (SelectedProduct newProduct) {
		this.allProducts.add(newProduct);
	}
	
	public void deleteProductFromList (Product productToRemove) {
		this.allProducts.remove(productToRemove);
	}
	
	public void removeProduct(Product productToRemove) throws ProductDoesNotExistOnListException {
		if (this.thisProductIsSelected(productToRemove)) {
			this.deleteProductFromList(productToRemove);
		} else {
			throw new ProductDoesNotExistOnListException("El producto que intenta eliminar no se encuentra seleccionado");
		}
	}
	
	public void updateAmount(Money newAmount) {
		totalAmount = totalAmount.add(newAmount);
	}
	
	public Money calculateAmount(Money unitPrice , Integer quantity) {
		return unitPrice.times(quantity);
	}

	//todo
	public Duration getProcessingTime() {
		Duration d = new Duration(0L);
		for(SelectedProduct p : this.getAllProducts()){
			d = d.plus(p.getProduct().getProcessingTime().multipliedBy(p.getQuantity()));
		}
		return null;
	}
	
	public int getQuantityOfProducts() {
		return 0;
	}	
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String newName) {
		this.name = newName;
	}
	
	public Money getTotalAmount() {
		return this.totalAmount;
	}
	
	public void setTotalAmount(Money newTotalAmount) {
		this.totalAmount = newTotalAmount;
	}
	
	public List<SelectedProduct> getAllProducts() {
		return this.allProducts;
	}
	
	public void setAllProducts (ArrayList<SelectedProduct> newProducts) {
		this.allProducts = newProducts;
	}
	
	@Override
	public boolean equals(Object anyObject) {
		
		if (this.isMyType(anyObject)) {
			ProductList newProductList = (ProductList) anyObject;
			return this.totalEquals(newProductList) ;
		}
		return false;
		
	}
	
	private boolean isMyType(Object anyObject) {
		return anyObject != null && anyObject instanceof ProductList;
	}
	
	private boolean totalEquals(ProductList someProductList) {
		return this.getName().equals(someProductList.getName());
	}

	public Boolean isApplicable(Offer aOffer) {
		return this.getAppliedOffers().stream().noneMatch(
				offer -> offer.equals(aOffer)
				);
	}

	public void applyOffer(Offer aNewOffer) {
		this.getAppliedOffers().add(aNewOffer);
	}

	public List<Offer> getAppliedOffers() {
		return appliedOffers;
	}

	public void setAppliedOffers(List<Offer> appliedOffers) {
		this.appliedOffers = appliedOffers;
	}
	
}
