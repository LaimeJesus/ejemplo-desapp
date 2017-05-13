package rest.dtos;

import model.products.ProductList;

public class ProductListDTO {

	public String name;
	public ProductListDTO(){
		
	}
	public ProductListDTO(ProductList pl) {
		name = pl.getName();
	}
	public ProductList toProductList() {
		return new ProductList(name);
	}	
}
