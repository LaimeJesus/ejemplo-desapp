package services.microservices;

import java.util.List;

import javax.transaction.Transactional;

import model.products.Product;
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
}
