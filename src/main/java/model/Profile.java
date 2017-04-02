package model;

import java.util.ArrayList;
import java.util.Iterator;

import exceptions.ProductIsAlreadySelectedException;
import exceptions.ProductListAlreadyCreatedException;
import exceptions.ProductListDoesNotExistException;
import util.Address;
import util.Money;

public class Profile {

	
	private Address address;
	private ArrayList<ProductList> allProductLists;
	
	public Profile() {
		allProductLists = new ArrayList<ProductList>();
	}
	
	
	
	public void createProductList(String newName) throws ProductListAlreadyCreatedException {
		if (this.listAlreadyExist(newName)) {
			throw new ProductListAlreadyCreatedException("Ya existe una lista con el mismo nombre");
		} else {
			ProductList newProductList = new ProductList(newName);
			allProductLists.add(newProductList);
		}
	}
	
	public void addProductToList(String nameOfList , Product productToAdd , Integer howMany) throws ProductIsAlreadySelectedException {
		for (ProductList currentProductList : this.allProductLists) {
			if (currentProductList.getName().equals(nameOfList)) {
				currentProductList.selectProduct(productToAdd , howMany);
			}
		}
	}
	
	public Money getCostOfList(String nameOfList) {
		for (ProductList currentProductList : this.allProductLists) {
			if (currentProductList.getName().equals(nameOfList)) {
				return currentProductList.getTotalAmount();
			}
		}
		return new Money(0,0);
	}
	
	public boolean listAlreadyExist(String name) {
		for (ProductList currentProductList : this.allProductLists) {
			if (currentProductList.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public Address getAddress() {
		return this.address;
	}
	
	public void setAddress(Address newAddress) {
		this.address = newAddress;
	}
	
	public ArrayList<ProductList> getAllProductList() {
		return this.allProductLists;
	}
	
	public void setAllLists(ArrayList<ProductList> newProductLists) {
		this.allProductLists = newProductLists;
	}
	
}
