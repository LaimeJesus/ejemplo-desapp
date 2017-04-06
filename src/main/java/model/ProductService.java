package model;

import java.util.List;

public class ProductService {
	
	private CSVProductParser csvparser;
	private List<Product> products;

	public void addProduct(Product aProduct){
		this.save(aProduct);
	}

	private void save(Product aProduct) {
		this.products.add(aProduct);
	}
	
	public void removeProduct(Product aProduct){
		this.remove(aProduct);
	}

	private void remove(Product aProduct) {
		this.products.remove(aProduct);
	}
	
	public void loadCSVFile(String csv) throws Exception{
		List<Product> newProducts = this.csvparser.toListObject(this.csvparser.parse(csv));
		products = newProducts;
	}
	
}
