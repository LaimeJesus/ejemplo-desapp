package services.general;


import org.springframework.transaction.annotation.Transactional;

import exceptions.MoneyCannotSubstractException;
import exceptions.ProductDoesNotExistException;
import exceptions.ProductIsAlreadySelectedException;
import exceptions.UserAlreadyExistsException;
import exceptions.UsernameOrPasswordInvalidException;
import exceptions.WrongUserPermissionException;
import model.offers.Offer;
import model.products.Product;
import model.products.ProductList;
import model.users.User;
import services.microservices.ProductListService;
import services.microservices.ProductService;
import services.microservices.UserService;
import util.Money;

public class GeneralService {
	
	
	
	
	
	
	private UserService userService;
	private GeneralOfferService generalOfferService;
	private ProductService productService;
	private ProductListService productListService;
	
	
	
	@Transactional
	public void createUser (User newUser) throws UserAlreadyExistsException{
		getUserService().createNewUser(newUser);	
	}
	
	@Transactional
	public void loginUser (User user) throws UsernameOrPasswordInvalidException {
		getUserService().loginUser(user);
	}
	
	@Transactional
	public void logoutUser (User user) throws UsernameOrPasswordInvalidException{
		getUserService().logout(user);
	}
	
	@Transactional
	public void authenticateUser (User user) throws UsernameOrPasswordInvalidException {
		getUserService().authenticateUser(user);
	}
	
	@Transactional
	public void createOffer (Offer offer , User user) throws UsernameOrPasswordInvalidException, WrongUserPermissionException {
		if (getUserService().hasWritePermission(user)) {
			getGeneralOfferService().save(offer);
		} else { throw new WrongUserPermissionException(); }
	}
	
	@Transactional
	public void addProduct (Product newProduct) {
		this.getProductService().save(newProduct);
	}
	
	@Transactional
	public void updateProduct (Product newProduct) {
		this.getProductService().update(newProduct);
	}
	
	@Transactional
	public void createNewProductList(User somaValidUser, ProductList second) throws UsernameOrPasswordInvalidException {
		this.getUserService().createProductList(somaValidUser, second);
	}
	
	@Transactional
	public Money selectProduct (User user , ProductList productList , Product product , Integer quantity) throws ProductIsAlreadySelectedException, UsernameOrPasswordInvalidException, ProductDoesNotExistException {	
		return this.getProductListService().selectProduct(user , productList , product , quantity);
	}
	
	public void removeProduct (User u , ProductList pl , Product p ) {}
	
	public void generateRecommmendation (User u , Product p) {}
	
	public void applyOffer (User user , Offer offer , ProductList productList) throws UsernameOrPasswordInvalidException, MoneyCannotSubstractException {
		if (this.getGeneralOfferService().isOfferValid(offer) ){
			this.getProductListService().applyOffer(user,offer,productList);
		}
	}
	
	public void removeOffer (User u , Offer o , ProductList pl) {}

	
	
	
	
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public GeneralOfferService getGeneralOfferService() {
		return generalOfferService;
	}
	public void setGeneralOfferService(GeneralOfferService generalOfferService) {
		this.generalOfferService = generalOfferService;
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

		
	
	
	
	
}
