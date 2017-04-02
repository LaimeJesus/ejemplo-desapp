package model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Duration;

import exceptions.ProductDoesNotExistOnListException;
import exceptions.ProductIsAlreadySelectedException;
import util.Money;

public class ProductList {

	
	private String name;
	//ArrayList<Umbral> umbrals;
	
	private Money totalAmount = new Money(0,0);
	private List<SelectedProduct> allProducts = new ArrayList<SelectedProduct>();
	
	
	public ProductList () { }
	
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
		return null;
	}
	
	public Money getTotalAmount(){
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
	
}
