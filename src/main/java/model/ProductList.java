package model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Duration;

import exceptions.ProductDoesNotExistOnListException;
import exceptions.ProductIsAlreadySelectedException;
import util.Money;

public class ProductList {

	
	String name;
	//ArrayList<Umbral> umbrals;
	
	Money totalAmount = new Money(0,0);
	List<SelectedProduct> allProducts = new ArrayList<SelectedProduct>();
	
	
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
		totalAmount.add(newAmount);
	}
	
	public Money calculateAmount(Money unitPrice , Integer quantity) {
		unitPrice.times(quantity);
		return unitPrice;
	}

	//todo
	public Duration getProcessingTime() {
		return null;
	}
	
}
