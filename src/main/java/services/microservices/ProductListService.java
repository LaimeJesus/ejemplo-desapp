package services.microservices;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import exceptions.ProductDoesNotExistOnListException;
import exceptions.ProductIsAlreadySelectedException;
import model.products.Product;
import model.products.ProductList;

public class ProductListService extends GenericService<ProductList> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4298967256904633606L;

	private ProductService productService;
	
	
	
	@Transactional
	public void selectProduct( ProductList productList , Product product , Integer quantity) throws ProductIsAlreadySelectedException {
		this.validateProductList(productList);
		if (this.validateProduct(product)) {
			List<ProductList> possible = this.getRepository().findByExample(productList);
			ProductList result = possible.get(0);
			result.selectProduct(product, quantity);
			this.update(result);
		}
	}	
	
	@Transactional
	public void removeProduct(ProductList productList, Product product) throws ProductDoesNotExistOnListException {
		if (this.validateProduct(product) ) {
			List<ProductList> possible = this.getRepository().findByExample(productList);
			ProductList result = possible.get(0);
			result.removeProduct(product);
			this.update(result);
		}
		
	}
	
	private void validateProductList(ProductList aProductList) {
		//TODO
	}
	
	@Transactional
	private boolean validateProduct(Product aProduct) {
		return productService.retriveAll().contains(aProduct);
	}

	
	
	
	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
}
