package rest.dtos;

import model.products.ProductList;

public class ProductListDTO {

	public String name;
	public ProductList toProductList() {
		return new ProductList(name);
	}	
}
