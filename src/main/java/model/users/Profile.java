package model.users;

import java.util.ArrayList;
import java.util.List;

import exceptions.ProductIsAlreadySelectedException;
import exceptions.ProductListAlreadyCreatedException;
import model.alerts.Alert;
import model.products.Product;
import model.products.ProductList;
import model.products.PurchaseRecord;
import util.Address;
import util.Entity;
import util.Money;

public class Profile extends Entity{

	
	private Address address;
	private List<ProductList> allProductLists;
	private List<PurchaseRecord> purchaseHistory;
	private List<Alert> alerts;
	
	public Profile() {
		allProductLists = new ArrayList<ProductList>();
		purchaseHistory = new ArrayList<PurchaseRecord>();
		this.alerts = new ArrayList<Alert>();
	}
	
	public void createProductList(String newName) throws ProductListAlreadyCreatedException {
		if (this.listAlreadyExist(newName)) {
			throw new ProductListAlreadyCreatedException("ProductList is already created");
		} else {
			ProductList newProductList = new ProductList(newName);
			this.getAllProductList().add(newProductList);
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
	
	public List<PurchaseRecord> getPurchaseHistory() {
		return this.purchaseHistory;
	}

	public void addNewPurchaseToHistory(PurchaseRecord aPurchaseRecord) {
		this.getPurchaseHistory().add(aPurchaseRecord);
	}
	
	public void addNewAlert(Alert newAlert){
		this.getAlerts().add(newAlert);
	}

	private List<Alert> getAlerts() {
		return this.alerts;
	}
	
	public Boolean checkCanDisplayAlerts(ProductList aProductList, Product aProduct, Integer aQuantity){
		return this.getAlerts().stream().anyMatch(currentAlert -> currentAlert.canDisplayAlert(aProductList, aProduct, aQuantity));
	}
	
	public Boolean checkCanDisplayAlerts(ProductList aProductList){
		return this.getAlerts().stream().anyMatch(currentAlert -> currentAlert.canDisplayAlert(aProductList));
	}
	
	public void activate(Alert anAlert){
		this.getAlerts().get(this.getAlerts().indexOf(anAlert)).activate();
	}
	public void shutdown(Alert anAlert){
		this.getAlerts().get(this.getAlerts().indexOf(anAlert)).shutdown();
	}
}
