package model.users;

import java.util.ArrayList;
import java.util.List;
import exceptions.ProductIsAlreadySelectedException;
import exceptions.ProductListAlreadyCreatedException;
import exceptions.ProductListNotExistException;
import exceptions.PurchaseRecordNotExistException;
import model.alerts.Alert;
import model.products.Product;
import model.products.ProductList;
import model.registers.PurchaseRecord;
import util.Address;
import util.Entity;
import util.Money;

public class Profile extends Entity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5152611130346050935L;
	private Address address;
	private List<ProductList> allProductLists;
	private List<PurchaseRecord> purchaseRecords;
	private List<Alert> alerts;
	
	public Profile() {
		setAllProductLists(new ArrayList<ProductList>());
		setPurchaseRecords(new ArrayList<PurchaseRecord>());
		setAlerts(new ArrayList<Alert>());
	}
	
	private void setAlerts(List<Alert> alerts) {
		this.alerts = alerts;
	}

	private void setPurchaseRecords(List<PurchaseRecord> prs) {
		this.purchaseRecords = prs;
		
	}

	private void setAllProductLists(List<ProductList> pls) {
		this.allProductLists = pls;
		
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
	
	public List<PurchaseRecord> getPurchaseRecords() {
		return this.purchaseRecords;
	}

	public void addNewPurchaseToHistory(PurchaseRecord aPurchaseRecord) {
		this.getPurchaseRecords().add(aPurchaseRecord);
	}
	
	public void addNewAlert(Alert newAlert){
		this.getAlerts().add(newAlert);
	}

	public List<Alert> getAlerts() {
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

	public void addNewProductList(ProductList aProductList) {
		this.getAllProductList().add(aProductList);
	}

	public PurchaseRecord getPurchaseRecordById(Integer purchaseId) throws PurchaseRecordNotExistException {
		PurchaseRecord res = getPurchaseRecords().stream().filter((PurchaseRecord pr) -> pr.getId().equals(purchaseId)).findFirst().orElse(null);
		if(res == null){
			throw new PurchaseRecordNotExistException("Purchase record with purchaseId: " + purchaseId + " does not exist");
		}
		return res;
	}

	public ProductList getProductListById(Integer listId) throws ProductListNotExistException {
		ProductList res = getAllProductList().stream().filter((ProductList pl) -> pl.getId().equals(listId)).findFirst().orElse(null);
		if(res == null){
			throw new ProductListNotExistException("Product list with productListId: " + listId + " does not exist");
		}
		return res;
	}

	public void deletePurchaseRecordById(Integer purchaseId) throws PurchaseRecordNotExistException {
		PurchaseRecord res = getPurchaseRecordById(purchaseId);
		getPurchaseRecords().remove(res);
	}

	public ProductList getProductListByName(String name) throws ProductListNotExistException {
		ProductList res = getAllProductList().stream().filter((ProductList pl) -> pl.getName().equals(name)).findFirst().orElse(null);
		if(res == null){
			throw new ProductListNotExistException("Product list with name: " + name + " does not exist");
		}
		return res;
	}

	@Override
	public String toString() {
		return "Profile: " + this.getAddress().toString();
	}	
}
