package model.products;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.Duration;

import exceptions.MoneyCannotSubstractException;
import exceptions.ProductDoesNotExistOnListException;
import exceptions.ProductIsAlreadySelectedException;
import exceptions.SelectedProductNotExistException;
import model.offers.Offer;
import util.Category;
import util.Entity;
import util.Money;

public class ProductList extends Entity {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6025165365717618663L;
	private String name;	
	private Money totalAmount;
	public List<SelectedProduct> allProducts;
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
		this.validateProductIsSelected(product);
		this.addProductToList(new SelectedProduct(product, howMany));
	}
	
	private void validateProductIsSelected(Product product) throws ProductIsAlreadySelectedException {
		if ( this.thisProductIsSelected(product)) {
			throw new ProductIsAlreadySelectedException("product is already selected");
		}
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
	
	public void removeProduct(Product productToRemove) throws ProductDoesNotExistOnListException, ProductIsAlreadySelectedException, MoneyCannotSubstractException {
		if (this.thisProductIsSelected(productToRemove)) {
			this.deleteProductFromList(productToRemove);
			this.update();
		} else {
			throw new ProductDoesNotExistOnListException("El producto que intenta eliminar no se encuentra seleccionado");
		}
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
		return this.getMoneyOfProductsWithOffers();
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
		return this.appliedOffers;
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
	
	public Money getMoneyOfProducts() {
		Money result = new Money(0,0);		
		for (SelectedProduct p : this.getAllProducts()) {
			result = result.add(p.getFinalPrice());
		}
		return result;
	}
	
	public Money getMoneyOfProductsWithOffers() {
		Money result = this.getMoneyOfProducts();
		for (Offer o : this.getAppliedOffers()) {
			result = o.getFinalPrice(this,result);
		}
		return result;
	}
	
	public void applyOffer(Offer offer) throws MoneyCannotSubstractException {
		if (this.isApplicable(offer)) {
			this.getAppliedOffers().add(offer);
			this.setTotalAmount( offer.getFinalPrice(this , this.getTotalAmount()) );
		}
	}
	
	public void disapplyOffer(Offer offer) throws ProductIsAlreadySelectedException, MoneyCannotSubstractException {
		this.getAppliedOffers().remove(offer);
		this.update();
	}
	
	public Boolean isApplicable(Offer anOffer) {
		return anOffer.meetRequirements(this);
	}
	
	public void update() throws ProductIsAlreadySelectedException, MoneyCannotSubstractException {
		List<Offer> applied = this.getAppliedOffers();
		List<SelectedProduct> selected = this.getAllProducts();
		
		this.initialize();
		
		for (SelectedProduct selectedProduct : selected) {
			this.selectProduct(selectedProduct.getProduct(), selectedProduct.getQuantity());
		}
		
		for (Offer offer : applied) {
			this.applyOffer(offer);
		}
	}

	public SelectedProduct getSelectedProduct(Integer selectProductId) throws SelectedProductNotExistException {
		SelectedProduct res = getAllProducts().stream().filter((SelectedProduct sp) -> sp.getId().equals(selectProductId)).findFirst().orElse(null);
		if(res == null) throw new SelectedProductNotExistException("Selected Product with id: " + selectProductId+" does not exist");
		return res;
	}
}
