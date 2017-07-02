package services.microservices;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import exceptions.InvalidSelectedProduct;
import exceptions.InvalidUploadCSV;
import exceptions.ProductAlreadyCreatedException;
import exceptions.ProductNotExistException;
import model.products.Product;
import model.products.ProductList;
import model.products.SelectedProduct;
import util.CSVProductParser;

public class ProductService extends GenericService<Product> {


	/**
	 *
	 */
	private static final long serialVersionUID = -8067303758622033038L;

	private SelectedProductService selectedProductService;


	@Transactional
	public void addproduct(Product p) throws ProductAlreadyCreatedException{
		validateProduct(p);
		save(p);
	}

	@Transactional
	private Product validateProduct(Product p) throws ProductAlreadyCreatedException {
		Product prod = findByProduct(p);
		if( prod == null){
			return prod;
		}
		throw new ProductAlreadyCreatedException();

	}

	@Transactional
	public void upload(String csv) throws InvalidUploadCSV, ProductAlreadyCreatedException{
		CSVProductParser parser = new CSVProductParser();
		List<Product> products;
    try {
      products = parser.toListObject(parser.parse(csv));
    } catch (Exception e) {
      throw new InvalidUploadCSV();
    }
		for(Product p : products){
			createOrUpdate(p.getId(), p);
		}
	}

	@Transactional
	public void saveall(List<Product> products) {
		 List<Product> bdpds = retriveAll();
		for(Product aProduct : products){
			Product newProduct = findByProduct(aProduct);
			 if(bdpds.contains(newProduct)){
			  aProduct.setId(newProduct.getId());
			 	this.update(aProduct);
			 }
			 else{
				this.save(aProduct);
			 }
		}

	}

	@Transactional
	public Product findByProduct(Product aProduct) {
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
	public void deleteProduct(Product pro) {
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

	@Transactional
	public void createProduct(Product product) throws ProductAlreadyCreatedException {
		validateProduct(product);
		save(product);
	}

	@Transactional
	public void createOrUpdate(Integer productId, Product product) throws ProductAlreadyCreatedException {
		try {
			Product fromdb = getProductById(productId);
			fromdb.setName(product.getName());
			fromdb.setBrand(product.getBrand());
			fromdb.setStock(product.getStock());
			fromdb.getPrice().setInteger(product.getPrice().getInteger());
			fromdb.getPrice().setDecimal(product.getPrice().getDecimal());
			fromdb.setProcessingTime(product.getProcessingTime());
			fromdb.setImageUrl(product.getImageUrl());
//			product.setId(fromdb.getId());
//			delete(fromdb);
//			getRepository().delete(fromdb);
//			save(product);
			update(fromdb);
		} catch (ProductNotExistException e) {
			//validateProduct(product);
			save(product);
		}
	}

	@Transactional
	public Product getProductById(Integer productId) throws ProductNotExistException {
		Product p = findById(productId);
		if(p == null) throw new ProductNotExistException("Product with id: "+ productId + " does not exist");
		return p;
	}
}
