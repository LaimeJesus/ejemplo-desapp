package services;

import java.util.ArrayList;
import java.util.List;

import model.CSVProductParser;
import model.Product;

public class ProductService {
	
	private CSVProductParser csvparser;
	private List<Product> products;
	
	public ProductService(){
		csvparser = new CSVProductParser();
		products = new ArrayList<Product>();
	}

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
	
	public List<Product> getProducts(){
		return this.products;
	}

	public void loadCSVFile(String csv) throws Exception{
		List<Product> newProducts = this.csvparser.toListObject(this.csvparser.parse(csv));
		this.saveList(newProducts);
	}

	private void saveList(List<Product> newProducts) {
		this.products = newProducts;
	}
	
	
}
