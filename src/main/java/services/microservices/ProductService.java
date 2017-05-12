package services.microservices;

import java.util.List;

import javax.transaction.Transactional;

import exceptions.InvalidSelectedProduct;
import model.products.Product;
import model.products.ProductList;
import model.products.SelectedProduct;
import util.CSVProductParser;

public class ProductService extends GenericService<Product> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8067303758622033038L;
	
	@Transactional
	public void upload(String csv) throws Exception{
		CSVProductParser parser = new CSVProductParser();
		List<Product> products = parser.toListObject(parser.parse(csv));
		
		saveall(products);
	}

	@Transactional
	private void saveall(List<Product> products) {
		for(Product aProduct : products){
			Product newProduct = findByProduct(aProduct);
			if(newProduct != null){
				this.save(newProduct);
			}
			else{
				this.update(aProduct);
			}
		}
		
	}

	@Transactional
	private Product findByProduct(Product aProduct) {
		Product newProduct = new Product();
		newProduct.setName(aProduct.getName());
		newProduct.setBrand(aProduct.getBrand());
		List<Product> possibles = this.getRepository().findByExample(newProduct);
		return (possibles.size() > 0) ? possibles.get(0) : null;

	}

	@Transactional
	public void updateStock(ProductList p) throws InvalidSelectedProduct {
		for(SelectedProduct sp : p.getAllProducts()){
			validateSelectedProduct(sp);
			sp.getProduct().setStock(sp.getProduct().getStock() - sp.getQuantity());
			this.update(sp.getProduct());
		}
		
	}

	private void validateSelectedProduct(SelectedProduct sp) throws InvalidSelectedProduct {
		if(sp.getQuantity() > sp.getProduct().getStock()){
			throw new InvalidSelectedProduct("can not select that product");
		}
		
	}
}
