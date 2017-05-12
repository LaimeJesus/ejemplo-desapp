package services.general;


import org.springframework.transaction.annotation.Transactional;

import exceptions.ProductDoesNotExistOnListException;
import exceptions.ProductIsAlreadySelectedException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import exceptions.WrongUserPermissionException;
import model.offers.Offer;
import model.products.Product;
import model.products.ProductList;
import model.users.User;
import services.microservices.ProductListService;
import services.microservices.ProductService;
import services.microservices.UserService;

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
	public void loginUser (User user) throws UsernameDoesNotExistException {
		getUserService().loginUser(user);
	}
	
	@Transactional
	public void logoutUser (User user) throws UsernameDoesNotExistException{
		getUserService().logout(user);
	}
	
	@Transactional
	public void authenticateUser (User user) throws UsernameDoesNotExistException {
		getUserService().authenticateUser(user);
	}
	
	@Transactional
	public void createOffer (Offer offer , User user) throws UsernameDoesNotExistException, WrongUserPermissionException, UserIsNotLoggedException {
		User logged = getUserService().validateLogged(user);
		if(logged.hasWritePermission()){
			getGeneralOfferService().save(offer);			
		}else{
			throw new WrongUserPermissionException();
		}
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
	public void createProductList (User user , ProductList productList) throws UsernameDoesNotExistException, UserIsNotLoggedException {
		this.getUserService().createProductList(user,productList);
	}
	
	@Transactional
	public void selectProduct (ProductList productList , Product product , Integer quantity) throws ProductIsAlreadySelectedException {	
		this.getProductListService().selectProduct(productList , product , quantity);
	}
	
	@Transactional
	public void removeProduct (ProductList productList , Product product ) throws ProductDoesNotExistOnListException {
		this.getProductListService().removeProduct(productList , product);
	}
	
	public void generateRecommmendation (User u , Product p) {}
	
	public void applyOffer (User u , Offer o , ProductList pl) {}
	
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
