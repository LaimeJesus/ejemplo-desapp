package services.microservices;

import javax.annotation.PreDestroy;

import org.joda.time.Duration;
import org.springframework.transaction.annotation.Transactional;

import exceptions.InvalidSelectedProduct;
import exceptions.ProductListDoesNotExist;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import model.products.ProductList;
import model.registers.CashRegisterManager;
import model.registers.PurchaseRecord;
import model.users.User;

public class ShopService{
	
	private ProductService productService;
	private UserService userService;
	private ProductListService productListService;
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
	public Duration ready(User u, ProductList p) throws InvalidSelectedProduct, UserIsNotLoggedException, UsernameDoesNotExistException{
		User exist = userService.validateLogged(u);
		ProductList pl = productListService.getByUser(p, exist);
		Duration d = cashRegisterManager.getWaitingTime(pl);
		cashRegisterManager.addUserWithProductList(exist, pl);		
		productService.updateStock(pl);
		exist.newPurchase(new PurchaseRecord(p));
		userService.update(exist);
		return d.plus(pl.getProcessingTime());
	}
	
	@Transactional
	public Duration waitingTime(User u, ProductList p) throws UserIsNotLoggedException, UsernameDoesNotExistException, ProductListDoesNotExist{
		User logged = userService.validateLogged(u);
		ProductList pl = getProductListService().getByUser(p, logged);
		if(pl == null) {
			throw new ProductListDoesNotExist();
		}
		return cashRegisterManager.getWaitingTime(pl);
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

	public ProductListService getProductListService() {
		return productListService;
	}

	public void setProductListService(ProductListService productListService) {
		this.productListService = productListService;
	}
	
	@PreDestroy
	public void stop(){
		cashRegisterManager.stop();
	}
}
