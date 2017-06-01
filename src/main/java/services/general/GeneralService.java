package services.general;


import java.util.List;

import org.joda.time.Duration;
import org.springframework.transaction.annotation.Transactional;

import exceptions.InvalidSelectedProduct;
import exceptions.MoneyCannotSubstractException;
import exceptions.OfferIsAlreadyCreatedException;
import exceptions.ProductAlreadyCreatedException;
import exceptions.ProductDoesNotExistException;
import exceptions.ProductDoesNotExistOnListException;
import exceptions.ProductIsAlreadySelectedException;
import exceptions.ProductListDoesNotExist;
import exceptions.PurchaseRecordNotExistException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserDoesNotExistException;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import exceptions.UsernameOrPasswordInvalidException;
import exceptions.WrongUserPermissionException;
import model.offers.Offer;
import model.products.Product;
import model.products.ProductList;
import model.registers.PurchaseRecord;
import model.users.Profile;
import model.users.User;
import services.microservices.ProductListService;
import services.microservices.ProductService;
import services.microservices.ShopService;
import services.microservices.UserService;

public class GeneralService {
	
	
	
	
	
	
	private UserService userService;
	private GeneralOfferService generalOfferService;
	private ProductService productService;
	private ProductListService productListService;
	private ShopService shopService;
	
	@Transactional
	public void createUser (User newUser) throws UserAlreadyExistsException{
		getUserService().createNewUser(newUser);	
	}
	
	@Transactional
	public void loginUser (User user) throws UsernameDoesNotExistException, UsernameOrPasswordInvalidException {
		getUserService().loginUser(user);
	}
	
	@Transactional
	public void logoutUser (User user) throws UsernameDoesNotExistException, UserIsNotLoggedException{
		getUserService().logout(user);
	}
	
	@Transactional
	public void authenticateUser (User user) throws UsernameDoesNotExistException {
		getUserService().authenticateUser(user);
	}
	
	@Transactional
	public void createOffer (Offer offer , User user) throws UsernameDoesNotExistException, WrongUserPermissionException, UserIsNotLoggedException, OfferIsAlreadyCreatedException {
		User logged = getUserService().validateLogged(user);
		if(! logged.hasWritePermission()){
			throw new WrongUserPermissionException();
		}
		if (! generalOfferService.isOfferValid(offer)){
			throw new OfferIsAlreadyCreatedException();
		}
		generalOfferService.save(offer);
	}
	
	
	@Transactional
	public void addProduct(User user, Product product) throws UserIsNotLoggedException, UsernameDoesNotExistException, ProductAlreadyCreatedException {
		userService.validateLogged(user);
		productService.addproduct(product);
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
	public void selectProduct (User user , ProductList productList , Product product , Integer quantity) throws ProductIsAlreadySelectedException, ProductDoesNotExistException, UsernameDoesNotExistException, UserIsNotLoggedException {	
		this.getProductListService().selectProduct(user , productList , product , quantity);
	}
	
	@Transactional
	public void removeProduct (ProductList productList , Product product ) throws ProductDoesNotExistOnListException {
		//this.getProductListService().removeProduct(productList , product);
	}
	
	public void generateRecommmendation (User u , Product p) {}
	
	@Transactional
	public void applyOffer (User user , Offer offer , ProductList productList) throws MoneyCannotSubstractException, UsernameDoesNotExistException {
		if (this.getGeneralOfferService().isOfferValid(offer) ){
			this.getProductListService().applyOffer(user,offer,productList);
		}
	}
	
	public void removeOffer (User u , Offer o , ProductList pl) {}

	
	@Transactional
	public Duration ready(User user, ProductList productList) throws InvalidSelectedProduct, UserIsNotLoggedException, UsernameDoesNotExistException {
		return getShopService().ready(user, productList);
	}

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
	
	public ShopService getShopService() {
		return shopService;
	}

	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}

	@Transactional
	public List<ProductList> getProductLists(User user) throws UserIsNotLoggedException, UsernameDoesNotExistException {
		getUserService().validateLogged(user);
		return user.getProfile().getAllProductList();
	}

	@Transactional
	public void upload(String file) throws Exception {
		getProductService().upload(file);
	}
	
	@Transactional
	public List<Product> allProducts(){
		return getProductService().retriveAll();
	}

	@Transactional
	public Duration waitingTime(String username, String listname) throws UserIsNotLoggedException, UsernameDoesNotExistException, ProductListDoesNotExist {
		User user = new User();
		user.setUsername(username);
		ProductList pl = new ProductList();
		pl.setName(listname);
		return getShopService().waitingTime(user, pl);
	}

	@Transactional
	public void addProducts(List<Product> products) {
		getProductService().saveall(products);
	}

	@Transactional
	public void initRegisters(int registers) {
		getShopService().initialize(registers);
		
	}

	@Transactional
	public Profile getProfile(String username) throws UserIsNotLoggedException, UsernameDoesNotExistException {
		return getUserService().findByUsername(username).getProfile();
	}

	@Transactional
	public void updatePassword(User user) {
		User u = getUserService().findByUsername(user.getUsername());
		u.setPassword(user.getPassword());
		getUserService().update(u);
	}

	@Transactional
	public PurchaseRecord getPurchaseRecord(Integer userId, Integer purchaseId) throws PurchaseRecordNotExistException, UserDoesNotExistException {
		//exist
		User user = getUserService().getUserById(userId);
		//purchase record
		PurchaseRecord res = null;
		for(PurchaseRecord pr : user.getProfile().getPurchaseRecords()){
			if(pr.getId() == purchaseId){
				res = pr;
			}
		}
		if(res == null){
			throw new PurchaseRecordNotExistException();
		}
		return res;
	}	
}
