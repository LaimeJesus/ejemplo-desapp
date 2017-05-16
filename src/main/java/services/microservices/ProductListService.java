package services.microservices;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import exceptions.MoneyCannotSubstractException;
import exceptions.ProductDoesNotExistException;
import exceptions.ProductIsAlreadySelectedException;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import model.offers.Offer;
import model.products.Product;
import model.products.ProductList;
import model.users.User;

public class ProductListService extends GenericService<ProductList> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4298967256904633606L;

	private ProductService productService;
	private UserService userService;
	
	
	@Transactional
	public void selectProduct( User user , ProductList productList , Product product , Integer quantity) 
	throws 	ProductIsAlreadySelectedException, 
			ProductDoesNotExistException, UsernameDoesNotExistException, UserIsNotLoggedException {
		
		Product existProduct = validateProduct(product);
		User existUser = this.getUserService().validateLogged(user);
		ProductList valid = this.getByUser(productList, existUser);
		valid.selectProduct(existProduct, quantity);
		this.getUserService().update(existUser);
	}
	
	@Transactional
	private Product validateProduct(Product aProduct) throws ProductDoesNotExistException {
		Product exist = productService.findByProduct(aProduct);
		if (exist == null) {
			throw new ProductDoesNotExistException();
		}
		return exist;
	}
	
	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	
	@Transactional
	public ProductList getByUser(ProductList example , User owner) throws UsernameDoesNotExistException {
		User exist = getUserService().authenticateUser(owner);
		List<ProductList> listsOfUser = exist.getProfile().getAllProductList();
		for (ProductList current : listsOfUser) {
			if (current.equals(example)) {
				return current;
			}
		}
		return null;
		
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Transactional
	public void applyOffer(User someUser, Offer someOffer, ProductList someProductList) throws MoneyCannotSubstractException, UsernameDoesNotExistException {
		User exist = this.getUserService().authenticateUser(someUser);
		ProductList current = this.getByUser(someProductList, exist);
		current.applyOffer(someOffer);
		this.getUserService().update(exist);
	}
	
}
