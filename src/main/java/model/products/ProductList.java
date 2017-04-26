package model.products;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.Duration;

import exceptions.ProductDoesNotExistOnListException;
import exceptions.ProductIsAlreadySelectedException;
import model.offers.Offer;
import util.Category;
import util.Entity;
import util.Money;

public class ProductList extends Entity {

	
	private String name;	
	private Money totalAmount;
	private List<SelectedProduct> allProducts;
	private List<Offer> appliedOffers;
	
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
	
	public void selectProduct(Product product , Integer howMany) throws ProductIsAlreadySelectedException {
		if (this.thisProductIsSelected(product)) {
			throw new ProductIsAlreadySelectedException("El producto que intenta seleccionar, ya se encuentra dentro del listado");
		} else {
			this.addProductToList(new SelectedProduct(product, howMany));
			this.updateAmount( this.calculateAmount(product.getPrice() , howMany) );

		}
	}
	
	public List<SelectedProduct> getSelectedProductsBy(Category aCategory){
		return this.getAllProducts().stream().filter(
				aSelectedProduct -> aSelectedProduct.getProduct().getCategory().equals(aCategory) 
				).collect(Collectors.toList());
	}
	
	public SelectedProduct getSelectedProduct(Product someProduct){
		for(SelectedProduct selectedIterator : this.getAllProducts()){
			if(selectedIterator.equals(someProduct)){
				return selectedIterator;
			}
		}
		return null;
	}
	
	public boolean thisProductIsSelected (Product someProduct) {
		return this.getAllProducts().stream().anyMatch( 
				currentSelected -> currentSelected.getProduct().equals(someProduct)
				);
		
	}
	
	public boolean isEmpty() {
		return this.allProducts.isEmpty();
	}
	
	public void addProductToList (SelectedProduct newProduct) {
		this.allProducts.add(newProduct);
	}
	
	private void deleteProductFromList (Product productToRemove) {
		this.getAllProducts().removeIf( 
					currentSelectedProduct -> currentSelectedProduct.getProduct().equals(productToRemove)
				);
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

	public Integer getQuantityOfProducts(Category aCategory) {
		Integer categoryTotal = 0;
		List<SelectedProduct> filteredSelectedProduct = this.getSelectedProductsBy(aCategory);	
		for(SelectedProduct sp : filteredSelectedProduct){
			categoryTotal += sp.getQuantity();
		}
		return categoryTotal;

	}
	
}
