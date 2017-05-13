package services.microservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import exceptions.InvalidSelectedProduct;
import model.products.Product;
import model.products.ProductList;
import model.products.SelectedProduct;
import model.users.User;
import util.CSVProductParser;

public class ProductService extends GenericService<Product> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8067303758622033038L;
	
	@Autowired
	private SelectedProductService selectedProductService;
	
	
	@Transactional
	public void addproduct(User u, Product p){
		validateUser(u);
		validateProduct(p);
		save(p);
	}
	
	
	private void validateProduct(Product p) {
		if(findByProduct(p) != null){
		}
		
	}


	private void validateUser(User u) {
		// TODO Auto-generated method stub
		
	}


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
			Product current = this.getByExample(sp.getProduct());
			validateSelectedProduct(sp, current);
			current.setStock(current.getStock() - sp.getQuantity());
			this.update(current);
		}		
	}

	@Transactional
	private void validateSelectedProduct(SelectedProduct sp, Product current) throws InvalidSelectedProduct {
		if(sp.getQuantity() > current.getStock()){
			throw new InvalidSelectedProduct("can not select that product");
		}		
	}
	
	@Transactional
	@Override
	public void delete(Product pro) {
		List<SelectedProduct> allSelected = selectedProductService.retriveAll();
		for (SelectedProduct sp : allSelected) {
			if (sp.getProduct().equals(pro)){
				selectedProductService.delete(sp);
			}
		}
		super.delete(pro);
	}

	public SelectedProductService getSelectedProductService() {
		return selectedProductService;
	}

	public void setSelectedProductService(SelectedProductService selectedProductService) {
		this.selectedProductService = selectedProductService;
	}
}
