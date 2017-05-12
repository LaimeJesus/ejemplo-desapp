package model.products;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.Duration;

import exceptions.MoneyCannotSubstractException;
import exceptions.ProductDoesNotExistOnListException;
import exceptions.ProductIsAlreadySelectedException;
import model.offers.Offer;
import util.Category;
import util.Entity;
import util.Monetizable;
import util.Money;

public class ProductList extends Entity implements Monetizable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6025165365717618663L;
	private String name;	
	private Money totalAmount;
	public List<SelectedProduct> allProducts;
	private List<Offer> appliedOffers;
	
	private String moneyValue;
	
	public ProductList () {
		this.initialize();
	}
	
	public ProductList (String newName) {
		this.name = newName;
		this.initialize();
	}
	
	private void initialize() {
		this.setAllProducts(new ArrayList<SelectedProduct>());
		this.setTotalAmount(new Money(0,0));
		this.setAppliedOffers(new ArrayList<Offer>());
	}
	
	
	public List<SelectedProduct> getSelectedProductsBy(Category aCategory){
		List<SelectedProduct> result = new ArrayList<SelectedProduct>();
		for (SelectedProduct current : this.getAllProducts()) {
			if (current.getProduct().getCategory().equals(aCategory)){
				result.add(current);
			}
		}
		return result;
	}
	
	public SelectedProduct getSelectedProduct(Product someProduct){
		for(SelectedProduct selectedIterator : this.getAllProducts()){
			if(selectedIterator.equals(someProduct)){
				return selectedIterator;
			}
		}
		return null;
	}

	public void selectProduct(Product product , Integer howMany) throws ProductIsAlreadySelectedException {
		if (this.thisProductIsSelected(product)) {
			throw new ProductIsAlreadySelectedException("El producto que intenta seleccionar, ya se encuentra dentro del listado");
		} else {
			this.addProductToList(new SelectedProduct(product, howMany));
			this.plusAmount( this.calculateAmount(product.getPrice() , howMany) );
		}
	}
	
	public boolean thisProductIsSelected (Product someProduct) {
		for (SelectedProduct selected : this.getAllProducts()) {
			if (selected.getProduct().equals(someProduct))
				return true;
		}
		return false;
	}
	
	public void addProductToList (SelectedProduct newProduct) {
		this.allProducts.add(newProduct);
	}
	
	private void deleteProductFromList (Product productToRemove) {
		this.getAllProducts().removeIf( 
				currentSelectedProduct -> currentSelectedProduct.getProduct().equals(productToRemove)
				);
	}
	
	public boolean isEmpty() {
		return this.allProducts.isEmpty();
	}
	
	public void removeProduct(Product productToRemove) throws ProductDoesNotExistOnListException {
		if (this.thisProductIsSelected(productToRemove)) {
			this.deleteProductFromList(productToRemove);
			this.update();
		} else {
			throw new ProductDoesNotExistOnListException("El producto que intenta eliminar no se encuentra seleccionado");
		}
	}
	
	public void plusAmount(Money newAmount) {
		totalAmount = totalAmount.add(newAmount);
	}
	
	public void minusAmount (Money money) throws MoneyCannotSubstractException{
		totalAmount = totalAmount.minus(money);
	}
	
	public Money calculateAmount(Money unitPrice , Integer quantity) {
		return unitPrice.times(quantity);
	}

	public Duration getProcessingTime() {
		Duration processingTime = new Duration(0L);
		for(SelectedProduct aSelectedProduct : this.getAllProducts()){
			processingTime = processingTime.plus(aSelectedProduct.getProcessingTime());
		}
		return processingTime;
	}
	
	public Integer getQuantityOfProducts() {
		Integer quantity = 0;
		for(SelectedProduct p : this.getAllProducts()){
			quantity += p.getQuantity();
		}
		return quantity;
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
	
	public List<Offer> getAppliedOffers() {
		return appliedOffers;
	}
	
	public void setAppliedOffers(List<Offer> appliedOffers) {
		this.appliedOffers = appliedOffers;
	}
	
///////////////////////////////
///////////////////////////////
////// EQUALS METHODS /////////
///////////////////////////////
///////////////////////////////
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
///////////////////////////////
///////////////////////////////
////// EQUALS METHODS /////////
///////////////////////////////
///////////////////////////////
	
	public Integer getQuantityOfProducts(Category aCategory) {
		Integer categoryTotal = 0;
		List<SelectedProduct> filteredSelectedProduct = this.getSelectedProductsBy(aCategory);	
		for(SelectedProduct sp : filteredSelectedProduct){
			categoryTotal += sp.getQuantity();
		}
		return categoryTotal;

	}
	
///////////////////////////////
///////////////////////////////
////// OFFER METHODS //////////
///////////////////////////////
///////////////////////////////
	
	public void applyOffer (Offer offer) throws MoneyCannotSubstractException {
		if (this.isApplicable(offer)) {
			this.setTotalAmount(offer.getFinalPrice(this));
			this.addOffer(offer);
		}
	}
	
	public void disapplyOffer (Offer offer) {
		if (this.getAppliedOffers().contains(offer)) {
			this.removeOffer(offer);
			this.update();
		}
	}
	
	public Boolean isApplicable(Offer aOffer) {
		return this.getAppliedOffers().stream().noneMatch(
				offer -> offer.equals(aOffer)
				);
	}
	
	private void addOffer(Offer aNewOffer) {
		this.getAppliedOffers().add(aNewOffer);
	}
	
	private void removeOffer(Offer offerToRemove) {
		this.getAppliedOffers().remove(offerToRemove);
	}
///////////////////////////////
///////////////////////////////
////// OFFER METHODS //////////
///////////////////////////////
///////////////////////////////

	public void update() {
		List<Offer> currentOffers = this.getAppliedOffers();
		List<SelectedProduct> currentProducts = this.getAllProducts();
		
		this.initialize();

		try {
			for (SelectedProduct selected : currentProducts) {
				this.selectProduct(selected.getProduct(), selected.getQuantity());				
			}
			for (Offer offer : currentOffers) {
				this.applyOffer(offer);
			}
		} 	
		catch (ProductIsAlreadySelectedException | MoneyCannotSubstractException e) {
		}
	}
	
	
	
	/**
	 * Metodos necesarios para garantizar que se persistira un VARCHAR en lugar de un Money
	 */

	@Override
	public Money getMonetizableElement() {
		return this.totalAmount;
	}

	@Override
	public void setMonetizableElement(Money newMoney) {
		this.totalAmount = newMoney;
	}
	
}
