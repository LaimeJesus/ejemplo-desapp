package model;

import java.util.ArrayList;
import java.util.List;

import exceptions.ProductIsAlreadySelectedException;
import exceptions.ProductListAlreadyCreatedException;
import util.Address;
import util.Money;

public class Profile {

	
	private Address address;
	private List<ProductList> allProductLists;
	private List<PurchaseRecord> purchaseHistory;
	
	public Profile() {
		allProductLists = new ArrayList<ProductList>();
		purchaseHistory = new ArrayList<PurchaseRecord>();
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
		this.getList(nameOfList).selectProduct(productToAdd, howMany);
	}
	
	public Money getCostOfList(String nameOfList) {
		return this.getList(nameOfList).getTotalAmount();
	}
	
	public ProductList getList(String aProductListName){
		ProductList res = null;
		for(ProductList pl : this.getAllProductList()){
			if(pl.getName().equals(aProductListName)){
				return pl;
			}
		}
		return res;
	}
	
	public boolean listAlreadyExist(String name) {
		return this.getList(name) != null;
	}
	
	public Address getAddress() {
		return this.address;
	}
	
	public void setAddress(Address newAddress) {
		this.address = newAddress;
	}
	
	public List<ProductList> getAllProductList() {
		return this.allProductLists;
	}
	
	public void setAllLists(ArrayList<ProductList> newProductLists) {
		this.allProductLists = newProductLists;
	}

	public List<PurchaseRecord> getPurchaseHistory() {
		return this.purchaseHistory;
	}

	public void addNewPurchaseToHistory(PurchaseRecord aPurchaseRecord) {
		this.getPurchaseHistory().add(aPurchaseRecord);
	}
	
}
