package services.microservices;

import org.joda.time.Duration;
import org.springframework.transaction.annotation.Transactional;

import exceptions.InvalidSelectedProduct;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import model.products.ProductList;
import model.registers.CashRegisterManager;
import model.registers.PurchaseRecord;
import model.users.User;

public class ShopService{
	
	private ProductService productService;
	private UserService userService;
	private CashRegisterManager cashRegisterManager;
	
	@Transactional
	public void initialize(Integer n){
		setCashRegisterManager(new CashRegisterManager(n));
	}
	
	public CashRegisterManager getCashRegisterManager() {
		return cashRegisterManager;
	}

	public void setCashRegisterManager(CashRegisterManager cashRegisterManager) {
		this.cashRegisterManager = cashRegisterManager;
	}
	
	@Transactional
	public void ready(User u, ProductList p) throws InvalidSelectedProduct, UserIsNotLoggedException, UsernameDoesNotExistException{
		User e = userService.validateLogged(u);
		ProductList pl = e.getProfile().getAllProductList().get(e.getProfile().getAllProductList().indexOf(p));
		cashRegisterManager.addUserWithProductList(e, pl);		
		productService.updateStock(pl);
		e.newPurchase(new PurchaseRecord(p));
		userService.update(e);
	}
	
	@Transactional
	public Duration waitingTime(ProductList p){
		return cashRegisterManager.getWaitingTime(p);
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
}
