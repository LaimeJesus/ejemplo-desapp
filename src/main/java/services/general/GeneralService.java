package services.general;


import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.Duration;
import org.springframework.transaction.annotation.Transactional;

import exceptions.CategoryOfferNotExistException;
import exceptions.CombinationOfferNotExistException;
import exceptions.CrossingOfferNotExistException;
import exceptions.InvalidSelectedProduct;
import exceptions.MoneyCannotSubstractException;
import exceptions.OfferIsAlreadyCreatedException;
import exceptions.ProductAlreadyCreatedException;
import exceptions.ProductDoesNotExistException;
import exceptions.ProductDoesNotExistOnListException;
import exceptions.ProductIsAlreadySelectedException;
import exceptions.ProductListDoesNotExist;
import exceptions.ProductListNotExistException;
import exceptions.ProductNotExistException;
import exceptions.PurchaseRecordNotExistException;
import exceptions.SelectedProductNotExistException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserDoesNotExistException;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import exceptions.UsernameOrPasswordInvalidException;
import exceptions.WrongUserPermissionException;
import model.offers.CategoryOffer;
import model.offers.CombinationOffer;
import model.offers.CrossingOffer;
import model.offers.Offer;
import model.products.Product;
import model.products.ProductList;
import model.products.SelectedProduct;
import model.registers.PurchaseRecord;
import model.users.Profile;
import model.users.User;
import rest.dtos.SelectedProductDTO;
import rest.dtos.offers.CombinationOfferDTO;
import rest.dtos.offers.CrossingOfferDTO;
import services.microservices.ProductListService;
import services.microservices.ProductService;
import services.microservices.ShopService;
import services.microservices.UserService;
import util.Password;

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
	public void updatePassword(User user) {
		User u = getUserService().findByUsername(user.getUsername());
		u.setPassword(user.getPassword());
		getUserService().update(u);
	}
	
	@Transactional
	public Profile getProfile(String username) throws UserIsNotLoggedException, UsernameDoesNotExistException {
		return getUserService().findByUsername(username).getProfile();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Transactional
	public PurchaseRecord getPurchaseRecord(Integer userId, Integer purchaseId) throws PurchaseRecordNotExistException, UserDoesNotExistException {
		return getUserService().getUserById(userId).getProfile().getPurchaseRecordById(purchaseId);
	}

	@Transactional
	public List<User> getUsers() {
		return getUserService().retriveAll();
	}

	@Transactional
	public List<ProductList> getProductLists(Integer userId) throws UserDoesNotExistException {
		return getUserService().getProductLists(userId).stream().map(x -> x.updateTotalAmount()).collect(Collectors.toList());
	}

	@Transactional
	public User getUserById(Integer userId) throws UserDoesNotExistException {
		return getUserService().getUserById(userId);
	}

	@Transactional
	public List<PurchaseRecord> getPurchaseRecords(Integer userId) throws UserDoesNotExistException {
		return getUserService().getUserById(userId).getProfile().getPurchaseRecords();
	}

	@Transactional
	public Profile getProfile(Integer userId) throws UserDoesNotExistException {
		return getUserService().getUserById(userId).getProfile();
	}

	@Transactional
	public ProductList getProductListById(Integer userId, Integer listId) throws ProductListNotExistException, UserDoesNotExistException {
		return getUserService().getUserById(userId).getProfile().getProductListById(listId);
	}

	@Transactional
	public void deleteUser(Integer userId) throws UserDoesNotExistException {
		getUserService().delete(getUserById(userId));		
	}

	@Transactional
	public Product getProductById(Integer productId) throws ProductNotExistException {
		return getProductService().getProductById(productId);
	}

	@Transactional
	public void deleteProductById(Integer productId) throws ProductNotExistException {
		getProductService().delete(getProductService().getProductById(productId));
	}

	@Transactional
	public void createProduct(Product product) throws ProductAlreadyCreatedException {
		getProductService().createProduct(product);
	}

	@Transactional
	public void createOrUpdateUser(Integer userId, User user) throws UserAlreadyExistsException {
		getUserService().createOrUpdate(userId, user);
	}

	@Transactional
	public void createOrUpdateProduct(Integer productId, Product product) throws ProductAlreadyCreatedException {
		getProductService().createOrUpdate(productId, product);
	}

	
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Transactional
	public List<Offer> getOffers() {
		return getGeneralOfferService().retriveAll();
	}

	@Transactional
	public List<CategoryOffer> getCategoryOffers() {
		return getGeneralOfferService().getCategoryOfferService().retriveAll();
	}

	@Transactional
	public List<CombinationOffer> getCombinationOffers() {
		return getGeneralOfferService().getCombinationOfferService().retriveAll();
	}

	@Transactional
	public List<CrossingOffer> getCrossingOffers() {
		return getGeneralOfferService().getCrossingOfferService().retriveAll();
	}
	
	@Transactional
	public void deleteOffers() {
		getGeneralOfferService().deleteAll();		
	}
	
	@Transactional
	public void deleteCategoryOffer(Integer categoryOfferId) throws CategoryOfferNotExistException {
		getGeneralOfferService().getCategoryOfferService().delete(categoryOfferId);		
	}

	@Transactional
	public CategoryOffer getCategoryOfferById(Integer categoryOfferId) throws CategoryOfferNotExistException {
		return getGeneralOfferService().getCategoryOfferService().getCategoryOfferById(categoryOfferId);
	}

	@Transactional
	public void createCategoryOffer(CategoryOffer categoryOffer) {
		getGeneralOfferService().getCategoryOfferService().createOffer(categoryOffer);
	}

	@Transactional
	public void createCombinationOffer(CombinationOfferDTO co) throws ProductNotExistException {
		Product related = getProductById(co.relatedProductId);
		Product combinated = getProductById(co.combinatedProductId);
		getGeneralOfferService().getCombinationOfferService().createOffer(co.toCombinationOffer(related, combinated));
	}
	
	@Transactional
	public CombinationOffer getCombinationOfferById(Integer combinationOfferId) throws CombinationOfferNotExistException {
		return getGeneralOfferService().getCombinationOfferService().getCombinationOfferById(combinationOfferId);
	}

	@Transactional
	public void deleteCombinationOffer(Integer combinationOfferId) throws CombinationOfferNotExistException {
		getGeneralOfferService().getCombinationOfferService().delete(combinationOfferId);
	}
	@Transactional
	public CrossingOffer getCrossingOfferById(Integer crossingOfferId) throws CrossingOfferNotExistException{
		return getGeneralOfferService().getCrossingOfferService().getCrossingOfferById(crossingOfferId);
	}
	@Transactional
	public void deleteCrossingOffer(Integer crossingOfferId) throws CrossingOfferNotExistException {
		getGeneralOfferService().getCrossingOfferService().delete(crossingOfferId);
	}
	
	@Transactional
	public void createCrossingOffer(CrossingOffer crossingOffer) {
		getGeneralOfferService().getCrossingOfferService().createOffer(crossingOffer);
	}
	@Transactional
	public void createCrossingOffer(CrossingOfferDTO crossingOffer) throws ProductNotExistException {
		getGeneralOfferService().getCrossingOfferService().createOffer(crossingOffer.toCrossingOffer(getProductById(crossingOffer.productId)));
	}
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Transactional
	public void createProductList(Integer userId, ProductList productList) throws UserDoesNotExistException, UserIsNotLoggedException {
		User user = getUserById(userId);
		user.validateLogged();
		user.getProfile().addNewProductList(productList);
		getUserService().update(user);
	}

	@Transactional
	public void deleteProductList(Integer userId, Integer productlistId) throws UserDoesNotExistException, ProductListNotExistException, UserIsNotLoggedException {
		User user = getUserById(userId);
		user.validateLogged();
		user.getProfile().getAllProductList().remove(user.getProfile().getProductListById(productlistId));
		getUserService().update(user);	
	}

	@Transactional
	public void createSelectedProduct(Integer userId, Integer productlistId, Integer productId, Integer quantity) throws UserDoesNotExistException, UserIsNotLoggedException, ProductListNotExistException, ProductNotExistException, ProductIsAlreadySelectedException {
		User user = getUserById(userId);
		user.validateLogged();
		user.getProfile().getProductListById(productlistId).selectProduct(getProductById(productId), quantity);
		getUserService().update(user);
	}

	@Transactional
	public void deleteSelectedProduct(Integer userId, Integer productlistId, Integer selectedProductId) throws UserDoesNotExistException, ProductListNotExistException, SelectedProductNotExistException, ProductDoesNotExistOnListException, ProductIsAlreadySelectedException, MoneyCannotSubstractException, UserIsNotLoggedException {
		User user = getUserById(userId);
		user.validateLogged();
		ProductList pl = user.getProfile().getProductListById(productlistId);
		SelectedProduct sp = pl.getSelectedProduct(selectedProductId);
		pl.removeProduct(sp.getProduct());
		getUserService().update(user);
	}

	@Transactional
	public Duration getWaitingTime(Integer userId, Integer productlistId) throws UserDoesNotExistException, ProductListNotExistException, UserIsNotLoggedException {
		User user = getUserById(userId);
		user.validateLogged();
		return getShopService().waitingTime(user, user.getProductListById(productlistId));
	}

	@Transactional
	public Duration ready(Integer userId, Integer productlistId) throws UserDoesNotExistException, UserIsNotLoggedException, ProductListNotExistException, InvalidSelectedProduct {
		User user = getUserById(userId);
		user.validateLogged();
		return getShopService().ready(user, user.getProductListById(productlistId));
	}

	@Transactional
	public void shop(Integer userId, Integer productlistId) throws UserDoesNotExistException, UserIsNotLoggedException, ProductListNotExistException {
		User user = getUserById(userId);
		user.validateLogged();
		getShopService().shop(user, user.getProductListById(productlistId));
	}
	
	@Transactional
	public void deletePurchaseRecord(Integer userId, Integer purchaseId) throws UserDoesNotExistException, UserIsNotLoggedException, PurchaseRecordNotExistException {
		User user = getUserById(userId);
		user.validateLogged();
		user.getProfile().deletePurchaseRecordById(purchaseId);
		getUserService().update(user);
	}

	@Transactional
	public void updateSelectedProduct(Integer userId, Integer productlistId, Integer selectedProductId,
			SelectedProductDTO selectedProduct) throws UserDoesNotExistException, UserIsNotLoggedException, ProductListNotExistException, ProductNotExistException, ProductIsAlreadySelectedException{
		User user = getUserById(userId);
		user.validateLogged();
		ProductList pl = user.getProductListById(productlistId);
		try {
			pl.getSelectedProduct(selectedProductId).setQuantity(selectedProduct.quantity);
		} catch (SelectedProductNotExistException e) {
			pl.selectProduct(getProductById(selectedProduct.productId), selectedProduct.quantity);
		}
		getUserService().update(user);
	}

	@Transactional
	public void loginWithMailUser(User user) throws UserAlreadyExistsException, UsernameDoesNotExistException, UsernameOrPasswordInvalidException {
		User exists = getUserService().findByUsername(user.getEmail());
		if(exists == null){
			user.setPassword(new Password(user.getEmail()));
			getUserService().createNewUser(user);
			getUserService().loginUser(user);
		}else{
			getUserService().loginUser(exists);
		}
		
		
		
	}

}
